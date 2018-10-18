package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 设备信息
 */
@Data
public class Device {
    /**
     * 设备唯一id，必选
     */
    private String uuid;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 设备品牌
     */
    private String brand;
    /**
     * 设备类型，可选的值：phone, tv, iot, car
     */
    private String type;
    /**
     * 设备IEMI号
     */
    private String imei;
}
