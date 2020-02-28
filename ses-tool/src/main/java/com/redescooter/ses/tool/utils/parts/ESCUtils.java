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
        put("F50", "F50");
        put("F51", "F51");
        put("F52", "F52");
        put("F53", "F53");
        put("F55", "F55");
        put("F61", "F61");
        put("F62", "F62");
        put("F63", "F63");
        put("F64", "F64");
        put("F99", "F99");

    }};

    public static String checkESC(String sec) {
        return map.getOrDefault(sec, null);
    }
}
