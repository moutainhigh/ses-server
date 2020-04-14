package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrepareMaterialDetailEnter
 * @description: PrepareMaterialDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:32
 */
@ApiModel(value = "备料详情入参", description = "备料详情入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrepareMaterialDetailEnter extends PageEnter {

    @ApiModelProperty(value = "id")
    private Long id;
}
