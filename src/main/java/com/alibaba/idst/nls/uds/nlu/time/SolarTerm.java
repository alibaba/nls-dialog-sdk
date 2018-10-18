package com.alibaba.idst.nls.uds.nlu.time;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public enum SolarTerm {
    LC,
    // --立春
    YS,
    // --雨水
    JZ,
    // --惊蛰
    CF,
    // 春分
    QM,
    // 清明
    GY,
    // 谷雨
    LX,
    // 立夏
    XM,
    // 小满
    MZ,
    // 芒种
    XZ,
    // 夏至
    XS,
    // 小暑
    DS,
    // 大暑
    LQ,
    // 立秋
    CS,
    // 处暑
    BL,
    // 白露
    QF,
    // 秋分
    HL,
    // 寒露
    SJ,
    // 霜降
    LD,
    // 立冬
    XX,
    // 小雪
    DX,
    // 大雪
    DZ,
    // 冬至
    XH,
    // 小寒
    DH; // 大寒

    private static final Set<String> SOLAR_TERM_NAME_SET = ImmutableSet.of(SolarTerm.LC.name(), SolarTerm.YS.name(),
        SolarTerm.JZ.name(), SolarTerm.CF.name(), SolarTerm.QM.name(), SolarTerm.GY.name(), SolarTerm.LX.name(),
        SolarTerm.XM.name(), SolarTerm.MZ.name(), SolarTerm.XZ.name(), SolarTerm.XS.name(), SolarTerm.DS.name(),
        SolarTerm.LQ.name(), SolarTerm.CS.name(), SolarTerm.BL.name(), SolarTerm.QF.name(), SolarTerm.HL.name(),
        SolarTerm.SJ.name(), SolarTerm.LD.name(), SolarTerm.XX.name(), SolarTerm.DX.name(), SolarTerm.DZ.name(),
        SolarTerm.XH.name(), SolarTerm.DH.name());

    public static boolean isSolarTermTag(String name) {
        return SOLAR_TERM_NAME_SET.contains(name);
    }
}
