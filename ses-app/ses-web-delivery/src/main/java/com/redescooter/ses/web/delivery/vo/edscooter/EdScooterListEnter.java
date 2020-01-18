package com.redescooter.ses.web.delivery.vo.edscooter;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MobileListEnter
 * @description: MobileListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:28
 */
@ApiModel(value = "车辆列表", description = "车辆列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EdScooterListEnter extends PageEnter {
    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
