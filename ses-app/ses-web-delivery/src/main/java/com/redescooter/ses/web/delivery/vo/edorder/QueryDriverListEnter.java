package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:QueryDriverListEnter
 * @description: QueryDriverListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/14 13:59
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QueryDriverListEnter extends GeneralEnter {


    @ApiModelProperty(value = "不需要查询的司机Id")
    private Long id;
}
