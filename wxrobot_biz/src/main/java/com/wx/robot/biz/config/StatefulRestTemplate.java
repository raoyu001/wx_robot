package com.wx.robot.biz.config;

import lombok.Getter;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class StatefulRestTemplate extends RestTemplate{

    @Getter
    private HttpContext httpContext;

    StatefulRestTemplate(HttpContext httpContext){
        this.httpContext = httpContext;

        HttpComponentsClientHttpRequestFactory requestFactory =
                new StatefulHttpComponentsClientHttpRequestFactory(httpContext,HttpClients.createDefault());
        setRequestFactory(requestFactory);

        getMessageConverters().forEach(item -> {
            if(item instanceof MappingJackson2HttpMessageConverter){
                //内部实现是一个unmodifiableList
                List<MediaType> mediaTypes = item.getSupportedMediaTypes();
                List<MediaType> newMediaTypes = new ArrayList<>(mediaTypes);
                newMediaTypes.add(MediaType.TEXT_HTML);
                ((MappingJackson2HttpMessageConverter) item).setSupportedMediaTypes(newMediaTypes);
            }
        });
    }

    public CookieStore getCookieStore(){
        return (CookieStore) httpContext.getAttribute(HttpClientContext.COOKIE_STORE);
    }

    private class StatefulHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory{

        private final HttpContext httpContext;

        StatefulHttpComponentsClientHttpRequestFactory(HttpContext httpContext, HttpClient httpClient){
            super(httpClient);
            this.httpContext = httpContext;
        }

        @Override
        protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
            return super.createHttpContext(httpMethod, uri);
        }
    }
}
