package com.wx.robot.web.controller;

import com.wx.robot.biz.service.CacheService;
import com.wx.robot.common.result.DTOWrapper;
import com.wx.robot.common.result.PlainResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private CacheService cacheService;

    @RequestMapping("/qrCode")
    public PlainResult<String> qrCode(){
        String qrCode = cacheService.getQrCode();
        return DTOWrapper.wrapPlain(qrCode);
    }

}
