package com.redescooter.ses.web.ros.vo.production.wh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhEnter
 * @description: WhEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:34
 */
@ApiModel(value = "库存列表入参", description = "库存列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhEnter extends PageEnter {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
