package com.alibaba.idst.nls.uds.nlu.time.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;

public class TimeXDurationValue {

    private int year;
    private int mouth;
    private int week;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean build(String str) {
        List<String> output = new ArrayList<>();
        if (!TimeXUtil.fullMatch(TimeXUtil.DURATION_VALUE_RE, str, output)) {
            return false;
        }
        int idx = 1;
        if (output.get(idx) != null) {
            year = Integer.parseInt(output.get(idx));
        }
        idx = 3;
        if (output.get(idx) != null) {
            mouth = Integer.parseInt(output.get(idx));
        }
        idx = 5;
        if (output.get(idx) != null) {
            week = Integer.parseInt(output.get(idx));
        }
        idx = 7;
        if (output.get(idx) != null) {
            day = Integer.parseInt(output.get(idx));
        }
        idx = 9;
        if (output.get(idx) != null) {
            hour = Integer.parseInt(output.get(idx));
        }
        idx = 11;
        if (output.get(idx) != null) {
            minute = Integer.parseInt(output.get(idx));
        }
        idx = 13;
        if (output.get(idx) != null) {
            second = Integer.parseInt(output.get(idx));
        }
        return true;
    }

    /**
     * 这个只负责近似计算
     *
     * @return
     */
    public long getDuration() {
        return (long)second + minute * 60L + hour * 3600L + day * 86400L + 86400L * (mouth * 30L + year * 365L);
    }

}
