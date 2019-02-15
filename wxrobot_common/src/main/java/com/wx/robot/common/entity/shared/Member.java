package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class Member {
    
    private long Uin;
    
    private String UserName;
    
    private String NickName;
    
    private String HeadImgUrl;
    
    private int ContactFlag;
    
    private String RemarkName;
    
    private int HideInputBarFlag;
    
    private int Sex;
    
    private String Signature;
    
    private int VerifyFlag;
    
    private String PYInitial;
    
    private String PYQuanPin;
    
    private String RemarkPYInitial;
    
    private String RemarkPYQuanPin;
    
    private int StarFriend;
    
    private long AppAccountFlag;
    
    private long SnsFlag;

}
