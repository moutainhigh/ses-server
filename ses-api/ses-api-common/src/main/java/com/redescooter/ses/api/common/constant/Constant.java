package com.redescooter.ses.api.common.constant;

import java.math.BigDecimal;

/**
 * @description: Constant
 * @author: Darren
 * @create: 2019/01/18 11:40
 */
public interface Constant {

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
    String ADMIN_USER_NAME = "root@redescooter.com";

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
    //默认经纬度
    BigDecimal LATITUDE = new BigDecimal("48.862868");

    BigDecimal LONGITUDE = new BigDecimal("2.313960");
}
