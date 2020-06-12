package com.redescooter.ses.api.common.constant;

/**
 * @ClassName:RegexpConstant
 * @description: RegexpConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/12 13:35
 */
public interface RegexpConstant {

    //特殊字符 正则表达式
     String specialCharacters="[~!@#$%^&*()=+[\\]{}''\";:/?.,><`|！·￥…—（）\\-、；：。，》《]";

    //邮箱地址
     String email="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

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
     //不校验首字母大写
     //比如：Jack、Jack Chan、Donald Trump
    // 校验首字母 大写 /^[A-Z][a-z]*(\s[A-Z][a-z]*)*$/
     String name="/^[A-Za-z]*(\\s[A-Za-z]*)*$/";

     //限制两位数字 ^\d{n}$ 两位
     String twoNumber ="^\\d{2}$";

     String number="^[0-9]*$";

    //表达式 原帖地址：https://blog.csdn.net/gdhck123/article/details/86703978
}
