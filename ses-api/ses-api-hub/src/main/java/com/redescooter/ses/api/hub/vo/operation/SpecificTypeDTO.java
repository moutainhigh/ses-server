package com.redescooter.ses.api.hub.vo.operation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 规格类型信息 DTO
 * @author assert
 * @date 2020/12/15 13:24
 */
@Data
public class SpecificTypeDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分组名称
     */
    private String specificatName;

    /**
     * 规格类型编码
     */
    private String specificatCode;

    /**
     * 所属分组
     */
    private Long groupId;

    /**
     * 所属分组名称
     */
    private String groupName;

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
