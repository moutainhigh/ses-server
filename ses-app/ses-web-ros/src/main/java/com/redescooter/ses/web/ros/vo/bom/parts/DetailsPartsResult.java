package com.redescooter.ses.web.ros.vo.bom.parts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value="用户ID")
    private Long userId;

    @ApiModelProperty(value = "导入批次号")
    private String importLot;

    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    @ApiModelProperty(value = "部品类型")
    private String partsType;

    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。")
    private String sec;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "是否可销售,SC仅可采购，SSC可销售可采购")
    private String snClassFlag;

    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    @ApiModelProperty(value = "供应商主键")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "图纸")
    private String dwg;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "历史部品号")
    List<HistoryPartsDto> historyHist = new ArrayList<>();
}
