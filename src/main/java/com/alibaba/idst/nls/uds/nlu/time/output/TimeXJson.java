package com.alibaba.idst.nls.uds.nlu.time.output;

import com.alibaba.fastjson.serializer.PropertyFilter;

import lombok.Data;

@Data
public class TimeXJson implements PropertyFilter {

    private long time = -1;

    private String type;

    private TimeXTimeJson value;
    private TimeXUnitJson offset;
    private TimeXUnitJson freq;
    private TimeXUnitJson period;

    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object instanceof TimeXJson && value instanceof Long) {
            long v = (long)value;
            return v >= 0;
        }
        return true;
    }
}
