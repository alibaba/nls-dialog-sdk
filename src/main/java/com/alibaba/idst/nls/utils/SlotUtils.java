package com.alibaba.idst.nls.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.idst.nlu.response.common.Slot;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SlotUtils {

    private static String regex = "\\{(.*?)\\}";

    private static Pattern pattern = Pattern.compile(regex);

    /**
     * 解析utterance中的slot，并赋值
     * @param utterance
     * @param slots
     * @return
     */
    public static String parseUtterance(String utterance, Map<String, List<Slot>> slots) {
        String slotData = JSON.toJSONString(slots);
        log.info("utterance:{} slots:{}", utterance, slotData);
        if(Strings.isNullOrEmpty(utterance)) {
            return "";
        }
        if(slots == null || slots.isEmpty()) {
            return utterance;
        }

        String str = utterance;
        Matcher matcher = pattern.matcher(utterance);
        while (matcher.find()) {
            String group = matcher.group();
            String slotName = group.substring(1, group.length() - 1);
            String slotVal = normValue(slotData, slotName);
            str = str.replace(group, slotVal);
        }
        return str;
    }

    public static String normValue(String slotData, String slotName) {
        JSONObject root = JSON.parseObject(slotData);
        JSONArray array = root.getJSONArray(slotName);
        if (array == null || array.isEmpty()) {
            return "";
        }
        String val;
        switch (slotName) {
            case "geo":
                val = parseGeo(array);
                break;
            case "time":
            case "date":
                val = parseTime(array);
                break;
            default:
                val = parseBasic(array);
                break;
        }
        return val;
    }

    private static String parseBasic(JSONArray array) {
        JSONObject data = array.getJSONObject(0);
        if (data != null) {
            return data.getString("norm");
        }
        return "";
    }

    private static String parseGeo(JSONArray array) {
        JSONObject data = array.getJSONObject(0);
        if (data != null) {
            if(data.containsKey("tag")) {
                if(data.getString("tag").contains("USER.")) {
                    return data.getString("norm");
                }
            }
            return data.getJSONObject("level_3").getString("norm");
        }
        return "";
    }

    private static String parseTime(JSONArray array) {
        JSONObject data = array.getJSONObject(0);
        if (data != null) {
            if(data.containsKey("tag")) {
                if(data.getString("tag").contains("USER.")) {
                    return data.getString("norm");
                }
            }
            return data.getJSONObject("timex").getString("value");
        }
        return "";
    }

}