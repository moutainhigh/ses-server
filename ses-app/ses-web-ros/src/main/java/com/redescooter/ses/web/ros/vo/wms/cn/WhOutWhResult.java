package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutWhResult
 * @description: WhOutWhResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 14:08
 */
@ApiModel(value = "仓库列表出参", description = "仓库列表出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutWhResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "仓库名字")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

}
