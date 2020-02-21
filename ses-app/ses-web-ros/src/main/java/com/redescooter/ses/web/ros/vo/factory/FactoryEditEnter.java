package com.redescooter.ses.web.ros.vo.factory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "代工厂修改", description = "代工厂修改")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class FactoryEditEnter extends FactorySaveEnter {

    @ApiModelProperty(value = "主键")
    private Long id;
}
