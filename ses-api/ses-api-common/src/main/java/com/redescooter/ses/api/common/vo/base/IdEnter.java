package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:IdEnter
 * @description: IdEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:32
 */
@ApiModel(value = "Id入参", description = "Id入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class IdEnter extends GeneralEnter {

    @ApiModelProperty(value = "业务Id")
    @NotNull
    private Long id;
}
