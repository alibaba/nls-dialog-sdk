package com.alibaba.idst.nls.uds.nlu.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.alibaba.idst.nls.uds.nlu.time.config.TimeXConfig;
import com.alibaba.idst.nls.uds.nlu.time.config.TimeXConfigItem;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfDate;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TimeXPeriodOperator implements TimeXOperator {

    public static final String PERIOD_PREFIX = "period_";
    private static ThreadLocal<DateFormat> FORMATOR = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private Calendar startCalendar = new GregorianCalendar();
    private Calendar endCalendar = new GregorianCalendar();
    private TimeX origin;

    public TimeXPeriodOperator(TimeX origin) {
        this.origin = origin;
    }

    private void init(Date startValue, Date endValue) {
        startCalendar.setTime(startValue);
        endCalendar.setTime(endValue);
    }

    private void applyDateStartAndEnd(TimeXConfig config, TimeXConfigItem item, TimeXValueOfDate dateValue,
                                      TimeXValueOfDate startTmp, TimeXValueOfDate endTmp) {
        TimeXValueOfTime startTimeValue = item.getPeriodStart();
        TimeXValueOfTime endTimeValue = item.getPeriodEnd();
        // reset
        dateValue.setDateTimeTag(null);
        // start
        startTmp.fromTimeXValueOfDate(dateValue);
        config.copyDateValue(startTmp, startTimeValue);

        endTmp.fromTimeXValueOfDate(dateValue);
        config.copyDateValue(endTmp, endTimeValue);
    }

    private boolean initDateAndGetPeriod(TimeXConfig config, TimeXValueOfDate dateValue, TimeXValueOfDate startTmp,
                                         TimeXValueOfDate endTmp) {
        if (StringUtils.isBlank(dateValue.getDateTimeTag())) {
            // no tag is ok
            return true;
        }
        TimeXConfigItem item = config.get(dateValue.getDateTimeTag());
        if (item == null) {
            return false;
        }
        // if (item.getType().equals("WEEK") && !formatWeek) {
        // // no format week as wish
        // return true;
        // }
        applyDateStartAndEnd(config, item, dateValue, startTmp, endTmp);
        // no matter offset is null or not
        Date groundedStart = startTmp.toDate(origin.getOffsetValue());
        // no matter offset is null or not
        Date groundedEnd = endTmp.toDate(origin.getOffsetValue());

        init(groundedStart, groundedEnd);

        return true;
    }

    public boolean init(TimeXConfig config, TimeXValueOfDate dateValue) {
        TimeXValueOfDate startTmp = new TimeXValueOfDate();
        TimeXValueOfDate endTmp = new TimeXValueOfDate();
        return initDateAndGetPeriod(config, dateValue, startTmp, endTmp);
    }

    public boolean init(TimeXConfig config, TimeXValueOfTime timeValue) {
        TimeXValueOfTime startTmpBase = new TimeXValueOfTime();
        TimeXValueOfTime endTmpBase = new TimeXValueOfTime();
        // 如果date tag不为空，那么先apply date tag，但是必须成功
        if (!StringUtils.isBlank(timeValue.getDateTimeTag())) {
            TimeXValueOfDate startTmpBaseDate = new TimeXValueOfDate();
            TimeXValueOfDate endTmpBaseDate = new TimeXValueOfDate();
            // 有date tag但是apply失败
            if (!initDateAndGetPeriod(config, timeValue, startTmpBaseDate, endTmpBaseDate)) {
                return false;
            }
            startTmpBase.fromTimeXValueOfDate(startTmpBaseDate);
            endTmpBase.fromTimeXValueOfDate(endTmpBaseDate);
        } else {
            // set as default
            startTmpBase = timeValue.copy();
            endTmpBase = timeValue.copy();
        }
        // 时间标签是否为空，为空也是对的
        if (StringUtils.isBlank(timeValue.getTimeTag())) {
            // no tag is ok
            return true;
        }
        TimeXConfigItem item = config.get(timeValue.getTimeTag());
        if (item == null) {
            return false;
        }

        TimeXValueOfTime startTimeValue = item.getPeriodStart();
        TimeXValueOfTime endTimeValue = item.getPeriodEnd();
        TimeXValueOfTime startTmp = startTmpBase.copy();
        config.copyTimeValue(startTmp, startTimeValue);
        Date groundedStart = startTmp.toDate(origin.getOffsetValue());
        TimeXValueOfTime endTmp = endTmpBase.copy();
        config.copyTimeValue(endTmp, endTimeValue);
        Date groundedEnd = endTmp.toDate(origin.getOffsetValue());
        init(groundedStart, groundedEnd);
        return true;
    }

    @Override
    public String get(String field) {
        if ((PERIOD_PREFIX + "start_time_unix").equals(field)) {
            return Long.toString(startCalendar.getTimeInMillis());
        }
        if ((PERIOD_PREFIX + "start_time").equals(field)) {
            return FORMATOR.get().format(startCalendar.getTime());
        }
        if ((PERIOD_PREFIX + "end_time_unix").equals(field)) {
            return Long.toString(endCalendar.getTimeInMillis());
        }
        if ((PERIOD_PREFIX + "end_time").equals(field)) {
            return FORMATOR.get().format(endCalendar.getTime());
        }
        if ("raw".equals(field)) {
            return getRaw();
        }
        return "";
    }

    public String getRaw() {
        return origin.getRaw();
    }
}
