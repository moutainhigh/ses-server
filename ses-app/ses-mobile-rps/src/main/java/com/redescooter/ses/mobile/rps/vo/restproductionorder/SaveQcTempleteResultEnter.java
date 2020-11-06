package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveQcTempleteResultEnter
 * @description: SaveQcTempleteResultEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 16:27 
 */
@ApiModel(value = "保存质检结果", description = "保存质检结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveQcTempleteResultEnter extends GeneralEnter {

//    @ApiModelProperty(value = "主单据Id")
//    private Long id;

    @ApiModelProperty(value = "子单据Id")
    private Long orderBId;
    
    @ApiModelProperty(value = "质检项Id")
    private Long itemId;

    @ApiModelProperty(value = "图片URL，多个图片逗号分隔")
    private String imageUrls;

    @ApiModelProperty(value = "质检结果集Id")
    private Long qcResultId;

    @ApiModelProperty(value = "备注")
    private String remark;
}
