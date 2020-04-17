package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:QcResuleEnter
 * @description: QcResuleEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 09:53
 */
@ApiModel(value = "质检结果项", description = "质检结果项")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcResultEnter extends GeneralEnter {

    @ApiModelProperty(value = "通过标签")
    private Boolean passFlag=false;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "是否上传图片")
    private Boolean uploadPictureFalg;

    @ApiModelProperty(value = "结果项排序")
    private Integer resultSequence;
}
