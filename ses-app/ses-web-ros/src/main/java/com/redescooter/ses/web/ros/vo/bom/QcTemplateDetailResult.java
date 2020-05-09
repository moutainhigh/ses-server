package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:QcTemplateDetailResult
 * @description: QcTemplateDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 10:00
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcTemplateDetailResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检名称")
    private String qcItemName;

    @ApiModelProperty(value = "质检项结果")
    private List<QcResultResult> qcResultResultList;
}
