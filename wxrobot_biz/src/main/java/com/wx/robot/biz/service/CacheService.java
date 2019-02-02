package com.wx.robot.biz.service;

import com.wx.robot.common.entity.request.BaseRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class CacheService {

    private String uuid;

    private String hostUrl;

    private String syncUrl;

    private String fileUrl;

    private String passTicket;

    private String sKey;

    private String sid;

    private String uin;

    private BaseRequest baseRequest;

    //供外部接口调用
    private String qrCode;

}
