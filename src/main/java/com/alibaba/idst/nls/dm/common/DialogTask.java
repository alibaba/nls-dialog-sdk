package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author niannian.ynn DialogTask代表着任务（intent）
 */
@Data
public class DialogTask {
    private final Intent intent;
    private final int taskId;
    /**
     * QUD 信息
     */
    private final Map<String, SlotDef> qudSlots = new HashMap<>();
    /**
     * 虚拟slot
     */
    private final Map<String, Slot> virtualSlots = new HashMap<>();

    /**
     * 任务相关的数据存储区
     */
    private final Map<String, String> taskContext = new HashMap<>();

    public DialogTask(IntentDef intentDef, int taskId) {
        this.intent = new Intent(intentDef);
        this.taskId = taskId;
    }

    public String getDomainName() {
        return intent.getIntentDef().getDomain();
    }

    public String getIntentName() {
        return intent.getIntentDef().getIntent();
    }

    public List<SlotDef> getAllSlotDef() {
        return intent.getIntentDef().getSlotDefs();
    }

    /**
     * 根据名字获得Slot（包含虚拟slot)
     */
    public Slot getSlot(String name) {
        Slot slot = intent.getSlotValueMap().get(name);
        if (slot != null) {
            return slot;
        }
        return virtualSlots.get(name);
    }

    /**
     * 设置slot值
     */
    public void setSlotValues(String slotName, List<SlotValue> slotValues) {
        Slot slot = getSlot(slotName);
        if (slot != null) {
            slot.getSlotValues().clear();
            slot.getSlotValues().addAll(slotValues);
        }
    }

    public void addSlotValues(String slotName, SlotValue slotValue) {
        Slot slot = getSlot(slotName);
        if (slot != null) {
            slot.getSlotValues().add(slotValue);
        }
    }

    /**
     * 获取可以放指定值类型的SLot
     */
    public List<Slot> getSlotsByType(List<String> slotTypeList) {
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : intent.getSlotValueMap().values()) {
            for (String slotType : slotTypeList) {
                if (slot.getSlotDef().getType().contains(slotType)) {
                    slots.add(slot);
                    break;
                }
            }
        }
        for (Slot slot : virtualSlots.values()) {
            for (String slotType : slotTypeList) {
                if (slot.getSlotDef().getType().contains(slotType)) {
                    slots.add(slot);
                    break;
                }
            }
        }
        return slots;
    }
}
