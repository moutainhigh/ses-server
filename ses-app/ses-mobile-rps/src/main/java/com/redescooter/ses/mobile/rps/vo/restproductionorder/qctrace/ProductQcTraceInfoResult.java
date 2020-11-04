package com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductQcTraceInfoResult
 * @description: ProductQcTraceInfoResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 18:47 
 */
@ApiModel(value = "模版质检信息", description = "模版质检信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductQcTraceInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检项结果Id")
    private Long productQcTemplateBId;

    @ApiModelProperty(value = "质检项结果名称")
    private String productQcTemplateBName;

    @ApiModelProperty(value = "质检项Id")
    private Long productQcTemplateId;

    @ApiModelProperty(value = "质检项名称")
    private String productQcTemplateName;

    @ApiModelProperty(value = "质检记录Id")
    private Long qcItemId;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "创建人")
    private Long createdId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}
