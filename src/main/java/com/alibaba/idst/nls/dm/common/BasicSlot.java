package com.alibaba.idst.nls.dm.common;

/**
 * @author niannian.ynn
 * 为了兼容老nlu用
 */
public class BasicSlot extends SlotValue {
    public BasicSlot(SlotValue value) {
        this.setNorm(value.getNorm());
        this.setRaw(value.getRaw());
        this.setScore(value.getScore());
        this.setTag(value.getTag());
    }
}
