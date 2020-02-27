package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:23 下午
 * @ClassName: ImportExcelOrderResult
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ImportExcelPartsResult extends GeneralResult {

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
