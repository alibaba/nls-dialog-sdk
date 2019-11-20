package com.alibaba.idst.nls.dm.common.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.idst.nls.dm.common.BasicSlot;
import com.alibaba.idst.nls.dm.common.SlotValue;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class NluResultElement {
    private String domain;
    private String intent;
    private String intentionName;
    @SerializedName("classify_type")
    private String classifyType;
    private double score;
    private String source;
    private boolean fully;
    private Map<String, List<SlotValue>> slots = new HashMap<>();
    private List<String> TriggerPluginList;
    // ---------- context nlu 需要添加 -------------------
    /** 字节数 */
    private int count;
    /** 起始位置 */
    private int offset;

    /**
     * 为了兼容老nlu接口
     */
    public Map<String, List<BasicSlot>> getSlotMap() {
        Map<String, List<BasicSlot>> map = new HashMap<>();
        if (slots != null) {
            for (Map.Entry<String, List<SlotValue>> entry : slots.entrySet()) {
                List<BasicSlot> value = new ArrayList<>();
                if (entry.getValue() != null && entry.getValue().size() > 0) {
                    for (SlotValue slotValue : entry.getValue()) {
                        value.add(new BasicSlot(slotValue));
                    }
                    map.put(entry.getKey(), value);
                }
            }
        }
        return map;
    }

    /**
     * 为了兼容老nlu接口
     */
    public BasicSlot getSlot(String slotName) {
        List<SlotValue> slot = slots.get(slotName);
        if (slot != null && slot.size() > 0) {
            return new BasicSlot(slot.get(0));
        }
        return null;
    }

    public String getSlotStringValue(String slotName) {
        BasicSlot slot = getSlot(slotName);
        if (slot != null) {
            return slot.getRaw();
        }
        return null;
    }
}
