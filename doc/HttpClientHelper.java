package com.ehi.rrs.common.http;

import com.ehi.rrs.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author landyl
 * @create 9:54 08/23/2019
 */
class HttpClientHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientHelper.class);

    private CloseableHttpClient client;
    private List<NameValuePair> nameValuePostBodies;
    private List<Header> headers;
    private String contentType;
    private Map<String, String> bodyParams;


    public HttpClientHelper(RequestConfig requestConfig, List<NameValuePair> nameValuePostBodies, List<Header> headers, String contentType, Map<String, String> bodyParams) {
        if(requestConfig != null) {
            this.client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(HttpClientManager.getConnManager()).build();
        } else {
            this.client = HttpClients.custom().setConnectionManager(HttpClientManager.getConnManager()).build();
        }
        this.nameValuePostBodies = nameValuePostBodies;
        this.headers = headers;
        this.contentType = contentType;
        this.bodyParams = bodyParams;
    }

    public HttpResponseContent doPost(String url) {
        try {
            return this.doPost(url, HttpConstants.ENCODING_UTF8);
        } catch (Exception ex) {
            LOGGER.error("occurs an unexpected exception,url:{}", url, ex);
            return null;
        }
    }

    public HttpResponseContent doPost(String url, String urlEncoding) throws HttpException, IOException {
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;

        if(StringUtils.isBlank(urlEncoding)) urlEncoding = HttpConstants.ENCODING_UTF8;

        try {
            HttpPost httpPost = new HttpPost(url);
            //set bodies
            if(HttpConstants.APPLICATION_FORM_URLENCODED.equals(contentType)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePostBodies, urlEncoding));
            } else {
                String bodyParamsJson = JsonUtil.modelToJson(bodyParams);
                StringEntity stringEntity = new StringEntity(bodyParamsJson, ContentType.create(HttpConstants.APPLICATION_JSON, urlEncoding));
                httpPost.setEntity(stringEntity);
            }

            //set headers
            for (Header header : headers) {
                httpPost.setHeader(header);
            }
            request = httpPost;
            response = this.client.execute(httpPost);
            HttpResponseContent responseContent = this.buildResponseContent(response);
            return responseContent;
        } finally {
            this.close(entity, request, response);
        }
    }

    private HttpResponseContent buildResponseContent(CloseableHttpResponse response) throws HttpException, IOException {
        HttpResponseContent responseContent = new HttpResponseContent();
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        Header enHeader = entity.getContentEncoding();
        if (enHeader != null) {
            String encoding = enHeader.getValue().toLowerCase();
            responseContent.setEncoding(encoding);
        }

        String contentType = this.getResponseContentType(entity);
        int statusCode = statusLine.getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), HttpConstants.ENCODING_UTF8);
            throw new HttpException("Invalid status code: " + statusCode + ", result: " + result);
        }

        responseContent.setStatusCode(statusCode);
        responseContent.setContentType(contentType);
        responseContent.setContentTypeString(this.getResponseContentTypeString(entity));
        responseContent.setContentBytes(EntityUtils.toByteArray(entity));

        return responseContent;
    }


    private String getResponseContentType(HttpEntity method) {
        Header contentType = method.getContentType();
        if (contentType == null) {
            return null;
        } else {
            String ret = null;

            try {
                HeaderElement[] hes = contentType.getElements();
                if (hes != null && hes.length > 0) {
                    ret = hes[0].getName();
                }
            } catch (Exception var5) {
            }

            return ret;
        }
    }

    private String getResponseContentTypeString(HttpEntity method) {
        Header contentType = method.getContentType();
        return contentType == null ? null : contentType.getValue();
    }

    private void close(HttpEntity entity, HttpRequestBase request, CloseableHttpResponse response) throws IOException {
        if (request != null) {
            request.releaseConnection();
        }

        if (entity != null) {
            entity.getContent().close();
        }

        if (response != null) {
            response.close();
        }
    }

    public static class Builder {
        private List<NameValuePair> nameValuePostBodies = new LinkedList();
        private List<Header> headers = new LinkedList();
        private RequestConfig requestConfig;

        private Integer socketTimeout = 60000;
        private Integer connectTimeout = 60000;
        private Integer connectionRequestTimeout = 60000;

        private String contentType;
        private Map<String, String> bodyParamsCopy;

        public Builder() {
        }

        public Builder(Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
            this.socketTimeout = socketTimeout;
            this.connectTimeout = connectTimeout;
            this.connectionRequestTimeout = connectionRequestTimeout;
        }

        public Builder setBodyParams(Map<String, String> bodyParams) {
            bodyParamsCopy = bodyParams;
            if (bodyParams != null && !bodyParams.isEmpty()) {
                for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                    nameValuePostBodies.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            return this;
        }

        public Builder setHeaderParams(Map<String, String> headerParams) {
            contentType = headerParams.get(HttpConstants.CONTENT_TYPE);
            if(StringUtils.isBlank(contentType)) contentType = HttpConstants.APPLICATION_JSON;
            if (headerParams != null && !headerParams.isEmpty()) {
                for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                    headers.add(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            return this;
        }

        public Builder setRequestConfig(RequestConfig requestConfig) {
            this.requestConfig = requestConfig;
            return this;
        }

        public Builder setRequestConfig(Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
            this.requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            return this;
        }

        public HttpClientHelper build() {
            if(this.requestConfig == null) {
                this.setRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout);
            }
            return new HttpClientHelper(requestConfig, nameValuePostBodies, headers, contentType ,bodyParamsCopy);
        }
    }
}
