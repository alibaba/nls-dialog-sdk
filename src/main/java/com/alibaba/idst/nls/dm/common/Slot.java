package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author niannian.ynn Slot的运行时内容
 */
@Data
public class Slot {
    private final SlotDef slotDef;

    /**
     * Slot的值，允许多个
     */
    private final List<SlotValue> slotValues = new ArrayList<>();

    /**
     * 对应domainOntology的slot定义
     */
    public Slot(SlotDef slotDef) {
        this.slotDef = slotDef;
    }

    public String name() {
        return slotDef.getName();
    }
}
