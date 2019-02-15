package com.wx.robot.common.entity.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "msg")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendInvitationContent {
    @JacksonXmlProperty
    private String fromusername;
    @JacksonXmlProperty
    private String encryptusername;
    @JacksonXmlProperty
    private String fromnickname;
    @JacksonXmlProperty
    private String content;
    @JacksonXmlProperty
    private String shortpy;
    @JacksonXmlProperty
    private long imagestatus;
    @JacksonXmlProperty
    private int scene;
    @JacksonXmlProperty
    private String country;
    @JacksonXmlProperty
    private String province;
    @JacksonXmlProperty
    private String city;
    @JacksonXmlProperty
    private int percard;
    @JacksonXmlProperty
    private int sex;
    @JacksonXmlProperty
    private String alias;
    @JacksonXmlProperty
    private long albumflag;
    @JacksonXmlProperty
    private int albumstyle;
    @JacksonXmlProperty
    private String albumbgimgid;
    @JacksonXmlProperty
    private long snsflag;
    @JacksonXmlProperty
    private String snsbgimgid;
    @JacksonXmlProperty
    private String snsbgobjectid;
    @JacksonXmlProperty
    private String mhash;
    @JacksonXmlProperty
    private String mfullhash;
    @JacksonXmlProperty
    private String bigheadimgurl;
    @JacksonXmlProperty
    private String smallheadimgurl;
    @JacksonXmlProperty
    private String ticket;
    @JacksonXmlProperty
    private int opcode;
}
