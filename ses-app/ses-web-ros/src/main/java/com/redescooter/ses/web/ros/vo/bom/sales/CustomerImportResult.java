package com.redescooter.ses.web.ros.vo.bom.sales;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@ApiModel(value = "表格导入结果集", description = "表格导入结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomerImportResult {

    @ApiModelProperty(value = "上传成功表示")
    private Boolean success;
    @ApiModelProperty(value = "成功解析个数")
    private Integer successNum;
    @ApiModelProperty(value = "失败数个数")
    private Integer failNum;
    @ApiModelProperty(value = "批次号")
    private String batchNo;
    @ApiModelProperty(value = "是否包含重复数据")
    private Boolean repeatFlag;
    @ApiModelProperty(value = "解析错误信息")
    private List<Map<String, String>> errorMsgList = new ArrayList<>();
}
