package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrincipalsEnter
 * @description: PrincipalsEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/14 14:58
 */
@ApiModel(value = "负责人列表", description = "负责人列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrincipalsEnter extends GeneralEnter {

    @ApiModelProperty(value = "部门Id")
    private Long id;
}
