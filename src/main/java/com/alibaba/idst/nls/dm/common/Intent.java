package com.alibaba.idst.nls.dm.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author niannian.ynn
 */

@Data
public class Intent {
    private final IntentDef intentDef;
    private final Map<String, Slot> slotValueMap = new HashMap<>();
    private double probability = 0.0;
    private String source;

    public Intent(IntentDef intentDef) {
        this.intentDef = intentDef;
        for (SlotDef slotDef : intentDef.getSlotDefs()) {
            slotValueMap.put(slotDef.getName(), new Slot(slotDef));
        }
    }

    public static boolean isMatch(Intent i1, Intent i2) {
        return (i1.getIntentDef().getDomain().equalsIgnoreCase(i2.getIntentDef().getDomain())
            && i1.getIntentDef().getIntent().equalsIgnoreCase(i2.getIntentDef().getIntent()));
    }
}
