package com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:SaveProductQcInfoEnter
 * @description: SaveProductQcInfoEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 19:05 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveProductQcInfoEnter extends GeneralEnter {

    @ApiModelProperty(value="质检项结果Id")
    private Long productQcTemplateBId;

    @ApiModelProperty(value="质检结果")
    private String productQcTemplateBName;

    @ApiModelProperty(value="质检项Id")
    private Long productQcTemplateId;

    @ApiModelProperty(value="质检项名称")
    private String productQcTemplateName;

    @ApiModelProperty(value="质检图片（多个图片逗号分隔）")
    private String picture;

    @ApiModelProperty(value="备注")
    private String remark;

}
