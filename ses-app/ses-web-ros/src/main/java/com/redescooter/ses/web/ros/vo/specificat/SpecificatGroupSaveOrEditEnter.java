package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatGroupSaveOrEditEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 15:37
 * @Version V1.0
 **/
@Data
public class SpecificatGroupSaveOrEditEnter extends GeneralEnter {

    @ApiModelProperty(value="主键")
    private Long id;


    @TableField(value = "group_name")
    @ApiModelProperty(value="分组名称")
    private String groupName;

    /**
     * 产品类型，1：整车，2：组装
     */
    @TableField(value = "production_type")
    @ApiModelProperty(value="产品类型，1：整车，2：组装")
    private Integer productionType;



}
