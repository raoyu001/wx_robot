package com.wx.robot.common.exception;

import com.wx.robot.common.enums.WechatErrorEnums;

public class WechatException extends RuntimeException {

    public WechatException(WechatErrorEnums errorEnums) {
        super(errorEnums.getMessage());
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }

    public WechatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
