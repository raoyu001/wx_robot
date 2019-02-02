package com.wx.robot.common.utils;

import com.google.common.base.Preconditions;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

public class HeaderUtils {
    public static HttpHeaders merge(HttpHeaders... headers) {
        Preconditions.checkNotNull(headers);
        if (headers.length == 1) {
            return headers[0];
        }
        return assign(new HttpHeaders(),headers);
    }

    public static HttpHeaders assign(HttpHeaders header, HttpHeaders... headers) {
        Preconditions.checkNotNull(header);
        Preconditions.checkNotNull(headers);

        Arrays.stream(headers).forEach(item -> header.setAll(item.toSingleValueMap()));
        return header;
    }
}
