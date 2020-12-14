package com.redescooter.ses.web.ros.vo.restproduct;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRosPartsListResult
 * @Description
 * @Author Aleks
 * @Date2020/9/23 17:52
 * @Version V1.0
 **/
@Data
public class RosPartsListResult extends GeneralResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("部件编号")
    private String partsNo;

    @ApiModelProperty("sec")
    private Long partsSec;

    @ApiModelProperty("sec名称")
    private String partsSecName;

    @ApiModelProperty("部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("是否可采购，0：否，1：是")
    private Integer snClass;

    @ApiModelProperty("是否是组装件，0：否，1：是")
    private Integer partsIsAssembly;

    @ApiModelProperty("是否可用于组装件，0：否，1：是")
    private Integer partsIsForAssembly;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "采购周期")
    private Integer procurementCycle;

    @ApiModelProperty(value = "图纸url")
    private String dwg;

    @ApiModelProperty(value = "页面类型,1:草稿，2：部件")
    private Integer classType;

    @ApiModelProperty("是否禁用，0：否，1：是")
    private Integer disable = 0;

    @TableField(value = "id_calss")
    @ApiModelProperty(value = "是否有序列号，0：否，1：是")
    private Integer idCalss;

    @ApiModelProperty(value = "QC模板提示Icon,true 表示 不存在质检模板 展示提示Icon，false 存在 质检模板 无需展示")
    private Boolean qcTempletePromptIcon = Boolean.TRUE;
}
