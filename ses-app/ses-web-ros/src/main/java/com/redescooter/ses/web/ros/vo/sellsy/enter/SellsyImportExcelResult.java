package com.redescooter.ses.web.ros.vo.sellsy.enter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value = "表格导入结果集", description = "表格导入结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyImportExcelResult {

    @ApiModelProperty(value = "上传成功表示")
    private Boolean success;
    @ApiModelProperty(value = "成功解析个数")
    private Integer successNum;
    @ApiModelProperty(value = "失败数个数")
    private Integer failNum;
    @ApiModelProperty(value = "是否包含重复数据")
    private Boolean repeatFlag;
    @ApiModelProperty(value = "解析错误信息")
    private List<Map<String, String>> errorMsgList = new ArrayList<>();
}
