package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:ScanBarCodeEnter
 * @description: ScanBarCodeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/25 13:24
 */
@ApiModel(value = "扫码枪质检入参", description = "扫码枪质检入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ScanBarCodeEnter extends GeneralEnter {

    @ApiModelProperty(value = "采购单Id")
    private Long purchasingId;

    @ApiModelProperty(value = "部品Id")
    private Long partId;

    @ApiModelProperty(value = "成功的部品编号")
    private List<String> successParts;

    @ApiModelProperty(value = "失败的部品编号")
    private List<String> failParts;
}
