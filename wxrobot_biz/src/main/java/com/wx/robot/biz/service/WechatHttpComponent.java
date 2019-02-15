package com.wx.robot.biz.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wx.robot.biz.config.StatefulRestTemplate;
import com.wx.robot.biz.config.properties.WechatReqProperties;
import com.wx.robot.common.entity.shared.StatReport;
import com.wx.robot.common.entity.shared.Token;
import com.wx.robot.common.entity.request.BaseRequest;
import com.wx.robot.common.entity.request.StatReportRequest;
import com.wx.robot.common.entity.response.LoginResp;
import com.wx.robot.common.enums.WechatErrorEnums;
import com.wx.robot.common.exception.WechatException;
import com.wx.robot.common.utils.HeaderUtils;
import com.wx.robot.common.utils.QRCodeUtils;
import com.wx.robot.common.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class WechatHttpComponent {

    private HttpHeaders getHeader = new HttpHeaders();

    private HttpHeaders postHeader = new HttpHeaders();

    private WechatReqProperties wechatReqProperties;

    //请求的最初来源，只适用于POST
    private String originValue;
    //与origin的区别在于不仅限于POST
    private String refererValue;

    @Resource
    private RestTemplate restTemplate;

    WechatHttpComponent(@Autowired WechatReqProperties wechatReqProperties) {
        this.wechatReqProperties = wechatReqProperties;

        postHeader.set(HttpHeaders.USER_AGENT, wechatReqProperties.getUserAgent());
        postHeader.set(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        postHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.ALL));
        postHeader.set(HttpHeaders.ACCEPT_LANGUAGE, wechatReqProperties.getAcceptLanguage());
        postHeader.set(HttpHeaders.ACCEPT_ENCODING, wechatReqProperties.getAcceptLanguage());

        getHeader.set(HttpHeaders.USER_AGENT, wechatReqProperties.getUserAgent());
        getHeader.set(HttpHeaders.ACCEPT_LANGUAGE, wechatReqProperties.getAcceptLanguage());
        getHeader.set(HttpHeaders.ACCEPT_ENCODING, wechatReqProperties.getAcceptEncoding());
    }

    void open(int retryTimes) throws URISyntaxException {
        final String entryUrl = wechatReqProperties.getEntryUrl();
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setPragma("no-cache");
        customHeader.setCacheControl("no-cache");
        customHeader.set("Upgrade-Insecure-Requests", "1");
        customHeader.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

        HeaderUtils.assign(customHeader, getHeader);
        restTemplate.getForObject(entryUrl, String.class, new HttpEntity<>(customHeader));
        //manually insert two cookies into cookiestore, as they're supposed to be stored in browsers by javascript.

        Map<String, String> cookies = new HashMap<>(3);
        cookies.put("MM_WX_NOTIFY_STATE", "1");
        cookies.put("MM_WX_SOUND_STATE", "1");
        if (retryTimes > 0) {
            cookies.put("refreshTimes", String.valueOf(retryTimes));
        }

        String domain = new URI(entryUrl).getHost();
        CookieStore store = ((StatefulRestTemplate) restTemplate).getCookieStore();

        appendAdditionalCookies(store, cookies, domain, "/", new Date(Long.MAX_VALUE));
        //It's now at entry page.
        this.originValue = entryUrl;
        this.refererValue = entryUrl.replaceAll("/$", "");
    }

    String getUUID() {
        //使用regex查询uuid
        final String regEx = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";";
        final String url = String.format(wechatReqProperties.getUuidUrl(), System.currentTimeMillis());

        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setPragma("no-cache");
        customHeader.setCacheControl("no-cache");
        customHeader.setAccept(Arrays.asList(MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        HeaderUtils.assign(customHeader, getHeader);
        String json = restTemplate.getForObject(url, String.class, new HttpEntity<>(customHeader));
        Matcher matcher = Pattern.compile(regEx).matcher(json);
        if (matcher.find()) {
            if ("200".equals(matcher.group(1))) {
                return matcher.group(2);
            }
        }
        throw new WechatException(WechatErrorEnums.UUID_NOT_FIND);
    }

    String getQR(String uuid) {
        final String url = String.format(wechatReqProperties.getQrUrl(), uuid);

        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "image/webp,image/apng,image/*,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        HeaderUtils.assign(customHeader, getHeader);

        byte[] bytes = restTemplate.getForObject(url, byte[].class, new HttpEntity<>(customHeader));

        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        String qrCode = null;
        try {
            String qrUrl = QRCodeUtils.decode(bos);
            qrCode = QRCodeUtils.generateQR(qrUrl, 40, 40);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return qrCode;
    }

    void statReport() {
        final String url = wechatReqProperties.getStatReportUrl();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUin("");
        baseRequest.setSid("");
        StatReportRequest request = new StatReportRequest(baseRequest, 0, new StatReport[0]);

        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        customHeader.setOrigin(wechatReqProperties.getEntryUrl().replaceAll("/$", ""));

        HeaderUtils.assign(customHeader, postHeader);
        String json = restTemplate.postForObject(url, new HttpEntity<>(request, customHeader), String.class);
        log.debug("statReport response, {}", json);
    }

    LoginResp accessCode(String uuid) {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept(Arrays.asList(MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        HeaderUtils.assign(customHeader, getHeader);

        long time = System.currentTimeMillis();
        long wiseTime = RandomUtils.generateDateWithBitwiseNot(time);
        String url = String.format(wechatReqProperties.getLoginUrl(), uuid, wiseTime, time);

        String json = restTemplate.getForObject(url, String.class, new HttpEntity<>(customHeader));
        log.info("login resp,{}", json);
        //正则查询code和重定向链接
        Pattern codePattern = Pattern.compile("window.code=(\\d+)");
        Pattern hostUrlPattern = Pattern.compile("window.redirect_uri=\\\"(.*)\\/cgi-bin");
        Pattern redirectUrlPattern = Pattern.compile("window.redirect_uri=\\\"(.*)\\\";");

        LoginResp loginResp = new LoginResp();

        Matcher matcher = codePattern.matcher(json);
        if (matcher.find()) {
            loginResp.setCode(matcher.group(1));
        }

        Matcher hostUrlMatcher = hostUrlPattern.matcher(json);
        if (hostUrlMatcher.find()) {
            loginResp.setHostUrl(hostUrlMatcher.group(1));
        }

        Matcher redirectUrlMatcher = redirectUrlPattern.matcher(json);
        if (redirectUrlMatcher.find()) {
            loginResp.setRedirectUrl(redirectUrlMatcher.group(1));
        }
        return loginResp;
    }

    Token accessToken(String redirectUrl) throws IOException {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        customHeader.set("Upgrade-Insecure-Requests", "1");
        HeaderUtils.assign(customHeader, getHeader);

        String xmlString = restTemplate.getForObject(redirectUrl, String.class, new HttpEntity<>(customHeader));
        return new XmlMapper().readValue(xmlString, Token.class);
    }

    void accessedRedirect(String hostUrl) {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, wechatReqProperties.getEntryUrl());
        customHeader.set("Upgrade-Insecure-Requests", "1");
        HeaderUtils.assign(customHeader, getHeader);

        String url = hostUrl + "/";
        restTemplate.getForObject(url, String.class, new HttpEntity<>(customHeader));
        //It's now at main page.
        this.originValue = hostUrl;
        this.refererValue = url;
    }

    void logout(String hostUrl, String skey) throws IOException {
        final String url = String.format(wechatReqProperties.getLogoutUrl(), hostUrl, escape(skey));
        restTemplate.getForObject(url, String.class, new HttpEntity<>(postHeader));
    }


    private void appendAdditionalCookies(CookieStore cookieStore, Map<String, String> cookies, String domain, String path, Date expireDate) {
        cookies.forEach((key, value) -> {
            BasicClientCookie cookie = new BasicClientCookie(key, value);
            cookie.setDomain(domain);
            cookie.setPath(path);
            cookie.setExpiryDate(expireDate);
            cookieStore.addCookie(cookie);
        });
    }

    private String escape(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    }
}
