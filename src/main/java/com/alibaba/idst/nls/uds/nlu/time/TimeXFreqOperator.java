package com.alibaba.idst.nls.uds.nlu.time;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXFreq;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXOffset;
import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfSet;
import com.alibaba.idst.nls.uds.nlu.time.output.TimeXJson;
import com.alibaba.idst.nls.uds.nlu.time.output.TimeXTimeJson;
import com.alibaba.idst.nls.uds.nlu.time.output.TimeXUnitJson;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TimeXFreqOperator implements TimeXOperator {

    private static final PropertyFilter TIME_JSON_FILTER = new TimeXTimeJson();
    private static final PropertyFilter FULL_JSON_FILTER = new TimeXJson();
    private TimeXValueOfSet setValue;
    private TimeXOffset offset;
    private TimeXFreq freq;
    private TimeXFreq period;
    private Date localTime;
    private TimeX origin;

    public TimeXFreqOperator(Date localTime, TimeXValueOfSet value, TimeXOffset offset, TimeXFreq freq,
                             TimeXFreq period, TimeX origin) {
        this.setValue = value;
        this.offset = offset;
        this.freq = freq;
        this.period = period;
        this.localTime = localTime;
        this.origin = origin;
    }

    public boolean hasOffset() {
        return offset != null;
    }

    public boolean hasValue() {
        return setValue != null;
    }

    @Override
    public String get(String field) {
        if (field == null) {
            return toJson();
        }
        if ("freq".equals(field)) {
            return freqToJsonString(origin.getFreqValue());
        }
        if ("periodicity".equals(field)) {
            return freqToJsonString(origin.getPeriodicityValue());
        }
        if ("offset".equals(field)) {
            if (offset == null) {
                return "";
            }
            TimeXUnitJson json = new TimeXUnitJson(offset.getTag(), offset.getOffset());
            return JSON.toJSONString(json);
        }
        if ("value".equals(field)) {
            TimeXTimeJson timeJson = new TimeXTimeJson();
            copyValueToJsonObject(timeJson);
            return writeFreqToJson(timeJson);
        }
        if ("value_all".equals(field)) {
            return toJson();
        }
        if ("raw".equals(field)) {
            return getRaw();
        }
        return "";
    }

    private String freqToJsonString(TimeXFreq freq) {
        if (freq == null) {
            return "";
        }
        TimeXUnitJson json = new TimeXUnitJson(freq.getUnit(), freq.getValue());
        return JSON.toJSONString(json);
    }

    public String getRaw() {
        return origin.getRaw();
    }

    public String toJson() {
        TimeXJson json = new TimeXJson();
        TimeXTimeJson timeJson = new TimeXTimeJson();
        if (hasValue()) {
            copyValueToJsonObject(timeJson);
            json.setValue(timeJson);
        }
        // set offset
        if (hasOffset()) {
            json.setOffset(new TimeXUnitJson(offset.getTag(), offset.getOffset()));
        }

        if (period != null) {
            json.setPeriod(new TimeXUnitJson(period.getUnit(), period.getValue()));
        }

        if (freq != null) {
            json.setFreq(new TimeXUnitJson(freq.getUnit(), freq.getValue()));
        }

        if (hasValue()) {
            return writeFreqToJson(json);
        } else {
            return JSON.toJSONString(json);
        }
    }

    private String writeFreqToJson(Object json) {
        SerializeWriter writer = new SerializeWriter();
        JSONSerializer serial = new JSONSerializer(writer);
        // for set json
        serial.getPropertyFilters().add(TIME_JSON_FILTER);
        // for full json
        serial.getPropertyFilters().add(FULL_JSON_FILTER);
        serial.write(json);
        return writer.toString();
    }

    private void copyValueToJsonObject(TimeXTimeJson timeJson) {
        // time tag is grounded, just date tag left
        if (StringUtils.isNoneBlank(setValue.getDateTimeTag())) {
            timeJson.setTag(setValue.getDateTimeTag());
        }
        timeJson.copyValue(setValue);
    }
}
