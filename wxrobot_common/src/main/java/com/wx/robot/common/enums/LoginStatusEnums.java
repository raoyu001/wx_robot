package com.wx.robot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginStatusEnums {


    SUCCESS("200","成功"),
    AWAIT_CONFIRM("201","在手机上点击确认"),
    EXPIRED("400","二维码超时"),
    AWAIT_SCAN("408","等待扫描二维码");

    String code;

    String message;
}
