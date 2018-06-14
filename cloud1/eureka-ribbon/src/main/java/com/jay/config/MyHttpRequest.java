package com.jay.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;

/**
 * 自定义请求类，用于转换URI
 */
public class MyHttpRequest implements HttpRequest {
    private HttpRequest sourceRequest;

    public MyHttpRequest(HttpRequest sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    @Override
    public HttpHeaders getHeaders() {
        return sourceRequest.getHeaders();
    }

    @Override
    public HttpMethod getMethod() {
        return sourceRequest.getMethod();
    }

    /**
     * 将URI转换
     */
    @Override
    public URI getURI() {
//        try {
//            URI newUri = new URI("http://localhost:8080/hello");
//            return newUri;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        URI uri = sourceRequest.getURI();
        System.out.print("请中的查询参数，query=" + uri.getQuery());

        return sourceRequest.getURI();
    }

    @Override
    public String getMethodValue() {
        return null;
    }
}
