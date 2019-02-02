package com.wx.robot.common.entity.response;

import lombok.Data;

@Data
public class LoginResp {
    private String code;

    private String hostUrl;

    private String redirectUrl;
}
