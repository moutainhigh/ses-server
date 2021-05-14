package com.redescooter.ses.api.common.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName:RegexpConstant
 * @description: RegexpConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/12 13:35
 */
public interface RegexpConstant {

    //特殊字符 正则表达式 表达式取反  ^((?!你的正则表达式).)*
     String specialCharacters="^((?![`~!@#$%^&*()+=|{}':;',\\\\[\\\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]).)*";

    //邮箱地址
     String email="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

     String emailAcount = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
    //用户名校验
    //帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
     String loginName="^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    //密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
     String password="^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    //强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     String passwordStrength="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";

    //空格 首尾空白字符的正则表达式
     String space="^\\s*|\\s*$";

     //名称校验 英文姓名由字母和空格组成
     //不校验首字母大写 和法语 -
     //比如：Jack、Jack Chan、Donald Trump
    // 校验首字母 大写 /^[A-Z][a-z]*(\s[A-Z][a-z]*)*$/
     String name="^[A-Za-z àâäèéêëîïôœùûüÿçÀÂÄÈÉÊËÎÏÔŒÙÛÜŸÇ_\\.-]+$";

     //限制两位数字 ^\d{n}$ 两位
     String twoNumber ="^\\d{2}$";

     String number="^[0-9]*$";

     //  正整数的正则表达式(不包括0)
     String positiveInteger="^[1-9]\\d*$";

     //四种钱的表示形式我们可以接受:"10000.00" 和 "10,000.00", 和没有 "分" 的 "10000" 和 "10,000"
     String MONEY="^[0-9]+(.[0-9]+)?$";

     //由数字、26个英文大写字母或者下划线组成的字符串
     String ORDER_NUMBER="^[A-Z0-9-_]+$";

     //非负整数
     String positiveNumber="^[1-9]\\d*|0$";

     String lat="^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,10})?|90(\\.0{1,10})?)$";
     //纬度
     String lng="^[\\-\\+]?(0(\\.\\d{1,10})?|([1-9](\\d)?)(\\.\\d{1,10})?|1[0-7]\\d{1}(\\.\\d{1,10})?|180\\.0{1,10})$";

    //表达式 原帖地址：https://blog.csdn.net/gdhck123/article/details/86703978

    // 规格类型名称的正则表达式 （只允许大写字母、数字、-组成）
    String SPECIFICATNAME = "^[0-9A-Z-]+$";

}
