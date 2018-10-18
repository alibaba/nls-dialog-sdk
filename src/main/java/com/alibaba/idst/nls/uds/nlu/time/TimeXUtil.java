package com.alibaba.idst.nls.uds.nlu.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class TimeXUtil {

    public static final Map<String, HasValue> TAGGER_MAP = new HashMap<>();
    public static final Map<String, Integer> UNIT_MAP = new HashMap<>();
    public static final Pattern DATE_VALUE_RE = Pattern.compile("(L)?([X0-9]{4})(\\-([X0-9]{2})(\\-([X0-9]{2}))?)?");
    public static final Pattern TIME_VALUE_RE = Pattern
        .compile(
            "((L)?([X0-9]{4})\\-((([X0-9]{2})\\-([X0-9]{2}))|((W([A-Z]{1,2})?)([X0-9]{1,2}))|([A-Z]{2})))?(U)?T"
                + "([X0-9]{2}):([X0-9]{2})(:([X0-9]{2}))?");
    // DATE_VALUE_RRe2E must match first
    public static final Pattern DATE_VALUE_TAG_RE = Pattern
        .compile("(L)?([X0-9]{4})\\-(([A-Z]{2})|((W|([A-Z]{2,3}))([X0-9]{1,2})))");
    // TIME_VALUE_RRe2E must match first
    public static final Pattern TIME_VALUE_TAG_RE = Pattern
        .compile(
            "((L)?([X0-9]{4})\\-((([X0-9]{2})\\-([X0-9]{2}))|((W([A-Z]{1,2})?)([X0-9]{1,2}))|([A-Z]{2})))?T([A-Z]{2,"
                + "3})");
    public static final Pattern DURATION_VALUE_RE = Pattern
        .compile("P(([0-9]+)Y)?(([0-9]+)M)?(([0-9]+)W)?(([0-9]+)D)?(([0-9]+)H)?(([0-9]+)I)?(([0-9]+)S)?");
    public static final Pattern FREQ_RE = Pattern.compile("([0-9]+)([A-Z])");
    public static final Pattern OFFSET_RE = Pattern.compile("([A-Z]{1,3})@([\\-+]?[0-9]+)");
    public static final Pattern PERIODICITY_RE = Pattern.compile("P([0-9]+)([A-Z])");
    public static final Pattern TAG_RE = Pattern.compile("[A-Z]{2,3}");
    public static final Pattern IS_UNKNOWN_TAG = Pattern.compile("X{2,4}");
    public static Map<String, Integer> WEEK_INFO = new HashMap<>();

    static {
        TAGGER_MAP.put("has_year", HasValue.YEAR);
        TAGGER_MAP.put("has_mouth", HasValue.MOUTH);
        TAGGER_MAP.put("has_day", HasValue.DAY);
        TAGGER_MAP.put("has_hour", HasValue.HOUR);
        TAGGER_MAP.put("has_minute", HasValue.MINUTE);
        TAGGER_MAP.put("has_second", HasValue.SECOND);
        TAGGER_MAP.put("has_date_tag", HasValue.DATE_TAG);
        TAGGER_MAP.put("has_time_tag", HasValue.TIME_TAG);
        TAGGER_MAP.put("has_lunar", HasValue.LUNAR);
        TAGGER_MAP.put("has_week", HasValue.WEEK);
        TAGGER_MAP.put("has_am_pm", HasValue.AM_PM);
    }

    static {
        WEEK_INFO.put("WMO", Calendar.MONDAY);
        WEEK_INFO.put("WTU", Calendar.TUESDAY);
        WEEK_INFO.put("WWE", Calendar.WEDNESDAY);
        WEEK_INFO.put("WTH", Calendar.THURSDAY);
        WEEK_INFO.put("WFR", Calendar.FRIDAY);
        WEEK_INFO.put("WSA", Calendar.SATURDAY);
        WEEK_INFO.put("WSU", Calendar.SUNDAY);
    }

    static {
        UNIT_MAP.put("D", Calendar.DAY_OF_MONTH);
        UNIT_MAP.put("Y", Calendar.YEAR);
        UNIT_MAP.put("M", Calendar.MONTH);
        UNIT_MAP.put("H", Calendar.HOUR_OF_DAY);
        UNIT_MAP.put("I", Calendar.MINUTE);
        UNIT_MAP.put("S", Calendar.SECOND);
        // long
        UNIT_MAP.put("day", Calendar.DAY_OF_MONTH);
        UNIT_MAP.put("year", Calendar.YEAR);
        UNIT_MAP.put("month", Calendar.MONTH);
        UNIT_MAP.put("hour", Calendar.HOUR_OF_DAY);
        UNIT_MAP.put("minute", Calendar.MINUTE);
        UNIT_MAP.put("second", Calendar.SECOND);
    }

    public static boolean fullMatch(Pattern pattern, String str, List<String> output) {
        if (output == null) {
            return false;
        } else {
            output.clear();
        }
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                output.add(matcher.group(i));
            }
        }
        return output.size() > 0;
    }

    public static boolean isUnknownTag(String str) {
        return IS_UNKNOWN_TAG.matcher(str).matches();
    }

    public static boolean isNullOrUnknownTag(String str) {
        return StringUtils.isBlank(str) || isUnknownTag(str);
    }

    public static Date getEndTime(Calendar calendar) {
        Calendar tmpCalendar = new GregorianCalendar();
        tmpCalendar.setTime(calendar.getTime());
        // reset
        tmpCalendar.set(Calendar.HOUR, 0);
        tmpCalendar.set(Calendar.MINUTE, 0);
        tmpCalendar.set(Calendar.SECOND, 0);
        // add 1 day - 1 sec
        tmpCalendar.add(Calendar.DATE, 1);
        tmpCalendar.add(Calendar.SECOND, -1);
        return tmpCalendar.getTime();
    }

    public enum HasValue {
        YEAR,
        MOUTH,
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        DATE_TAG,
        TIME_TAG,
        LUNAR,
        WEEK,
        AM_PM
    }
}
