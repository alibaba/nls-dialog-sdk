package com.alibaba.idst.nls.dm.common.io;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niannian.ynn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {
    private SDK sdk;
    private App app;
    private System system;
    private Device device;
    private Network network;
    private Geography geography;
    private Map<String, String> custom;
    private String optional;
}
