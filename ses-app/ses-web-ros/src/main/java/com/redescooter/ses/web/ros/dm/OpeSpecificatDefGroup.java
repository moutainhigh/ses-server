package com.redescooter.ses.web.ros.dm;

import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2020/12/8 11:55
 */
@Data
public class OpeSpecificatDefGroup {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是否删除标记 0未删除 1已删除
     */
    private Integer dr;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 创建人
     */
    private Integer createdBy;

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

    /**
     * 所属规格id
     */
    private Integer specificatId;
}