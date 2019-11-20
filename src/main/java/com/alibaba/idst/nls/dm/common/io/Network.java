package com.alibaba.idst.nls.dm.common.io;

import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class Network {
    private String ip;
    private String mac;
    private String type;
    private String subtype;
    private String carrier;
}
