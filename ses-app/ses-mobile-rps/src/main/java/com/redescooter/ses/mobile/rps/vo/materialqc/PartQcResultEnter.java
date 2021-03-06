package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PartQcResultEnter
 * @description: PartQcResultEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 18:00
 */
@ApiModel(value = "部件模板结果", description = "部件模板结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PartQcResultEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;
}
