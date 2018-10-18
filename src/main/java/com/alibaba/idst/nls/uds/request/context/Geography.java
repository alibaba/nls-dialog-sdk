package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 地理地址
 */
@Data
public class Geography {
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 第一级地址信息，一般是国家
     */
    private String address_l1;
    /**
     * 第二级地址信息，一般是省份
     */
    private String address_l2;
    /**
     * 第三级地址信息，一般是城市
     */
    private String address_l3;
    /**
     * 第四级地址信息，一般是街道
     */
    private String address_l4;
    /**
     * 第五级地址信息，一般是门牌号
     */
    private String address_l5;
}
