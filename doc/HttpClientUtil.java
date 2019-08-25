package com.ehi.rrs.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author landyl
 * @create 11:11 08/23/2019
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    public static HttpResponseContent doPost(String url, Map<String, String> bodyParams, Map<String, String> headerParams) {
        LOGGER.info("Access URL according HttpClient: {}", url);
        HttpClientHelper.Builder builder = create();
        if(headerParams == null) {
            headerParams = new HashMap<>();
            headerParams.put(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON);
        }
        HttpClientHelper helper = builder.setBodyParams(bodyParams).setHeaderParams(headerParams).build();
        return helper.doPost(url);
    }

    public static HttpResponseContent doPost(String url, Map<String, String> bodyParams) {
        return doPost(url, bodyParams, null);
    }

    private static HttpClientHelper.Builder create() {
        return new HttpClientHelper.Builder();
    }

}
