package com.redescooter.ses.api.common.constant;

import java.math.BigDecimal;

/**
 * @description: Constant
 * @author: Darren
 * @create: 2019/01/18 11:40
 */
public interface Constant {

    String DEFAULT_VERSION = "1.2.1";

    String DEFAULT_LANGUAGE = "en";

    String DEFAULT_COUNTRY = "US";

    String CHINA = "cn";

    /**
     * 逻辑删除标识，删除
     */
    int DR_TRUE = 1;

    /**
     * 逻辑删除标识，正常保留
     */
    int DR_FALSE = 0;

    /**
     * 默认编码格式
     */
    String UTF_8 = "UTF_8";

    /**
     * 菜单树根节点
     */
    long MENU_TREE_ROOT_ID = -1;

    /**
     * 部门树根节点
     */
    long DEPT_TREE_ROOT_ID = -1;

    /**
     * 区域树根节点
     */
    long AREA_TREE_ROOT_ID = 0;

    /**
     * 超级管理员用户名,原始adminRedE
     */
    String ADMIN_USER_NAME = "rede@redescooter.com";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "RedEScooter2019";
    /**
     * Excel批量导入车辆模板地址
     */
    String Import_Excel_Template = "https://rede.oss-cn-shanghai.aliyuncs.com/1567490261085.xlsx";
    //admin id
    Long ADMINUSERID = 1000000L;
    //百分比
    int AMOUNTP_ROPORTION = 100;

    // 经纬度范围
    // 最大经度
    String maxlng = "48.902862";
    // 最小经度
    String minlng = "48.840000";
    // 最大纬度
    String minlat = "2.250000";
    // 最小纬度
    String maxlat = "2.356000";

    // 经度参数名
    String lng = "lng";
    // 纬度参数名
    String lat = "lat";

    // 经纬度 精度
    int precision = 6;

    // 默认经纬度
    BigDecimal LATITUDE = new BigDecimal("48.862868");

    BigDecimal LONGITUDE = new BigDecimal("2.313960");

    //账户设置 下载导入模版的url
    String PARAMETER_DOWNLOAD_URL = "https://rede.oss-cn-shanghai.aliyuncs.com/1600332621562.xlsx";

    // 系统内置标识
    String SYSTEM_ROOT = "system::root";


}