package com.wx.robot.common.entity.shared;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "error")
public class Token {
    @JacksonXmlProperty(localName = "ret")
    private int ret;

    @JacksonXmlProperty(localName = "message")
    private String message;

    @JacksonXmlProperty(localName = "skey")
    private String skey;

    @JacksonXmlProperty(localName = "wxsid")
    private String wxsid;

    @JacksonXmlProperty(localName = "wxuin")
    private String wxuin;

    @JacksonXmlProperty(localName = "pass_ticket")
    private String pass_ticket;

    @JacksonXmlProperty(localName = "isgrayscale")
    private int isgrayscale;
}