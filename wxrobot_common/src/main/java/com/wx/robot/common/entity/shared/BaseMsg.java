package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class BaseMsg {

    private int Type;

    private String Content;

    private String FromUserName;

    private String ToUserName;

    private String LocalID;

    private String ClientMsgId;
}


