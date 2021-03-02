package com.redescooter.ses.web.website.vo.distributor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:43 上午
 * @Description 编辑经销商入参
 **/

@ApiModel(value = "编辑经销商入参", description = "编辑经销商入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityDealerEnter extends AddDealerEnter {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
}
