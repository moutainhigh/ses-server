package com.redescooter.ses.api.foundation.vo.app;


import lombok.Data;
import java.util.Date;

/**
 * 应用版本管理 DTO
 * @author assert
 * @date 2020/11/30 12:03
 */
@Data
public class AppVersionDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 系统ID
     */
    private String systemId;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * IOS or ANDROID or SCS(关注type字段即可,systemType无需关注)
     */
    private Integer systemType;

    /**
     * 版本类型：1.IOS 2. ANDROID 3.SCS
     */
    private Integer type;

    /**
     * 版本应用编码
     */
    private String code;

    /**
     * 是否强制更新 1时true，0时false
     */
    private Integer isForce;

    /**
     * 更新提示内容
     */
    private String updateContent;

    /**
     * 更新地址
     */
    private String updateLink;

    /**
     * 新版本号
     */
    private String newVersionNum;

    /**
     * 安装包大小
     */
    private String packageSize;

    /**
     * 新版本号名称
     */
    private String newVersionName;

    /**
     * 最小版本
     */
    private Integer minVersionNum;

    /**
     * 最小版本号名称
     */
    private String ninVersionName;

    /**
     * 版本状态 0未发布 1已发布 2生效中
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
