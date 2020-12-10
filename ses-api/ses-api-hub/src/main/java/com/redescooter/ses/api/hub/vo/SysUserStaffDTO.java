package com.redescooter.ses.api.hub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户员工信息 DTO
 * @author assert
 * @date 2020/12/10 11:01
 */
@Data
public class SysUserStaffDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 员工状态 1：正常，2：禁用
     */
    private Integer status;

    /**
     * 所属部门id
     */
    private Long deptId;

    /**
     * 所属岗位id
     */
    private Long positionId;

    /**
     * 所属角色id
     */
    private Long roleId;

    /**
     * 国家编码如+86
     */
    private String countryCode;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 入职日期
     */
    private Date entryDate;

    /**
     * 国籍
     */
    private String countryName;

    /**
     * 地址1
     */
    private String address1;

    /**
     * 地址2
     */
    private String address2;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 证件类型 1：身份证，2：驾驶证，3：护照
     */
    private Integer certificateType;

    /**
     * 图片1（身份证正面或者驾驶证或者护照）
     */
    private String certificatPicture1;

    /**
     * 图片2（身份证反面）
     */
    private String certificatPicture2;

    /**
     * 是否开通账号 0：否，1：是
     */
    private String openAccount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 员工头像
     */
    private String employeePicture;

    /**
     * 员工姓
     */
    private String firstName;

    /**
     * 员工名
     */
    private String lastName;

    /**
     * 员工全名
     */
    private String fullName;

    /**
     * 员工编码
     */
    private String code;

    /**
     * 性别 1：男，2：女
     */
    private Integer gender;

    /**
     * 用户ID
     */
    private Long sysUserId;

    /**
     * 是否开启安全码（0：否，1：是）
     */
    private Integer ifSafeCode;

    /**
     * 安全码
     */
    private String safeCode;

    /**
     * 系统内置标识
     */
    private String systemRoot;

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
