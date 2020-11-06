package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:saveQcResultEnter
 * @description: saveQcResultEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 15:25 
 */
@ApiModel(value = "保存质检结果", description = "保存质检结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveQcResultEnter extends GeneralEnter {
    @ApiModelProperty(value = "子单据Id")
    private Long id;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "序列号")
    private String serialNum;

    @ApiModelProperty(value = "是否存在序列号")
    private Boolean idClass;

    @ApiModelProperty(value = "批次号")
    private String lot;

    @ApiModelProperty(value = "质检数量")
    private Integer qty;

    @ApiModelProperty(value = "图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "质检结果 json 格式：[{\"id\":123,\"imageUrls\":\"\",\"qcResultId\":321313,\"remark\":\"\"}]")
    private String st;
}
