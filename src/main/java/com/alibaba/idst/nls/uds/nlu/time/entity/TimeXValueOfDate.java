package com.alibaba.idst.nls.uds.nlu.time.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.alibaba.idst.nls.uds.nlu.time.LunarCalendar;
import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;

import org.apache.commons.lang3.StringUtils;

public class TimeXValueOfDate {

    protected boolean isLunar = false;
    protected String year;
    protected String raw;
    protected String mouth;
    protected String day;
    protected String dateTimeTag;
    protected int weekNumber = 0;
    // 默认是false
    protected boolean[] valueTag = new boolean[TimeXUtil.HasValue.values().length];

    public TimeXValueOfDate() {}

    public TimeXValueOfDate(TimeXValueOfDate from) {
        this.fromTimeXValueOfDate(from);
    }

    protected Date dateInnerToDate(TimeXOffset offset) {
        Calendar calendar = new GregorianCalendar();
        if (!isLunar) {
            // set fields
            calendar.set(Calendar.YEAR, Integer.parseInt(year));
            // start from 0...
            calendar.set(Calendar.MONTH, Integer.parseInt(mouth) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        } else {
            // lunar
            // TODO: add
        }
        // reset left fields
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // apply offset
        if (offset != null) {
            offset.ground(calendar);
        }
        return calendar.getTime();
    }

    public Date toDate(TimeXOffset offset) {
        return dateInnerToDate(offset);
    }

    public Date toDate() {
        return dateInnerToDate(null);
    }

    public void ground(Date now) {
        ground(now, false);
    }

    public void ground(Date now, boolean isTimeGround) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        if (isUnknown(year)) {
            this.year = Integer.toString(calendar.get(Calendar.YEAR));
        }
        if (weekNumber > 0) {
            // calendar.setFirstDayOfWeek(Calendar.SUNDAY);
            if (StringUtils.isBlank(dateTimeTag)) {
                calendar.setWeekDate(calendar.get(Calendar.YEAR), weekNumber, Calendar.MONDAY);
            } else {
                calendar.setWeekDate(calendar.get(Calendar.YEAR), weekNumber, TimeXUtil.WEEK_INFO.get(dateTimeTag));
                // reset this tag after ground
                dateTimeTag = null;
            }
            // reset week number after ground
            weekNumber = 0;
            this.mouth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            this.day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            // if (mouth != null && TimeXUtil.isUnknownTag(mouth)) {
            if ((mouth != null && TimeXUtil.isUnknownTag(mouth)) || (isTimeGround && TimeXUtil.isNullOrUnknownTag(
                mouth))) {
                this.mouth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            }
            // if (day != null && TimeXUtil.isUnknownTag(day)) {
            if ((day != null && TimeXUtil.isUnknownTag(day)) || (isTimeGround && TimeXUtil.isNullOrUnknownTag(day))) {
                this.day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            }
        }

        if (isLunar) { // 农历处理
            // TODO: if mouth grounded or day grounded
            Calendar newCalendar = LunarCalendar.lunarToSolar(Integer.parseInt(this.year),
                Integer.parseInt(this.mouth), Integer.parseInt(this.day));
            isLunar = false;
            this.year = Integer.toString(newCalendar.get(Calendar.YEAR));
            // mouth is 0-based
            this.mouth = Integer.toString(newCalendar.get(Calendar.MONTH) + 1);
            this.day = Integer.toString(newCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    protected boolean isUnknown(String str) {
        return StringUtils.isBlank(str) || TimeXUtil.isUnknownTag(str);
    }

    public boolean build(String str) {
        List<String> output = new ArrayList<>();
        if (!TimeXUtil.fullMatch(TimeXUtil.DATE_VALUE_RE, str, output)) { // XX XX-XX-XX
            if (!TimeXUtil.fullMatch(TimeXUtil.DATE_VALUE_TAG_RE, str, output)) { // XXXX-GQ & XXXX-W23 & XXX-WSA12
                return false;
            } else {
                if (output.get(7) == null) {
                    this.dateTimeTag = output.get(3);
                    setValueTagger(TimeXUtil.HasValue.DATE_TAG);
                } else {
                    if (output.get(6) != null) {
                        this.dateTimeTag = output.get(6);
                        setValueTagger(TimeXUtil.HasValue.DATE_TAG);
                    }
                    // weekNumber有可能是XX
                    if (StringUtils.isNumeric(output.get(7))) {
                        this.weekNumber = Integer.parseInt(output.get(7));
                    } else {
                        // TODO(lincheng): 这个ground没办法只能放到这里了
                        this.weekNumber = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
                        // TODO(lincheng): 如果是周日，则weekNumber加1，有些有小问题
                        if ("WSU".equals(this.dateTimeTag)) {
                            this.weekNumber += 1;
                        }
                    }
                    setValueTagger(TimeXUtil.HasValue.WEEK);
                }
            }
        } else {
            this.mouth = output.get(3);
            if (!TimeXUtil.isNullOrUnknownTag(this.mouth)) {
                setValueTagger(TimeXUtil.HasValue.MOUTH);
            }
            this.day = output.get(5);
            if (!TimeXUtil.isNullOrUnknownTag(this.day)) {
                setValueTagger(TimeXUtil.HasValue.DAY);
            }
        }
        this.isLunar = (output.get(0) != null);
        if (this.isLunar) {
            setValueTagger(TimeXUtil.HasValue.LUNAR);
        }
        this.year = output.get(1);
        if (!TimeXUtil.isNullOrUnknownTag(this.year)) {
            setValueTagger(TimeXUtil.HasValue.YEAR);
        }
        this.raw = str;
        return true;
    }

    public void setValueTagger(TimeXUtil.HasValue index) {
        valueTag[index.ordinal()] = true;
    }

    public boolean getValueTagger(TimeXUtil.HasValue index) {
        return valueTag[index.ordinal()];
    }

    public void fromTimeXValueOfDate(TimeXValueOfDate date) {
        this.year = date.year;
        this.isLunar = date.isLunar;
        this.mouth = date.mouth;
        this.day = date.day;
        this.dateTimeTag = date.dateTimeTag;
        this.weekNumber = date.weekNumber;
        // add by lincheng
        this.valueTag = date.valueTag;
    }

    public boolean isLunar() {
        return isLunar;
    }

    public void setLunar(boolean lunar) {
        isLunar = lunar;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDateTimeTag() {
        return dateTimeTag;
    }

    public void setDateTimeTag(String dateTimeTag) {
        this.dateTimeTag = dateTimeTag;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public boolean[] getValueTag() {
        return valueTag;
    }

    public void setValueTag(boolean[] valueTag) {
        this.valueTag = valueTag;
    }
}
