package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:SaveScooterPartResult
 * @description: SaveScooterPartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:12
 */
@ApiModel(value = "部品列表入参", description = "部品列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PartListEnter extends PageEnter {

    @ApiModelProperty(value = "部件区域")
    private String sec;

    @ApiModelProperty(value = "导入批次号")
    private String importLot;

    @ApiModelProperty(value = "关键字查询")
    private String keyword;

    @ApiModelProperty(value = "选中的部件")
    private List<Long> ids;

}
