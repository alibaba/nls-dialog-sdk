package com.alibaba.idst.nls.uds.nlu.time.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;

public class TimeXOffset {

    private static final Map<String, OffsetFormater> OFFSET_UNIT_ACTION = new HashMap<>();

    static {
        OFFSET_UNIT_ACTION.put("W", (calendar, offset1) -> {
            // TODO: ...
        });
    }

    private String tag;
    private int offset;

    public boolean build(String str) {
        List<String> output = new ArrayList<>();
        if (!TimeXUtil.fullMatch(TimeXUtil.OFFSET_RE, str, output)) {
            return false;
        }
        this.tag = output.get(0);
        this.offset = Integer.parseInt(output.get(1));
        return true;
    }

    public void ground(Calendar calendar) {
        if (TimeXUtil.UNIT_MAP.containsKey(tag)) {
            calendar.add(TimeXUtil.UNIT_MAP.get(tag), offset);
        } else if (OFFSET_UNIT_ACTION.containsKey(tag)) {
            OFFSET_UNIT_ACTION.get(tag).run(calendar, offset);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private interface OffsetFormater {
        void run(Calendar calendar, int offset);
    }
}
