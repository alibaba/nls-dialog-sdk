package com.alibaba.idst.nls.uds.nlu.time;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.alibaba.fastjson.JSON;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfDate;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;
import com.alibaba.idst.nls.uds.nlu.time.output.TimeXJson;

import lombok.Data;

@Data
public class TimeXPointOperator implements TimeXOperator {

    private static ThreadLocal<DateFormat> FORMATOR = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private Calendar calendar = new GregorianCalendar();
    private TimeXValueOfTime timex;
    private TimeX origin;

    public TimeXPointOperator(Date value, TimeXValueOfTime timex, TimeX origin) {
        this.timex = timex;
        this.origin = origin;
        calendar.setTime(value);
    }

    public TimeXPointOperator copy() {
        TimeXPointOperator operCompare = new TimeXPointOperator(calendar.getTime(), timex, origin);
        return operCompare;
    }

    public boolean isUnknownNoon() {
        return this.timex.isUnknownNoon();
    }

    public void setTo(long time) {
        calendar.setTime(new Date(time));
    }

    public void addTime(int hour, int min, int second) {
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, min);
        calendar.add(Calendar.SECOND, second);
    }

    @Override
    public String get(String field) {
        if (field == null) {
            return toJson();
        }
        if (TimeXUtil.UNIT_MAP.containsKey(field)) {
            int offset = 0;
            if ("M".equals(field)) {
                offset = 1;
            }
            return Integer.toString(calendar.get(TimeXUtil.UNIT_MAP.get(field)) + offset);
        }
        // 返回原始的输入是否包含关注的字段，年月日等 TODO(lincheng): 是否需要补充一些常见的用法？
        if (TimeXUtil.TAGGER_MAP.containsKey(field)) {
            return Boolean.toString(timex.getValueTagger(TimeXUtil.TAGGER_MAP.get(field)));
        }
        // for other field
        if ("start_time_unix".equals(field) || (TimeXPeriodOperator.PERIOD_PREFIX + "start_time_unix").equals(field)) {
            return Long.toString(calendar.getTimeInMillis());
        }
        if ("start_time".equals(field) || (TimeXPeriodOperator.PERIOD_PREFIX + "start_time").equals(field)) {
            return FORMATOR.get().format(calendar.getTime());
        }
        if (field.startsWith("end_time") || field.startsWith(TimeXPeriodOperator.PERIOD_PREFIX + "end_time")) {
            Date endDate = TimeXUtil.getEndTime(calendar);
            if ("end_time_unix".equals(field) || (TimeXPeriodOperator.PERIOD_PREFIX + "end_time_unix").equals(field)) {
                return Long.toString(endDate.getTime());
            } else if ("end_time".equals(field) || (TimeXPeriodOperator.PERIOD_PREFIX + "end_time").equals(field)) {
                return FORMATOR.get().format(endDate);
            }
        }
        if ("raw".equals(field)) {
            return getRaw();
        }
        return "";
    }

    public void setTime(int hour, int min, int second) {
        if (hour >= 0 && hour < 24) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (min >= 0 && min < 60) {
            calendar.set(Calendar.MINUTE, min);
        }
        if (second >= 0 && second < 60) {
            calendar.set(Calendar.SECOND, second);
        }
    }

    public void addDate(int year, int mouth, int day) {
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, mouth);
        calendar.add(Calendar.DATE, day);
    }

    public void setDate(int year, int mouth, int day) {
        if (year >= 0) {
            calendar.set(Calendar.YEAR, year);
        }
        if (mouth > 0 && mouth <= 12) {
            // start from 0
            calendar.set(Calendar.MONTH, mouth - 1);
        }
        if (day > 0 && day <= 31) {
            calendar.set(Calendar.DATE, day);
        }
    }

    public boolean isBefore(TimeXPointOperator timeOp) {
        return calendar.getTime().before(timeOp.calendar.getTime());
    }

    public boolean isBefore(long utime) {
        return calendar.getTime().before(new Date(utime));
    }

    public boolean isAfter(TimeXPointOperator timeOp) {
        return calendar.getTime().after(timeOp.calendar.getTime());
    }

    public boolean isAfter(long utime) {
        return calendar.getTime().after(new Date(utime));
    }

    public void setToNearest(long localTime, String change) {
        if (!TimeXUtil.UNIT_MAP.containsKey(change)) {
            return;
        }
        int field = TimeXUtil.UNIT_MAP.get(change);
        Date cmp = new Date(localTime);
        if (calendar.after(cmp)) {
            while (calendar.after(cmp)) {
                calendar.add(field, -1);
            }
            // make sure after that
            calendar.add(field, 1);
        } else { // before
            while (calendar.before(cmp)) {
                calendar.add(field, 1);
            }
            // make sure before that
            calendar.add(field, -1);
        }
    }

    public long getUnixTime() {
        return calendar.getTimeInMillis();
    }

    public String toJson() {
        TimeXJson json = new TimeXJson();
        json.setTime(calendar.getTime().getTime());
        if (timex instanceof TimeXValueOfTime) {
            json.setType("TIME");
        } else if (timex instanceof TimeXValueOfDate) {
            json.setType("DATE");
        } else {
            json.setType("OTHER");
        }
        return JSON.toJSONString(json);
    }

    public String toDateString() {
        return calendar.getTime().toString();
    }

    public String toText() {
        return timex.getRaw();
    }

    public String getRaw() {
        return origin.getRaw();
    }
}
