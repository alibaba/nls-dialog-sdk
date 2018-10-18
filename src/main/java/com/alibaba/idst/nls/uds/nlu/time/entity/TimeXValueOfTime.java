package com.alibaba.idst.nls.uds.nlu.time.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimeXValueOfTime extends TimeXValueOfDate {

    private boolean isUnknownNoon = false;
    private String second;
    private String hour;
    private String minute;
    private String timeTag;

    public TimeXValueOfTime() {
    }

    public TimeXValueOfTime(TimeXValueOfDate date) {
        this.year = date.year;
        this.mouth = date.mouth;
        this.day = date.day;
        this.dateTimeTag = date.dateTimeTag;
        this.isLunar = date.isLunar;
        this.weekNumber = date.weekNumber;
        // add by lincheng
        this.valueTag = date.valueTag;
    }

    public TimeXValueOfTime copy() {
        TimeXValueOfTime newTime = new TimeXValueOfTime(this);
        newTime.isUnknownNoon = this.isUnknownNoon;
        newTime.second = this.second;
        newTime.hour = this.hour;
        newTime.minute = this.minute;
        newTime.timeTag = this.timeTag;
        return newTime;
    }

    @Override
    public boolean build(String str) {
        List<String> output = new ArrayList<>();
        if (!TimeXUtil.fullMatch(TimeXUtil.TIME_VALUE_RE, str, output)) {
            if (!TimeXUtil.fullMatch(TimeXUtil.TIME_VALUE_TAG_RE, str, output)) {
                return false;
            } else {
                this.timeTag = output.get(12);
                setValueTagger(TimeXUtil.HasValue.TIME_TAG);
            }
        } else {
            this.isUnknownNoon = (output.get(12) != null);
            if (!this.isUnknownNoon) {
                setValueTagger(TimeXUtil.HasValue.AM_PM);
            }
            this.hour = output.get(13) != null ? output.get(13) : this.hour;
            if (!TimeXUtil.isNullOrUnknownTag(this.hour)) {
                setValueTagger(TimeXUtil.HasValue.HOUR);
            }
            this.minute = output.get(14) != null ? output.get(14) : this.minute;
            if (!TimeXUtil.isNullOrUnknownTag(this.minute)) {
                setValueTagger(TimeXUtil.HasValue.MINUTE);
            }
            this.second = output.get(16) != null ? output.get(16) : this.second;
            if (!TimeXUtil.isNullOrUnknownTag(this.second)) {
                setValueTagger(TimeXUtil.HasValue.SECOND);
            }
        }
        this.isLunar = (output.get(1) != null);
        if (this.isLunar) {
            setValueTagger(TimeXUtil.HasValue.LUNAR);
        }
        this.year = output.get(2);
        if (!TimeXUtil.isNullOrUnknownTag(this.year)) {
            setValueTagger(TimeXUtil.HasValue.YEAR);
        }
        if (this.year != null) {
            if (output.get(5) == null) { // this is week or tag
                if (output.get(10) == null) {
                    this.dateTimeTag = output.get(3);
                    if (!TimeXUtil.isNullOrUnknownTag(this.dateTimeTag)) {
                        setValueTagger(TimeXUtil.HasValue.DATE_TAG);
                    }
                } else {
                    this.dateTimeTag = output.get(8);
                    if (!TimeXUtil.isNullOrUnknownTag(this.dateTimeTag)) {
                        setValueTagger(TimeXUtil.HasValue.DATE_TAG);
                    }
                    if (!TimeXUtil.isUnknownTag(output.get(10))) {
                        this.weekNumber = Integer.parseInt(output.get(10));
                        setValueTagger(TimeXUtil.HasValue.WEEK);
                    }
                }
            } else {
                this.mouth = output.get(5);
                if (!TimeXUtil.isNullOrUnknownTag(this.mouth)) {
                    setValueTagger(TimeXUtil.HasValue.MOUTH);
                }
                this.day = output.get(6);
                if (!TimeXUtil.isNullOrUnknownTag(this.day)) {
                    setValueTagger(TimeXUtil.HasValue.DAY);
                }
            }
        }
        return true;
    }

    protected Date timeInnerToDate(TimeXOffset offset) {
        Calendar calendar = new GregorianCalendar();
        // timex date to date, ignore offset
        calendar.setTime(dateInnerToDate(null));
        // whatever unkonwn noon or not
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        if (second != null) {
            calendar.set(Calendar.SECOND, Integer.parseInt(second));
        }
        if (offset != null) {
            offset.ground(calendar);
        }
        return calendar.getTime();
    }

    @Override
    public Date toDate(TimeXOffset offset) {
        return timeInnerToDate(offset);
    }

    @Override
    public Date toDate() {
        return timeInnerToDate(null);
    }

    @Override
    public void ground(Date now) {
        super.ground(now, true);
        // ground to 00 if not exist value
        if (TimeXUtil.isNullOrUnknownTag(hour)) {
            hour = "00";
        }
        if (TimeXUtil.isNullOrUnknownTag(minute)) {
            minute = "00";
        }
        if (TimeXUtil.isNullOrUnknownTag(second)) {
            second = "00";
        }
    }
}
