package com.wx.robot.common.entity.shared;

import lombok.Data;

@Data
public class SyncCheckKey {
    private int Count;
    private SyncCheckKeyPair[] List;
}
