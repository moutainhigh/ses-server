package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.print.PrinterAbortException;

import io.swagger.annotations.*;

/**
 * @ClassName:SavePrepareMaterialListEnter
 * @description: SavePrepareMaterialListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:19
 */
@ApiModel(value = "保存备料列表", description = "保存备料列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SavePrepareMaterialListEnter extends GeneralEnter {

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "批次号")
    private String batchN;

    @ApiModelProperty(value = "序列号")
    private String serialN;
}
