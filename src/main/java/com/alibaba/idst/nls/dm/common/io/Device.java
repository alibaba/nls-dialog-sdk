package com.alibaba.idst.nls.dm.common.io;

import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class Device {
    private String uuid;
    private String model;
    private String brand;
    private String type;
    private String imei;
}
