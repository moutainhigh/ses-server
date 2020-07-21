package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutListEnter
 * @description: WhOutListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:42
 */
@ApiModel(value = "出库单列表入参", description = "出库单列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutListEnter extends PageResult {

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
