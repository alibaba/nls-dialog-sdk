package com.alibaba.idst.nls.uds.nlu.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:518ad-ccn date:Dec 13, 2011 describe：24节气
 * 注：程序中使用到的计算节气公式、节气世纪常量等相关信息参照http://www.360doc.com/skill/11/0106/22/5281066_84591519.shtml，
 * 程序的运行得出的节气结果绝大多数是正确的，有少数部份是有误差的
 */
public class SolarTermCalendar {

    private static final double D = 0.2422;
    private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<>();// +1偏移
    private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<>();// -1偏移
    // 每个节气在几月
    private final static int[] SOLAR_MONTH_MAP = new int[] {2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10,
        11, 11, 12, 12, 1, 1};
    // 定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY = {
        {4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44,
            23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84},
        {3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646,
            23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12}};

    static {
        DECREASE_OFFSETMAP.put(SolarTerm.YS.name(), new Integer[] {2026});// 雨水
        INCREASE_OFFSETMAP.put(SolarTerm.CF.name(), new Integer[] {2084});// 春分
        INCREASE_OFFSETMAP.put(SolarTerm.XM.name(), new Integer[] {2008});// 小满
        INCREASE_OFFSETMAP.put(SolarTerm.MZ.name(), new Integer[] {1902});// 芒种
        INCREASE_OFFSETMAP.put(SolarTerm.XZ.name(), new Integer[] {1928});// 夏至
        INCREASE_OFFSETMAP.put(SolarTerm.XS.name(), new Integer[] {1925, 2016});// 小暑
        INCREASE_OFFSETMAP.put(SolarTerm.DS.name(), new Integer[] {1922});// 大暑
        INCREASE_OFFSETMAP.put(SolarTerm.LQ.name(), new Integer[] {2002});// 立秋
        INCREASE_OFFSETMAP.put(SolarTerm.BL.name(), new Integer[] {1927});// 白露
        INCREASE_OFFSETMAP.put(SolarTerm.QF.name(), new Integer[] {1942});// 秋分
        INCREASE_OFFSETMAP.put(SolarTerm.SJ.name(), new Integer[] {2089});// 霜降
        INCREASE_OFFSETMAP.put(SolarTerm.LD.name(), new Integer[] {2089});// 立冬
        INCREASE_OFFSETMAP.put(SolarTerm.XX.name(), new Integer[] {1978});// 小雪
        INCREASE_OFFSETMAP.put(SolarTerm.DX.name(), new Integer[] {1954});// 大雪
        DECREASE_OFFSETMAP.put(SolarTerm.DZ.name(), new Integer[] {1918, 2021});// 冬至
        INCREASE_OFFSETMAP.put(SolarTerm.XH.name(), new Integer[] {1982});// 小寒
        DECREASE_OFFSETMAP.put(SolarTerm.XH.name(), new Integer[] {2019});// 小寒
        INCREASE_OFFSETMAP.put(SolarTerm.DH.name(), new Integer[] {2082});// 大寒
    }

    /**
     * @param year 年份
     * @param name 节气的名称
     * @return 返回节气是相应的年月日
     */
    public static Calendar getSolarTermNum(int year, String name) {

        double centuryValue = 0;// 节气的世纪值，每个节气的每个世纪值都不同
        int ordinal = SolarTerm.valueOf(name).ordinal();

        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {// 20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {// 21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
        int dateNum = 0;
        /**
         * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
         */
        int y = year % 100;// 步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年
            if (ordinal == SolarTerm.XH.ordinal() || ordinal == SolarTerm.DH.ordinal()
                || ordinal == SolarTerm.LC.ordinal() || ordinal == SolarTerm.YS.ordinal()) {
                // 注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;// 步骤2
            }
        }
        dateNum = (int)(y * D + centuryValue) - y / 4;// 步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, name);// 步骤4，加上特殊的年分的节气偏移量
        // return dateNum;
        return new GregorianCalendar(year, SOLAR_MONTH_MAP[ordinal] - 1, dateNum);
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param name 节气名称
     * @return 返回其偏移量
     */
    private static int specialYearOffset(int year, String name) {
        if (checkOffset(DECREASE_OFFSETMAP, year, name)) { return -1; }
        if (checkOffset(INCREASE_OFFSETMAP, year, name)) { return 1; }
        return 0;
    }

    private static boolean checkOffset(Map<String, Integer[]> map, int year, String name) {
        Integer[] years = map.get(name);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    return true;
                }
            }
        }
        return false;
    }
}