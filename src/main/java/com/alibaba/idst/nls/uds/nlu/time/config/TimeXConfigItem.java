package com.alibaba.idst.nls.uds.nlu.time.config;

import com.alibaba.idst.nls.uds.nlu.time.entity.TimeXValueOfTime;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
@Data
public class TimeXConfigItem {

    @Attribute(name = "name")
    private String name;
    @Attribute(name = "tag")
    private String tag;
    @Attribute(name = "point")
    private String pointStr;
    @Attribute(name = "period")
    private String period;
    @Attribute(name = "type")
    private String type;
    private TimeXValueOfTime point = new TimeXValueOfTime();
    private TimeXValueOfTime periodStart = new TimeXValueOfTime();
    private TimeXValueOfTime periodEnd = new TimeXValueOfTime();

    public boolean build() {
        String[] ps = period.split(",");
        return ps.length == 2 && this.point.build(pointStr) && this.periodStart.build(ps[0]) && this.periodEnd.build(
            ps[1]);
    }
}