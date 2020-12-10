package com.redescooter.ses.api.common.vo.scooter;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 规格分组信息 DTO
 * @author assert
 * @date 2020/12/10 17:45
 */
@Data
public class SpecificGroupDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 产品类型，1：整车，2：组装
     */
    private Integer productionType;

    /**
     * 备注
     */
    private String remark;

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
