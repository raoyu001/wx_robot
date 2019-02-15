package com.wx.robot.biz.service;

import com.wx.robot.biz.config.properties.WechatReqProperties;
import com.wx.robot.biz.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class SyncService {

    @Resource
    private CacheService cacheService;

    @Resource
    private WechatHttpComponent wechatHttpComponent;

    @Resource
    private MessageHandler messageHandler;

    @Resource
    private WechatReqProperties wechatReqProperties;

    private final static String RED_PACKET_CONTENT = "收到红包，请在手机上查看";
}
