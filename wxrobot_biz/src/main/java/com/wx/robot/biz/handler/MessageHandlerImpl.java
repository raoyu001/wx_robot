package com.wx.robot.biz.handler;

import com.wx.robot.common.entity.shared.ChatRoomMember;
import com.wx.robot.common.entity.shared.Contact;
import com.wx.robot.common.entity.shared.Message;
import com.wx.robot.common.entity.shared.RecommendInfo;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MessageHandlerImpl implements MessageHandler {

    @Override
    public void onReceivingChatRoomTextMessage(Message message) {

    }

    @Override
    public void onReceivingChatRoomImageMessage(Message message, String thumbImageUrl, String fullImageUrl) {

    }

    @Override
    public void onReceivingPrivateTextMessage(Message message) {

    }

    @Override
    public void onReceivingPrivateImageMessage(Message message, String thumbImageUrl, String fullImageUrl) {

    }

    @Override
    public boolean onReceivingFriendInvitation(RecommendInfo info) {
        return false;
    }

    @Override
    public void postAcceptFriendInvitation(Message message) {

    }

    @Override
    public void onChatRoomMembersChanged(Contact chatRoom, Set<ChatRoomMember> membersJoined, Set<ChatRoomMember> membersLeft) {

    }

    @Override
    public void onNewChatRoomsFound(Set<Contact> chatRooms) {

    }

    @Override
    public void onChatRoomsDeleted(Set<Contact> chatRooms) {

    }

    @Override
    public void onNewFriendsFound(Set<Contact> contacts) {

    }

    @Override
    public void onFriendsDeleted(Set<Contact> contacts) {

    }

    @Override
    public void onNewMediaPlatformsFound(Set<Contact> mps) {

    }

    @Override
    public void onMediaPlatformsDeleted(Set<Contact> mps) {

    }

    @Override
    public void onRedPacketReceived(Contact contact) {

    }
}
