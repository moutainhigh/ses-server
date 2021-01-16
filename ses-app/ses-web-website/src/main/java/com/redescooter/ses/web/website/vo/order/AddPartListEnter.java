package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/16 6:10 下午
 * @Description 添加配件
 **/
@ApiModel(value = "Add order Parts", description = "添加配件")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddPartListEnter extends GeneralEnter {

    /**
     * 配件ID
     */
    @ApiModelProperty(value = "partsId")
    private Long partsId;

    /**
     * 配件数量
     */
    @ApiModelProperty(value = "parts_qty")
    private String parts_qty;
}
