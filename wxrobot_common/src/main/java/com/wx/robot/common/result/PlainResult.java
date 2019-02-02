package com.wx.robot.common.result;

import lombok.Data;

/**
 * @author chenzhen
 * @date 2018/3/22
 */
@Data
public class PlainResult<T> extends BaseResult {

    private static final long serialVersionUID = 4159608495918106556L;

    private T data;
}
