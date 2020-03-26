package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveSupplierAnnexEnter
 * @description: SaveSupplierAnnexEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/24 20:21
 */
@ApiModel(value = "保存供应商附件", description = "保存供应商附件")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveSupplierAnnexEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "partsId")
    private Long partsId;

    @ApiModelProperty(value = "附件图片")
    private String picture;
}
