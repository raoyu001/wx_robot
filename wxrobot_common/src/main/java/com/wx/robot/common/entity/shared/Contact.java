package com.wx.robot.common.entity.shared;

import lombok.Data;

import java.util.Set;

@Data
public class Contact extends Member {

    private int MemberCount;

    private Set<ChatRoomMember> MemberList;

    private int OwnerUin;
    
    private long Statues;
    
    private long AttrStatus;
    
    private String Province;
    
    private String City;
    
    private String Alias;
    
    private int UniFriend;
    
    private String DisplayName;
    
    private long ChatRoomId;
    
    private String KeyWord;
    
    private String EncryChatRoomId;
    
    private int IsOwner;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return this.getUserName().equals(contact.getUserName());
    }

    @Override
    public int hashCode() {
        return this.getUserName().hashCode();
    }
}
