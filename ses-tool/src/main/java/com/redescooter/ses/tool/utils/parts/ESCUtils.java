package com.redescooter.ses.tool.utils.parts;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ESCUtils
 * @Author Jerry
 * @date 2020/02/27 21:34
 * @Description: 部品项目区域验证工具
 */

public class ESCUtils {

    public final static Map<String, String> map = new HashMap() {{
        put("SEC", "SEC");
        put("F00", "F00");
        put("F01", "F01");
        put("F02", "F02");
        put("F03", "F03");
        put("F04", "F04");
        put("F05", "F05");
        put("F06", "F06");
        put("F07", "F07");
        put("F08", "F08");
        put("F09", "F09");
        put("F10", "F10");
        put("F11", "F11");
        put("F12", "F12");
        put("F13", "F13");
        put("F14", "F14");
        put("F15", "F15");
        put("F16", "F16");
        put("F17", "F17");
        put("F18", "F18");
        put("F19", "F19");
        put("F20", "F20");
        put("F21", "F21");
        put("F22", "F22");
        put("F23", "F23");
        put("F24", "F24");
        put("F25", "F25");
        put("F26", "F26");
        put("F27", "F27");
        put("F28", "F28");
        put("F29", "F29");
        put("F30", "F30");
        put("F31", "F31");
        put("F32", "F32");
        put("F33", "F33");
        put("F34", "F34");
        put("F35", "F35");
        put("F36", "F36");
        put("F37", "F37");
        put("F38", "F38");
        put("F39", "F39");
        put("F40", "F40");
        put("F41", "F41");
        put("F42", "F42");
        put("F43", "F43");
        put("F44", "F44");
        put("F45", "F45");
        put("F46", "F46");
        put("F47", "F47");
        put("F48", "F48");
        put("F49", "F49");
        put("F50", "F50");
        put("F51", "F51");
        put("F52", "F52");
        put("F53", "F53");
        put("F54", "F54");
        put("F55", "F55");
        put("F56", "F56");
        put("F57", "F57");
        put("F58", "F58");
        put("F59", "F59");
        put("F60", "F60");
        put("F61", "F61");
        put("F62", "F62");
        put("F63", "F63");
        put("F64", "F64");
        put("F65", "F65");
        put("F66", "F66");
        put("F67", "F67");
        put("F68", "F68");
        put("F69", "F69");
        put("F70", "F70");
        put("F71", "F71");
        put("F72", "F72");
        put("F73", "F73");
        put("F74", "F74");
        put("F75", "F75");
        put("F76", "F76");
        put("F77", "F77");
        put("F78", "F78");
        put("F79", "F79");
        put("F80", "F80");
        put("F81", "F81");
        put("F82", "F82");
        put("F83", "F83");
        put("F84", "F84");
        put("F85", "F85");
        put("F86", "F86");
        put("F87", "F87");
        put("F88", "F88");
        put("F89", "F89");
        put("F90", "F90");
        put("F91", "F91");
        put("F92", "F92");
        put("F93", "F93");
        put("F94", "F94");
        put("F95", "F95");
        put("F96", "F96");
        put("F97", "F97");
        put("F98", "F98");
        put("F99", "F99");

    }};

    public static String checkESC(String sec) {
        return map.getOrDefault(sec, null);
    }
}
