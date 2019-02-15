package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class Profile {
    
    private int BitFlag;

    private Object UserName;

    private Object NickName;

    private Object BindEmail;

    private Object BindMobile;
    
    private long BindUin;
    
    private long Status;
    
    private int Sex;
    
    private int PersonalCard;
    
    private String Alias;
    
    private int HeadImgUpdateFlag;
    
    private String HeadImgUrl;
    
    private String Signature;
}
