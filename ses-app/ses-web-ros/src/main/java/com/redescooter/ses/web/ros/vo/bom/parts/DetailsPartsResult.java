package com.redescooter.ses.web.ros.vo.bom.parts;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassName AddPartsEnter
 * @Author Jerry
 * @date 2020/02/26 20:37
 * @Description:
 */
@ApiModel(value = "部品详情", description = "部品详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DetailsPartsResult extends GeneralResult {
    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 导入批次号
     */
    @ApiModelProperty(value = "导入批次号")
    private String importLot;
    /**
     * 部品号 部品号
     */
    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    @ApiModelProperty(value = "部品类型")
    private String partsType;

    /**
     * 项目区域 项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。
     */
    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。")
    private String sec;

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
    private String snClassFlag;

    /**
     * 生产周期 生产周期
     */
    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    /**
     * 供应商 供应商
     */
    @ApiModelProperty(value = "供应商主键")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 图纸 图纸
     */
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 是否有子集
     */
    @ApiModelProperty(value = "是否有子集")
    private boolean packCount;

    /**
     * 备注 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

}
