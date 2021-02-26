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
    String EMAIL_LOGIN_CODE = "email:login:code:";

    // 登陆的时候 密码输错了，在这个路劲记录输错的次数
    String LOGIN_PSD_ERROR_NUM = "login:psd:error:num:";

    String LOGIN_ROLE_DATA = "login:role:data:";

    // 校验安全码的结果放缓存
    String CHECK_SAFE_CODE_RESULT = "check:safe:code:result:";

    //第一次登陆 需要重置密码的缓存地址
    String FIRST_LOGIN_RESET_PSD = "first:login:reset:psd:";

    //默认超时时间为1s
    int DEFAULT_EXPIRE = 1;

    // 接口权限
    String PERMISSION = "menu:permission:";

}
