package com.redescooter.ses.web.ros.vo.distributor.enter;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 经销商列表入参
 * @Author Chris
 * @Date 2020/12/16 16:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商列表入参", description = "经销商列表入参")
public class DistributorListEnter extends PageEnter implements Serializable {

    private static final long serialVersionUID = -2104991747921351199L;

    @ApiModelProperty(value = "状态 0全部 1启用 2未启用")
    private Integer status;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "邮政编码")
    private String cp;

    @ApiModelProperty(value = "关键字 包括门店名称,门店编码,联系电话和邮件地址")
    private String keyword;

}
