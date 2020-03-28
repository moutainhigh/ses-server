package com.redescooter.ses.api.common.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SavePurchasingNodeEnter
 * @description: SavePurchasingNodeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/25 10:00
 */
@ApiModel(value = "保存采购单节点", description = "保存采购单节点")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveNodeEnter extends GeneralEnter {

    @ApiModelProperty(value = "采购单Id")
    private Long id;

    @ApiModelProperty(value = "采购单状态")
    private String status;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "备注")
    private String memo;
}
