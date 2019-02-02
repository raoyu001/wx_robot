package com.wx.robot.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzhen
 * @date 2018/3/22
 */
@Data
public class MapResult<K, V> extends BaseResult {

    private static final long serialVersionUID = 7444857640252579657L;

    // data field
    private Map<K, V> data;

    /**
     * 增加一个k/v
     *
     * @param key
     * @param val
     * @return MapResult<K, V>
     */
    public MapResult<K, V> add(K key, V val) {
        if (data == null) {
            data = new HashMap<K, V>();
        }
        data.put(key, val);
        return this;
    }

}
