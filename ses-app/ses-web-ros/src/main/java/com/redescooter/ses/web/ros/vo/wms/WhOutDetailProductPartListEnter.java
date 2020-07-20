package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutDetailProductPartListEnter
 * @description: WhOutDetailProductPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:52
 */
@ApiModel(value = "详情产品列表", description = "详情产品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutDetailProductPartListEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
