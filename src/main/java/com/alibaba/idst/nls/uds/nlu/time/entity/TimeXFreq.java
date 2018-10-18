package com.alibaba.idst.nls.uds.nlu.time.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;

public class TimeXFreq {

    private String unit;
    private int value;

    public boolean build(String str) {
        List<String> output = new ArrayList<>();
        if (!TimeXUtil.fullMatch(TimeXUtil.FREQ_RE, str, output)) {
            if (!TimeXUtil.fullMatch(TimeXUtil.PERIODICITY_RE, str, output)) {
                return false;
            }
        }
        this.value = Integer.parseInt(output.get(0));
        this.unit = output.get(1);
        return true;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
