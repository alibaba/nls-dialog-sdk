package com.alibaba.idst.nls.uds.nlu.time.entity;

public class TimeXValueOfSet extends TimeXValueOfTime {

    private TimeXSetValueType type = null;
    private String tag;

    @Override
    public boolean build(String str) {
        TimeXValueOfDate tmpDate = new TimeXValueOfDate();
        // 尝试使用date解析
        if (tmpDate.build(str)) {
            this.fromTimeXValueOfDate(tmpDate);
            // 但是转回time格式
            this.type = TimeXSetValueType.TDATE;
        } else if (super.build(str)) { //尝试使用time解析 
            this.type = TimeXSetValueType.TTIME;
        }

        return type != null;
        // type 为空，解析失败
    }

    public enum TimeXSetValueType {
        TDATE,
        TTIME,
        TTAG
    }
}
