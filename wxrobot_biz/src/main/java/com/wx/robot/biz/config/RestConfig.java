package com.wx.robot.biz.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(){
        //借助httpcontext存储信息
        HttpContext httpContext = new BasicHttpContext();

        httpContext.setAttribute(HttpClientContext.COOKIE_STORE,new BasicCookieStore());
        httpContext.setAttribute(HttpClientContext.REQUEST_CONFIG,RequestConfig.custom().build());
        return new StatefulRestTemplate(httpContext);
    }
}
