package com.alibaba.idst.nls.uds.nlu.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeXDurationOperator implements TimeXOperator {

    public static final String DURATION = "duration";
    public static final String START_TIME_UNIX = "start_time_unix";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String END_TIME_UNIX = "end_time_unix";
    public static final String RAW = "raw";
    private static ThreadLocal<DateFormat> FORMATOR = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private TimeX origin;
    private long duration;
    private Calendar startCalendar;
    private Calendar localCalendar;
    private Calendar endCalendar;

    public TimeXDurationOperator(Date localDate, Date start, Date end, long duration, TimeX origin) {
        this.origin = origin;
        this.duration = duration;
        this.localCalendar = new GregorianCalendar();
        this.localCalendar.setTime(localDate);
        if (start != null) {
            startCalendar = new GregorianCalendar();
            startCalendar.setTime(start);
        }
        if (end != null) {
            endCalendar = new GregorianCalendar();
            endCalendar.setTime(end);
        }
    }

    public TimeXDurationOperator copy() {
        return null;
    }

    @Override
    public String get(String field) {
        if (field == null) {
            return null;
        }
        if (DURATION.equals(field)) {
            return Long.toString(duration);
        }
        if (START_TIME_UNIX.equals(field)) {
            if (startCalendar == null) {
                // TODO(lincheng): WHY do this ?! set this local time as default
                return Long.toString(localCalendar.getTimeInMillis());
            }
            return Long.toString(startCalendar.getTimeInMillis());
        }
        if (START_TIME.equals(field)) {
            if (startCalendar == null) {
                // TODO(lincheng): WHY do this ?! set this local time as default
                return FORMATOR.get().format(localCalendar.getTime());
            }
            return FORMATOR.get().format(startCalendar.getTime());
        }
        if (field.startsWith(END_TIME)) {
            if (endCalendar == null) {
                if (END_TIME_UNIX.equals(field)) {
                    return Long.toString(localCalendar.getTimeInMillis() + duration);
                } else if (END_TIME.equals(field)) {
                    Calendar tmp = new GregorianCalendar();
                    tmp.setTime(localCalendar.getTime());
                    tmp.add(Calendar.SECOND, (int)duration);
                    return FORMATOR.get().format(tmp.getTime());
                }
            }
            Date endDate = TimeXUtil.getEndTime(endCalendar);
            if (END_TIME_UNIX.equals(field)) {
                return Long.toString(endDate.getTime());
            } else if (END_TIME.equals(field)) {
                return FORMATOR.get().format(endDate);
            }
        }
        if (RAW.equals(field)) {
            return getRaw();
        }
        return "";
    }

    public String getRaw() {
        return origin.getRaw();
    }
}
