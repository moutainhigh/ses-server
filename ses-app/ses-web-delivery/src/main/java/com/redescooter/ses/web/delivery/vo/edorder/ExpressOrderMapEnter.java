package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/2/2 16:06
 *  @version：V 1.2
 *  @Description: ExpressOrderMapEnter
 */
@ApiModel(value = "快递地图入参", description = "快递地入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ExpressOrderMapEnter extends GeneralEnter {

    @ApiModelProperty(value = "状态列表")
    private List<String> statusList= new ArrayList<>();
}
