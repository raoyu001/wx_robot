package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class MPSubscription {
    
    private int MPArticleCount;
    
    private MPArticle[] MPArticleList;
    
    private String NickName;
    
    private long Time;
    
    private String UserName;
}
