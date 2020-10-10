package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassNameRosPartsSaveOrUpdateEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/23 18:23
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RosPartsSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("部件编号")
    private String partsNo;

    @ApiModelProperty("sec")
    private Long partsSec;

    @ApiModelProperty(value = "1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("是否可采购，0：否，1：是")
    private Integer snClass;

    @ApiModelProperty("是否有序列号，0：否，1：是")
    private Integer idCalss;

    @ApiModelProperty("是否是组装件，0：否，1：是")
    private Integer partsIsAssembly = 1;

    @ApiModelProperty("是否可用于组装件，0：否，1：是")
    private Integer partsIsForAssembly = 1;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @ApiModelProperty(value = "生产周期")
    private Integer procurementCycle;

    @ApiModelProperty(value = "图纸")
    private String dwg;

    @ApiModelProperty(value = "是否发布，0：否，1：是")
    private Integer isAnnoun = 0;

    // 追加
    @ApiModelProperty(value = "级别")
    private Integer level;

    /**
     * 母图纸
     */
    @ApiModelProperty(value = "母图纸")
    private String mainDrawing;

    /**
     * 设变号
     */
    @ApiModelProperty(value = "设变号")
    private String ecnNumber;

    /**
     * 品目
     */
    @ApiModelProperty(value = "品目")
    private String item;

    /**
     * 销售分区
     */
    @ApiModelProperty(value = "销售分区")
    private String sellCalss;

    /**
     * 图纸尺寸
     */
    @ApiModelProperty(value = "图纸尺寸")
    private String drawingSize;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 供应商2
     */
    @ApiModelProperty(value = "供应商2")
    private Long supplierId2;

    /**
     * 依存度区分
     */
    @ApiModelProperty(value = "依存度区分")
    private String rateTyp;
}
