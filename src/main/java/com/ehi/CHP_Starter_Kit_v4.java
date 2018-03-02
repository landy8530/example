package com.ehi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CHP_Starter_Kit_v4 {

    public static class CloudHygieneAPI {

        public String base_url = "https://api.cloudhygiene.com/";

        private String token;
        private static final String LINE_FEED = "\r\n";

        public CloudHygieneAPI(String token) {
            this.token = token;
        }

        private HttpURLConnection getConnection(String method, String fullUrl) throws IOException,KeyManagementException,NoSuchAlgorithmException {
            URL url = new URL(fullUrl);
            //解决PKIX path building failed的问题
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier((urlHostName,session)->{
                System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                        + session.getPeerHost());
                return true;
            });

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("X-API-KEY", token);
            return connection;
        }

        private void trustAllHttpsCertificates() throws KeyManagementException,NoSuchAlgorithmException {
            javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
            javax.net.ssl.TrustManager tm = new miTM();
            trustAllCerts[0] = tm;
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                    .getInstance("SSL");
            sc.init(null, trustAllCerts, null);
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                    .getSocketFactory());
        }

        private String readResponse(HttpURLConnection connection) throws IOException {
            String Response = "";
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer responseBuffer = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                Response = responseBuffer.toString();
                in.close();
            }
            return Response;
        }

        public String get(String func, HashMap<String, String> params) throws IOException,KeyManagementException,NoSuchAlgorithmException {
            String fullUrl = base_url + func + "?";
            for (Map.Entry<String, String> m:params.entrySet()) {
                fullUrl += m.getKey() + "=" + m.getValue() + "&";
            }
            System.out.println("Making request to " + fullUrl);
            HttpURLConnection con = getConnection("GET", fullUrl);
            return readResponse(con);
        }

        public String post(String func, HashMap<String, String> params) throws IOException,KeyManagementException,NoSuchAlgorithmException {
            String fullUrl = base_url + func;
            String body = "";
            for (Map.Entry<String, String> m:params.entrySet()) {
                body += m.getKey() + "=" + m.getValue() + "&";
            }
            System.out.println("Making request to " + fullUrl);
            HttpURLConnection connection = getConnection("POST", fullUrl);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            return readResponse(connection);
        }

        public String express(String emails, String profile) throws IOException,KeyManagementException,NoSuchAlgorithmException {
            HashMap<String, String> params = new HashMap<>();
            params.put("emails", emails);
            params.put("profile", profile);
            return this.get("task/express", params);
        }

        public String batch_raw(File file, String profile) throws IOException, NoSuchAlgorithmException,KeyManagementException {
            String filename = file.getName();
            String fullUrl = base_url + "task/run";
            byte[] fileContent = Files.readAllBytes(file.toPath());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(fileContent);
            byte[] digest = md.digest();
            String md5hash = DatatypeConverter.printHexBinary(digest);
            String boundary = UUID.randomUUID().toString();
            System.out.println("Making request to " + fullUrl + " / " + md5hash);
            HttpURLConnection connection = getConnection("POST", fullUrl);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(connection.getOutputStream(), "UTF-8"),
                    true);
            HashMap<String, String> params = new HashMap<>();
            params.put("profile", profile);
            params.put("md5", md5hash);
            // add parameters
            for (Map.Entry<String, String> m : params.entrySet()) {
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + m.getKey() + "\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=UTF-8").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(m.getValue()).append(LINE_FEED);
            }
            // add file part
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"data_file\";" +
                    "filename=\"" + filename + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: " + HttpURLConnection.guessContentTypeFromName(filename))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            connection.getOutputStream().write(fileContent);
            connection.getOutputStream().flush();
            writer.append(LINE_FEED);

            // closing multipart
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            return readResponse(connection);
        }

        public Boolean downloadFile(String packageName, String fileName, String outFileName) throws IOException,KeyManagementException,NoSuchAlgorithmException {
            String fullUrl = base_url + "task/get_file?package_name=" +
                    packageName + "&filename=" + fileName;
            HttpURLConnection con = getConnection("GET", fullUrl);
            InputStream inputStream = con.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(outFileName);
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return true;
        }

        public JSONObject parse(String jsonString) throws ParseException {
            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject) parser.parse(jsonString);
            Object errors = result.get("errors");
            if (errors != null && errors instanceof JSONArray && ((JSONArray)errors).size() > 0) {
                System.out.println("We've got errors : " + errors.toString());
                return null;
            } else {
                return result;
            }
        }
    }


    static class miTM implements javax.net.ssl.TrustManager,javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    static final String API_KEY = "TOKEN";
    static final String PROFILE = "__TEST";

    public static void main(String[] args) throws IOException, ParseException, NoSuchAlgorithmException, InterruptedException,KeyManagementException {
        // sample get call - getting api version
        String responseString;
        JSONObject responseObject;
        HashMap<String, String> params = new HashMap<>();

        CloudHygieneAPI api = new CloudHygieneAPI(API_KEY);
        responseString = api.get("version", params);
        System.out.println("API version : " + responseString);
        responseString = api.post("version", params);
        System.out.println("API version : " + responseString);

        // express task
        responseString = api.express("testemail@gmail.com", PROFILE);
        // parse json result as you like
        responseObject = api.parse(responseString);
        if (responseObject != null) {
            JSONArray emails = (JSONArray)responseObject.get("emails");
            for (Object email_record : emails) {
                String email = (String) ((JSONObject) email_record).get("email");
                String result = (String) ((JSONObject) email_record).get("result");
                // result equals to Clean/Valid means email is good
                System.out.println(email + " : " + result);
            }
        }

        // batch task
        // multipart
        File file = new File("../test_small.csv");
        responseString = api.batch_raw(file, PROFILE);
        System.out.println("Batch response : " + responseString);
        responseObject = api.parse(responseString);
        if (responseObject != null) {
            String packageName = (String) responseObject.get("package_name");
            Boolean isCompleted = false;
            params.clear();
            params.put("package_name", packageName);
            while(!isCompleted) {
                responseString = api.get("task/info", params);
                System.out.println("Response : " + responseString);
                responseObject = api.parse(responseString);
                if (responseObject != null) {
                    System.out.println("status : " + responseObject.get("status"));
                    // finished if task is in completed state and returned files
                    if (responseObject.get("status").toString().equals("completed") &&
                            responseObject.get("files") != null) {
                            JSONArray files = (JSONArray) responseObject.get("files");
                            for(Object resultFile:files) {
                                System.out.println("Result file : " + resultFile.toString());
                            }
                            System.out.println("Download insert file to result_insert.csv...");
                            api.downloadFile(packageName, "insert.csv", "result_insert.csv");
                            System.out.println("Done.");
                            break;
                        }
                } else {
                    break;
                }
                Thread.sleep(10000);
            }
        }


    }


}
