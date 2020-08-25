package com.redescooter.ses.web.ros.vo.sellsy.result;

import com.sellsy.apientities.SellsyResponseInfo;
import com.sellsy.coreConnector.SellsyApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;

/**
 * @ClassName:SellsyGeneralResult
 * @description: SellsyGeneralResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 16:53
 */
@ApiModel(value = "Sellsy 通用反参", description = "\"Sellsy 通用反参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyGeneralResult {
    
    @ApiModelProperty(value = "错误")
    private String error;
    
    @ApiModelProperty(value = "是否调用成功 success 或者失败")
    private String status;
    
    @ApiModelProperty(value = "返回值")
    private SellsyApiResponse result;
    
    @ApiModelProperty(value = "分页信息")
    private SellsyResponseInfo sellsyResponseInfo;
}
