package com.wx.robot.common.entity.shared;

import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class SyncKey {

    private int Count;

    private SyncKeyPair[] List;

    @Override
    public String toString() {
        if (this.List != null) {
            return String.join("|", Arrays.stream(this.List)
                    .map(x -> String.format("%s_%S", x.getKey(), x.getVal())).collect(Collectors.toList()));
        }
        return null;
    }
}
