package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@ApiModel(value = "可分配司机列表", description = "可分配司机列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AttribuableDriverListEnter extends GeneralEnter {
    @ApiModelProperty(value = "不可分配司机Id",required = false)
    private List<Long> ids;
}
