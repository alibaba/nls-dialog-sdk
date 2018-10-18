package com.alibaba.idst.nls.uds.nlu.time.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeXUnitJson {
    private String unit;
    private int value;

    public TimeXUnitJson() {}
}
