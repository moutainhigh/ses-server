package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 新增法国仓库入库单入参
 * @Author Chris
 * @Date 2021/4/15 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "新增法国仓库入库单入参", description = "新增法国仓库入库单入参")
public class FrInWhOrderAddEnter extends GeneralEnter {

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他")
    private Integer inWhType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("入库明细,json格式的字符串")
    private String st;

}
