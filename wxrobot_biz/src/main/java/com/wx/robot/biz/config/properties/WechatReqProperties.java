package com.wx.robot.biz.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WechatReqProperties {

    @Value("${wechat.qr.refreshTime}")
    int qrRefreshTime;

    @Value("${wechat.url.entry}")
    String entryUrl;

    @Value("${wechat.url.loginBase}")
    String loginBaseUrl;

    @Value("${wechat.url.uuid}")
    String uuidUrl;

    @Value("${wechat.url.qr}")
    String qrUrl;

    @Value("${wechat.url.statReport}")
    String statReportUrl;

    @Value("${wechat.url.login}")
    String loginUrl;

    @Value("${wechat.browser.userAgent}")
    String userAgent;

    @Value("${wechat.browser.acceptEncoding}")
    String acceptEncoding;

    @Value("${wechat.browser.acceptLanguage}")
    String acceptLanguage;
}
