package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName EditPartsEnter
 * @Author Jerry
 * @date 2020/02/26 20:09
 * @Description:
 */
@ApiModel(value = "部品编辑入参", description = "部品编辑入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EditSavePartsEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键",required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery",required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_TYPE_IS_EMPTY, message = "类型 为空")
    private String partsType;

    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。",required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_SEC_IS_EMPTY, message = "项目区域 为空")
    private String sec;

    @ApiModelProperty(value = "部品号",required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_NUM_IS_EMPTY, message = "部品号 为空")
    private String partsNumber;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "是否可销售,0:SC仅可采购，1:SSC可销售可采购",required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_SN_CLASS_IS_EMPTY, message = "是否可销售 为空")
    private String snClassFlag;

    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    @ApiModelProperty(value = "图纸")
    private String dwg;

    @ApiModelProperty(value = "备注")
    private String note;
}
