package com.alibaba.idst.nls.dm.common.io;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author junfeng.lijf
 * cache
 */
@Data
public class Cache {
    public Map<String, CacheItem> cacheDict = new HashMap<>();

    public void put(String k, String v) {
        int timeStamp = (int)(java.lang.System.currentTimeMillis() / 1000);
        CacheItem cacheItem = new CacheItem();
        cacheItem.setTimeStamp(timeStamp);
        cacheItem.setContent(v);
        cacheDict.put(k, cacheItem);
    }

    public boolean hasKey(String k) {
        return cacheDict.containsKey(k);
    }

    private CacheItem get(String k) {
        return cacheDict.get(k);
    }

    public String getContent(String k) {
        CacheItem cacheItem = get(k);
        if (cacheItem != null) {
            return cacheItem.getContent();
        }
        return null;
    }

    public boolean isExpired(String k, int threshHold) {
        CacheItem cacheItem = get(k);
        if (cacheItem != null) {
            int now = (int)(java.lang.System.currentTimeMillis() / 1000);
            return now >= threshHold + cacheItem.timeStamp;
        }
        return true;
    }

    public void delete(String k) {
        cacheDict.remove(k);
    }

    @Data
    public static class CacheItem {
        public int timeStamp;
        public String content;
    }
}
