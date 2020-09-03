package com.redescooter.ses.api.common.constant;

/**
 * @ClassName:JedisConstant
 * @description: JedisConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/23 14:09
 */
public interface JedisConstant {

    //jedis 枷锁 key值
    String JEDIS_KEY = "edTaskOrder";
    
    //邮箱验证码登陆模式  验证码的存放位置
    String EMAIL_LOGIN_CODE="email:login:code:";
    
    // 登陆的时候 密码输错了，在这个路劲记录输错的次数
    String LOGIN_PSD_ERROR_NUM = "login:psd:error:num:";

    //默认超时时间为1s
    int DEFAULT_EXPIRE = 1;
}
