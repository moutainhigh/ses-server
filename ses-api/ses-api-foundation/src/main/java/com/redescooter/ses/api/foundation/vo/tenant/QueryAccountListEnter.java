package com.redescooter.ses.api.foundation.vo.tenant;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:QueryAccountListEnter
 * @description: QueryAccountListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 18:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryAccountListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "emailList")
    private List<String> emailList;

    @ApiModelProperty(value = "激活开始时间")
    private String startActivationTime;

    @ApiModelProperty(value = "激活结束时间")
    private String endActivationTime;

    @ApiModelProperty(value = "到期开始时间")
    private String endExpirationTime;

    @ApiModelProperty(value = "到期结束时间")
    private String startExpirationTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
