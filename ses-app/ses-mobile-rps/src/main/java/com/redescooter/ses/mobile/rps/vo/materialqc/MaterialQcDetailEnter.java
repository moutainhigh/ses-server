package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MaterialQcDetailEnter
 * @description: MaterialQcDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:36
 */
@ApiModel(value = "来料质检详情", description = "来料质检详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MaterialQcDetailEnter extends PageEnter {

    @ApiModelProperty(value = "id")
    private Long id;
}
