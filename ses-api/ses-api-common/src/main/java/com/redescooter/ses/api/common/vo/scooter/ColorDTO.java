package com.redescooter.ses.api.common.vo.scooter;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 颜色信息 DTO
 * @author assert
 * @date 2020/12/9 10:56
 */
@Data
public class ColorDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 色值
     */
    private String colorValue;

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
