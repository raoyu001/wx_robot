package com.wx.robot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WechatErrorEnums {

    SUCCESS(0,"success"),
    UUID_NOT_FIND(1,"uuid not find"),

    QR_EXPIRED(2,"qr code expired"),

    LOGIN_CODE_NOT_FIND(3,"login code not find"),

    ACCESS_TOKEN_FAILURE(4,"获取token失败");

    int code;

    String message;

}
