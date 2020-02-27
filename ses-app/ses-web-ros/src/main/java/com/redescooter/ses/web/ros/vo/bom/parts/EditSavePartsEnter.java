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
    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    /**
     * 类型 类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery
     */
    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
    @NotNull(code = ValidationExceptionCode.PARTS_TYPE_IS_EMPTY, message = "类型 为空")
    private String partsType;

    /**
     * 项目区域 项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。
     */
    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。")
    @NotNull(code = ValidationExceptionCode.PARTS_SEC_IS_EMPTY, message = "项目区域 为空")
    private String sec;

    /**
     * 部品号 部品号
     */
    @ApiModelProperty(value = "部品号")
    @NotNull(code = ValidationExceptionCode.PARTS_NUM_IS_EMPTY, message = "部品号 为空")
    private String partsNumber;

    /**
     * 中文名称 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购
     */
    @ApiModelProperty(value = "是否可销售,0:SC仅可采购，1:SSC可销售可采购")
    @NotNull(code = ValidationExceptionCode.PARTS_SN_CLASS_IS_EMPTY, message = "是否可销售 为空")
    private String snClassFlag;

    /**
     * 生产周期 生产周期
     */
    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    /**
     * 供应商 供应商
     */
    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    /**
     * 图纸 图纸
     */
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 备注 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;
}
