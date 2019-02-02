package com.wx.robot.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chenzhen
 * @date 2018/3/22
 */
@Data
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1949910043360896391L;

    /**
     * 标识本次调用是否返回
     */
    private boolean success;

    /**
     * 本次调用返回code，一般为错误代码
     */
    private int code;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String message;

    /**
     * 请求Id
     */
    private String requestId;

    /**
     * 调用失败返回的数据
     */
    private Map<String, Object> errorData;

    public BaseResult() {
        this.code = 0;
        this.success = true;
        this.message = "successful";
    }

    /**
     * 设置错误信息
     */
    public <R extends BaseResult> R setErrorMessage(int code, String message) {
        this.code = code;
        this.success = false;
        this.message = message;
        return (R) this;
    }
}
