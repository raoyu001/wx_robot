package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class Message {
    
    private String MsgId;
    
    private String FromUserName;
    
    private String ToUserName;
    
    private int MsgType;
    
    private String Content;
    
    private long Status;
    
    private long ImgStatus;
    
    private long CreateTime;
    
    private long VoiceLength;
    
    private long PlayLength;
    
    private String FileName;
    
    private String FileSize;
    
    private String MediaId;
    
    private String Url;
    
    private int AppMsgType;
    
    private int StatusNotifyCode;
    
    private String StatusNotifyUserName;
    
    private RecommendInfo RecommendInfo;
    
    private int ForwardFlag;
    
    private AppInfo AppInfo;
    
    private int HasProductId;
    
    private String Ticket;
    
    private int ImgHeight;
    
    private int ImgWidth;
    
    private int SubMsgType;
    
    private long NewMsgId;
    
    private String OriContent;
}
