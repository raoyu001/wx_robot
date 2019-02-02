package com.wx.robot.common.result;

import java.util.List;
import java.util.Map;

/**
 * @author chenzhen
 * @date 2018/3/24
 */
public class DTOWrapper<T> {

    public static <T> ListResult<T> wrapList(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        return result;
    }

    public static <T> ListResult<T> wrapList(List<T> list, Integer count) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        result.setCount(count);
        return result;
    }

    public static <T> ListResult<T> wrapEmptyList() {
        ListResult<T> result = new ListResult<>();
        result.setCount(0);
        return result;
    }

    public static <T> PlainResult<T> wrapPlain(T b) {
        PlainResult<T> result = new PlainResult<>();
        result.setData(b);
        return result;
    }

    public static <K, V> MapResult<K, V> wrap(Map<K, V> map) {
        MapResult<K, V> result = new MapResult<K, V>();
        result.setData(map);
        return result;
    }

}
