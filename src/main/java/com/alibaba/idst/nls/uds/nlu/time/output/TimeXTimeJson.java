package com.alibaba.idst.nls.uds.nlu.time.output;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;

import lombok.Data;

@Data
public class TimeXTimeJson implements PropertyFilter {

    private boolean isLunar = false;

    private int year = -1;
    private int mouth = -1;
    private int day = -1;

    private int hour = -1;
    private int minute = -1;
    private int second = -1;

    private String tag;

    public void copyValue(TimeXValueOfTime time) {
        isLunar = time.isLunar();
        if (!TimeXUtil.isNullOrUnknownTag(time.getYear())) {
            year = Integer.parseInt(time.getYear());
        }
        if (!TimeXUtil.isNullOrUnknownTag(time.getMouth())) {
            mouth = Integer.parseInt(time.getMouth());
        }
        if (!TimeXUtil.isNullOrUnknownTag(time.getDay())) {
            day = Integer.parseInt(time.getDay());
        }
        if (!TimeXUtil.isNullOrUnknownTag(time.getHour())) {
            hour = Integer.parseInt(time.getHour());
        }
        if (!TimeXUtil.isNullOrUnknownTag(time.getMinute())) {
            minute = Integer.parseInt(time.getMinute());
        }
        if (!TimeXUtil.isNullOrUnknownTag(time.getSecond())) {
            second = Integer.parseInt(time.getSecond());
        }
    }

    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object instanceof TimeXTimeJson) { // just for this class
            if (value instanceof Boolean) {
                return (boolean)value;
            }
            if (value instanceof Integer) {
                int intValue = (int)value;
                return intValue >= 0;
            }
        }
        return true;
    }
}
