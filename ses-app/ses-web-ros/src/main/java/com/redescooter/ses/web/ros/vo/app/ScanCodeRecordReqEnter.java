package com.redescooter.ses.web.ros.vo.app;

import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ScanCodeRecordReqEnter
 * @Description
 * @Author Charles
 * @Date 2021/06/04 17:01
 */
@Data
@ApiModel(value = "部件查询")
@NoArgsConstructor
@AllArgsConstructor
public class ScanCodeRecordReqEnter extends PageResult {

    @ApiModelProperty("查询车牌名/ Rsn")
    private String searchContent;
}
