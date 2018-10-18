package com.alibaba.idst.nls.uds.nlu.time;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.idst.nls.uds.nlu.time.config.TimeXConfig;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXDurationValue;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXFreq;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXOffset;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfDate;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfSet;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;
import com.alibaba.idst.nlu.response.common.Slot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimeX extends Slot {

    public static final String RAW = "raw";
    public static final String VALUE = "value";
    public static final String OFFSET = "offset";
    public static final String MOD = "mod";
    public static final String PERIODICITY = "periodicity";
    public static final String FREQ = "freq";
    public static final String START = "start";
    public static final String END = "end";
    public static final String TYPE = "type";
    private static final String NORM_FIELD = "timex";
    public static final ValueFilter TIMEX_VALUE_FILTER = (object, name, value1) -> {
        if (object instanceof TimeX && NORM_FIELD.equals(name)) {
            // parseObject for timex field
            return JSON.parseObject(value1.toString());
        }
        return value1;
    };
    private TimeXConfig config;
    @JSONField(name = RAW)
    private String raw;
    @JSONField(name = NORM_FIELD)
    private String norm;
    private TimeXType type;
    private String value;
    private String offset;
    private String mod;
    private String start;
    private String end;
    private String periodicity;
    private String freq;
    private TimeXValueOfTime timeValue;
    private TimeXValueOfDate dateValue;
    private TimeXValueOfSet timeSetValue;
    private TimeXOffset offsetValue;
    private TimeXFreq freqValue;
    private TimeXFreq periodicityValue;
    private TimeXDurationValue durationValue;
    private TimeXValueOfTime startValue;
    private TimeXValueOfTime endValue;

    public boolean build(JSONObject slot) {
        if (!slot.containsKey(RAW) || !slot.containsKey(NORM_FIELD)) {
            return false;
        }
        this.raw = slot.getString(RAW);
        this.norm = slot.getString(NORM_FIELD);
        return setValues(slot);
    }

    public boolean build(JSONArray slotArray) {
        if (slotArray.size() == 0) {
            return false;
        }
        JSONObject slot = slotArray.getJSONObject(0);
        return build(slot);
    }

    private boolean setValues(JSONObject slot) {
        JSONObject norm = slot.getJSONObject(NORM_FIELD);
        if (!setTimeXType(norm)) {
            // set timex type error ?
            return false;
        }
        // set value
        if (!norm.containsKey(VALUE)) {
            // has no value and is SET and has offset is OK
            if ((type != TimeXType.SET || !norm.containsKey(OFFSET) || StringUtils.isBlank(norm.getString(OFFSET)))
                // has no value and is DURATION has start and end is OK
                && (type != TimeXType.DURATION || !norm.containsKey(START) || !norm.containsKey(END))) {
                return false;
            }
        } else {
            if (StringUtils.isBlank(norm.getString(VALUE))) {
                // value is not a string ?
                return false;
            }
            this.value = norm.getString(VALUE);
            if (!StringUtils.isBlank(this.value)) {
                switch (type) {
                    case SET:
                        this.timeSetValue = new TimeXValueOfSet();
                        if (!this.timeSetValue.build(this.value)) {
                            // build set value error
                            return false;
                        }
                        break;
                    case DATE:
                        this.dateValue = new TimeXValueOfDate();
                        if (!this.dateValue.build(this.value)) {
                            // build date value error
                            return false;
                        }
                        break;
                    case TIME:
                        this.timeValue = new TimeXValueOfTime();
                        if (!this.timeValue.build(this.value)) {
                            // build time value error
                            return false;
                        }
                        break;
                    case DURATION:
                        this.durationValue = new TimeXDurationValue();
                        if (!this.durationValue.build(this.value)) {
                            // build time value error
                            return false;
                        }
                        break;
                    default:
                        // what else ??
                        break;
                }
            } else {
                // empty value, error
                return false;
            }
        }
        // set offset
        if (norm.containsKey(OFFSET) && !StringUtils.isBlank(norm.getString(OFFSET))) {
            this.offset = norm.getString(OFFSET);
            this.offsetValue = new TimeXOffset();
            // build the offset entity error
            if (!this.offsetValue.build(this.offset)) {
                return false;
            }
        }
        // set mod
        if (norm.containsKey(MOD)) {
            this.mod = norm.getString(MOD);
        }
        // set periodicity
        if (norm.containsKey(PERIODICITY)) {
            this.periodicity = norm.getString(PERIODICITY);
            if (!StringUtils.isBlank(this.periodicity)) {
                this.periodicityValue = new TimeXFreq();
                // build the periodicity entity error
                if (!this.periodicityValue.build(this.periodicity)) {
                    return false;
                }
            }
        }
        // set freq
        if (norm.containsKey(FREQ)) {
            this.freq = norm.getString(FREQ);
            if (!StringUtils.isBlank(this.freq)) {
                this.freqValue = new TimeXFreq();
                // build the periodicity entity error
                if (!this.freqValue.build(this.freq)) {
                    return false;
                }
            }
        }
        // set start
        if (norm.containsKey(START)) {
            this.start = norm.getString(START);
            if (!StringUtils.isBlank(this.start)) {
                this.startValue = new TimeXValueOfTime();
                if (!buildStartOrEnd(this.startValue, this.start)) {
                    return false;
                }
            }
        }

        // set end
        if (norm.containsKey(END)) {
            this.end = norm.getString(END);
            if (!StringUtils.isBlank(this.end)) {
                this.endValue = new TimeXValueOfTime();
                return buildStartOrEnd(this.endValue, this.end);
            }
        }

        return true;
    }

    private boolean buildStartOrEnd(TimeXValueOfTime point_value, String point) {
        if (!point_value.build(point)) {
            TimeXValueOfDate tmpDate = new TimeXValueOfDate();
            if (!tmpDate.build(point)) {
                return false;
            }
            point_value.fromTimeXValueOfDate(tmpDate);
        }
        return true;
    }

    private boolean setTimeXType(JSONObject norm) {
        if (!norm.containsKey(TYPE)) {
            return false;
        }
        String timex_type = norm.getString(TYPE);
        try {
            this.type = TimeXType.valueOf(timex_type);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isTimeValue() {
        return TimeXType.TIME.equals(type);
    }

    public boolean isDateValue() {
        return TimeXType.DATE.equals(type);
    }

    public boolean isTimeSetValue() {
        return TimeXType.SET.equals(type);
    }

    public boolean isDurationValue() {
        return TimeXType.DURATION.equals(type);
    }

    public TimeXFreqOperator ground2Freq(long localTime) {
        Date localDate = new Date(localTime);
        TimeXFreqOperator oper = null;
        if (config == null) {
            return null;
        }
        if (isTimeSetValue() && (timeSetValue != null || offsetValue != null) && config != null) {
            // offsetValue not null
            if (timeSetValue != null) {
                if (!config.applyTimeTag(timeSetValue, false)) {
                    return null;
                }
            }
            oper = new TimeXFreqOperator(localDate, timeSetValue, offsetValue, freqValue, periodicityValue, this);
        }
        return oper;
    }

    public TimeXDurationOperator ground2Duration(long localTime) {
        Date localDate = new Date(localTime);
        TimeXDurationOperator oper = null;
        if (config == null) {
            return null;
        }
        if (isDurationValue()) {
            Date start = null;
            Date end = null;
            long duration = 0;
            if (startValue != null && endValue != null) {
                TimeXPointOperator startPoint = groundTimeValue(localDate, startValue, null);
                TimeXPointOperator endPoint = groundTimeValue(localDate, endValue, null);
                if (startPoint != null && endPoint != null) {
                    long startLong = startPoint.getUnixTime();
                    long endLong = endPoint.getUnixTime();
                    // for seconds
                    duration = (endLong - startLong) / 1000L;
                    start = new Date(startLong);
                    end = new Date(endLong);
                }
            } else {
                duration = durationValue.getDuration();
            }
            if (duration > 0) {
                oper = new TimeXDurationOperator(localDate, start, end, duration, this);
            }
        }
        return oper;
    }

    public TimeXPointOperator ground2Point(long localTime) {
        Date localDate = new Date(localTime);
        TimeXPointOperator oper = null;
        if (config == null) {
            return null;
        }
        if (isTimeValue()) {
            oper = groundTimeValue(localDate, this.timeValue, this.offsetValue);
        } else if (isDateValue()) {
            oper = groundDateValue(localDate, this.dateValue, this.offsetValue);
        }
        // just for time and date
        return oper;
    }

    public TimeXOperator ground2Period(long localTime) {
        Date localDate = new Date(localTime);
        TimeXPeriodOperator oper = null;
        if (config == null) {
            return null;
        }
        if (isTimeValue()) {
            // (no time and date tag) or (no time tag and date tag is solar tag) return point
            if ((StringUtils.isBlank(this.timeValue.getDateTimeTag()) && StringUtils.isBlank(this.timeValue
                .getTimeTag()))
                || (StringUtils.isBlank(this.timeValue.getTimeTag()) && SolarTerm.isSolarTermTag(this.timeValue
                .getDateTimeTag()))) {
                return groundTimeValue(localDate, this.timeValue, this.offsetValue);
            }
            // TODO(lincheng): 为了不修改原始得值，哎，其实所有得ground都应该这样，以后再改吧
            TimeXValueOfTime timeValueBk = timeValue.copy();
            timeValueBk.ground(localDate);
            TimeXPeriodOperator pOper = new TimeXPeriodOperator(this);
            if (!pOper.init(config, timeValueBk)) {
                return null;
            }
            return pOper;
        } else if (isDateValue()) {
            if (StringUtils.isBlank(this.dateValue.getDateTimeTag())
                || SolarTerm.isSolarTermTag(this.dateValue.getDateTimeTag())) {
                return groundDateValue(localDate, this.dateValue, this.offsetValue);
            }
            // TODO(lincheng): 为了不修改原始得值，哎，其实所有得ground都应该这样，以后再改吧
            TimeXValueOfDate dateValueBk = new TimeXValueOfDate(dateValue);
            // ground first
            dateValueBk.ground(localDate);
            // apply period tag
            TimeXPeriodOperator pOper = new TimeXPeriodOperator(this);
            if (!pOper.init(config, dateValueBk)) {
                return null;
            }
            return pOper;
        }
        return null;
    }

    private TimeXPointOperator groundDateValue(Date localDate, TimeXValueOfDate dateValueOrigin,
                                               TimeXOffset offsetValue) {
        if (config == null) {
            return null;
        }
        // 备份，以免原始值被改变
        TimeXValueOfDate dateValue = new TimeXValueOfDate(dateValueOrigin);
        dateValue.ground(localDate);
        if (!config.applyDateTag(dateValue)) {
            return null;
        }
        Date grounded = dateValue.toDate(offsetValue);
        return new TimeXPointOperator(grounded, new TimeXValueOfTime(dateValue), this);
    }

    private TimeXPointOperator groundTimeValue(Date localDate, TimeXValueOfTime timeValueOrigin,
                                               TimeXOffset offsetValue) {
        if (config == null) {
            return null;
        }
        // 备份，以免值被改变
        TimeXValueOfTime timeValue = timeValueOrigin.copy();
        timeValue.ground(localDate);
        if (!config.applyTimeTag(timeValue)) {
            return null;
        }
        // no matter offset is null or not
        Date grounded = timeValue.toDate(offsetValue);
        return new TimeXPointOperator(grounded, timeValue, this);
    }

    public String groundAndGetField(long localTime, String field) {
        if (TYPE.equals(field)) {
            return type.name();
        }
        TimeXOperator oper;
        switch (type) {
            case SET:
                // TODO(lincheng):这部分还没有做备份处理，会有问题，后续改掉
                oper = ground2Freq(localTime);
                break;
            case DURATION:
                oper = ground2Duration(localTime);
                break;
            case TIME:
            case DATE:
            default:
                if (field != null && field.startsWith(TimeXPeriodOperator.PERIOD_PREFIX)) {
                    // TODO(lincheng): 这个只能这样添加区间时间的获取
                    oper = ground2Period(localTime);
                } else {
                    oper = ground2Point(localTime);
                }
        }
        if (oper == null) {
            return "";
        }
        return oper.get(field);
    }

    public enum TimeXType {
        DATE,
        TIME,
        DURATION,
        SET
    }

}
