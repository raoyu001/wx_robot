package com.wx.robot.biz.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.wx.robot.biz.config.properties.WechatReqProperties;
import com.wx.robot.common.entity.shared.Token;
import com.wx.robot.common.entity.request.BaseRequest;
import com.wx.robot.common.entity.response.LoginResp;
import com.wx.robot.common.enums.LoginStatusEnums;
import com.wx.robot.common.enums.WechatErrorEnums;
import com.wx.robot.common.exception.WechatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class LoginService {

    @Resource
    private CacheService cacheService;

    @Resource
    private WechatReqProperties wechatReqProperties;

    @Resource
    private WechatHttpComponent wechatHttpComponent;

    public void login() {
        log.info("wxrobot start");

        try {
            wechatHttpComponent.open(wechatReqProperties.getQrRefreshTime());

            String uuid = wechatHttpComponent.getUUID();
            //保存uuid
            cacheService.setUuid(uuid);
            //根据uuid生成二维码
            String qrCode = wechatHttpComponent.getQR(uuid);
            log.info("qrcode complete \n {}", qrCode);

            cacheService.setQrCode(qrCode);
            wechatHttpComponent.statReport();

            LoginResp loginResp;
            while (true) {//轮询检查登录的状态
                if (handleLoginResp(loginResp = wechatHttpComponent.accessCode(uuid))) {
                    break;
                }
            }

            Token token = wechatHttpComponent.accessToken(loginResp.getRedirectUrl());
            handleTokenAndRedirect(token);
        } catch (Exception e) {
            log.error("login fail, {}", e.getMessage());
        }
    }
    //模拟浏览器处理返回的token信息，并回调通知
    private void handleTokenAndRedirect(Token token){
        log.info("token info,{}", JSON.toJSONString(token));

        if(token.getRet() != 0){
            throw new WechatException(WechatErrorEnums.ACCESS_TOKEN_FAILURE);
        }

        cacheService.setPassTicket(token.getPass_ticket());
        cacheService.setSKey(token.getSkey());
        cacheService.setSid(token.getWxsid());
        cacheService.setUin(token.getWxuin());

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUin(cacheService.getUin());
        baseRequest.setSid(cacheService.getSid());
        baseRequest.setSkey(cacheService.getSKey());
        cacheService.setBaseRequest(baseRequest);
        //授权成功后，重定向到主界面
        wechatHttpComponent.accessedRedirect(cacheService.getHostUrl());
    }


    private boolean handleLoginResp(LoginResp loginResp) {
        String code = loginResp.getCode();
        String hostUrl = loginResp.getHostUrl();

        Preconditions.checkArgument(StringUtils.isNotEmpty(code), "login code not null");

        if (LoginStatusEnums.SUCCESS.getCode().equals(code)) {
            cacheService.setHostUrl(hostUrl);
            if (hostUrl.equals("https://wechat.com")) {
                cacheService.setSyncUrl("https://webpush.web.wechat.com");
                cacheService.setFileUrl("https://file.web.wechat.com");
            } else {
                cacheService.setSyncUrl(hostUrl.replaceFirst("^https://", "https://webpush."));
                cacheService.setFileUrl(hostUrl.replaceFirst("^https://", "https://file."));
            }
            return true;
        } else if (LoginStatusEnums.AWAIT_CONFIRM.getCode().equals(code)) {
            log.info("[*] login status = AWAIT_CONFIRMATION");
        } else if (LoginStatusEnums.AWAIT_SCAN.getCode().equals(code)) {
            log.info("[*] login status = AWAIT_SCANNING");
        } else if (LoginStatusEnums.EXPIRED.getCode().equals(code)) {
            log.info("[*] login status = EXPIRED");
            throw new WechatException(WechatErrorEnums.QR_EXPIRED);
        } else {
            log.info("[*] login status = " + code);
        }
        return false;
    }
}
