package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class ChatRoomMember {

    private long Uin;

    private String UserName;

    private String NickName;

    private long AttrStatus;

    private String PYInitial;

    private String PYQuanPin;

    private String RemarkPYInitial;

    private String RemarkPYQuanPin;

    private long MemberStatus;

    private String DisplayName;

    private String Keyword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoomMember that = (ChatRoomMember) o;

        return UserName.equals(that.UserName);
    }

    @Override
    public int hashCode() {
        return UserName.hashCode();
    }
}
