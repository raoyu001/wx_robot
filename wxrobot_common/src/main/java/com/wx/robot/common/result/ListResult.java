package com.wx.robot.common.result;

import lombok.Data;

import java.util.List;

/**
 * @author chenzhen
 * @date 2018/3/22
 */
@Data
public class ListResult<T> extends BaseResult {

    private static final long serialVersionUID = -6058763157094169276L;

    private List<T> data;

    /**
     * 本次查询的对应的所有条目数量
     */
    private int count;
}
