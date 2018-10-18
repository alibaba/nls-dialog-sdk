package com.alibaba.idst.nls.uds.nlu.time.config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.idst.nls.uds.nlu.time.LunarCalendar;
import com.alibaba.idst.nls.uds.nlu.time.SolarTerm;
import com.alibaba.idst.nls.uds.nlu.time.SolarTermCalendar;
import com.alibaba.idst.nls.uds.nlu.time.TimeXUtil;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfDate;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

@Root(name = "Config")
public class TimeXConfig {

    private Map<String, TimeXConfigItem> dateItemMap = new HashMap<>();
    private Map<String, TimeXConfigItem> timeItemMap = new HashMap<>();

    @ElementList(inline = true, entry = "TimeTag")
    private List<TimeXConfigItem> tags;

    @Commit
    private void commit() {
        if (tags == null) {
            return;
        }
        if (!build()) {
            tags = null;
            timeItemMap.clear();
            dateItemMap.clear();
        }
    }

    private boolean build() {
        for (TimeXConfigItem item : tags) {
            if (!item.build()) {
                return false;
            }
            // date & week set into dateItemMap
            if ("DATE".equals(item.getType()) || "WEEK".equals(item.getType())) {
                dateItemMap.put(item.getTag(), item);
            } else if ("TIME".equals(item.getType())) {
                timeItemMap.put(item.getTag(), item);
            }
        }
        return true;
    }

    public TimeXConfigItem get(String key) {
        TimeXConfigItem item = dateItemMap.get(key);
        if (item != null) {
            return item;
        }
        // whatever null or not
        return timeItemMap.get(key);
    }

    public boolean applyDateTag(TimeXValueOfDate dateValue) {
        return applyDateTag(dateValue, true);
    }

    public boolean applyDateTag(TimeXValueOfDate dateValue, boolean formatWeek) {
        if (StringUtils.isBlank(dateValue.getDateTimeTag())) {
            // no tag is ok
            return true;
        }
        // we can compute that
        // 二十四节气，每年日期不一样
        if (SolarTerm.isSolarTermTag(dateValue.getDateTimeTag())) {
            if (!formatWeek) { // TODO: 这里不format，因为每年不一样
                // 必须return true，否则后面查表去了
                return true;
            }
            // TODO: we check year ?
            Calendar calendar = SolarTermCalendar.getSolarTermNum(Integer.parseInt(dateValue.getYear()),
                dateValue.getDateTimeTag());
            dateValue.setYear(Integer.toString(calendar.get(Calendar.YEAR)));
            dateValue.setMouth(Integer.toString(calendar.get(Calendar.MONTH) + 1));
            dateValue.setDay(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));

            // reset
            dateValue.setDateTimeTag(null);
            return true;
        }
        TimeXConfigItem item = get(dateValue.getDateTimeTag());
        if (item == null) {
            return false;
        }
        if ("WEEK".equals(item.getType()) && !formatWeek) {
            // no format week as wish
            return true;
        }
        TimeXValueOfDate originTime = item.getPoint();
        dateValue.setDateTimeTag(null);
        copyDateValue(dateValue, originTime);
        return true;
    }

    public void copyDateValue(TimeXValueOfDate to, TimeXValueOfDate baseOrigin) {
        TimeXValueOfDate base = new TimeXValueOfDate();
        base.fromTimeXValueOfDate(baseOrigin);
        if (base.isLunar()) { // set year to ground time
            base.setYear(to.getYear());
            Calendar calendar = LunarCalendar.lunarToSolar(Integer.parseInt(base.getYear()),
                Integer.parseInt(base.getMouth()), Integer.parseInt(base.getDay()));
            /**
             * month is 0-based, so +1
             */
            base.setMouth(Integer.toString(calendar.get(Calendar.MONTH) + 1));
            base.setDay(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        }
        // update & make sure no replace by XXXX
        if (!TimeXUtil.isNullOrUnknownTag(base.getYear()) && TimeXUtil.isNullOrUnknownTag(to.getYear())) {
            to.setYear(base.getYear());
        }
        if (!TimeXUtil.isNullOrUnknownTag(base.getMouth()) && TimeXUtil.isNullOrUnknownTag(to.getMouth())) {
            to.setMouth(base.getMouth());
        }
        if (!TimeXUtil.isNullOrUnknownTag(base.getDay()) && TimeXUtil.isNullOrUnknownTag(to.getDay())) {
            to.setDay(base.getDay());
        }
    }

    public boolean applyTimeTag(TimeXValueOfTime timeValue) {
        return applyTimeTag(timeValue, true);
    }

    /**
     * ground 时间标签，比如MO, LC等，变为具体时间
     *
     * @param timeValue
     * @return
     */
    public boolean applyTimeTag(TimeXValueOfTime timeValue, boolean formatWeek) {
        // 如果date tag不为空，那么先apply date tag，但是必须成功
        if (!StringUtils.isBlank(timeValue.getDateTimeTag()) && !applyDateTag(timeValue, formatWeek)) {
            // 有date tag但是apply失败
            return false;
        }
        // 时间标签是否为空，为空也是对的
        if (StringUtils.isBlank(timeValue.getTimeTag())) {
            // no tag is ok
            return true;
        }
        TimeXConfigItem item = get(timeValue.getTimeTag());
        if (item == null) {
            return false;
        }
        TimeXValueOfTime groudTime = item.getPoint();
        // reset
        timeValue.setTimeTag(null);
        copyTimeValue(timeValue, groudTime);
        return true;
    }

    public void copyTimeValue(TimeXValueOfTime to, TimeXValueOfTime base) {
        // update & make sure not replace by XXXX
        if (!TimeXUtil.isNullOrUnknownTag(base.getHour())) {
            to.setHour(base.getHour());
        }
        if (!TimeXUtil.isNullOrUnknownTag(base.getMinute())) {
            to.setMinute(base.getMinute());
        }
        if (!TimeXUtil.isNullOrUnknownTag(base.getSecond())) {
            to.setSecond(base.getSecond());
        }
    }
}
