package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameChooseScooterIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/24 17:03
 * @Version V1.0
 **/

@ApiModel(value = "分配摩托车列表id入参", description = "分配摩托车列表id入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ChooseScooterIdEnter extends PageEnter {

    @ApiModelProperty(value = "主键")
    private Long id;


}
