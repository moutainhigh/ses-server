package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutProductListEnter
 * @description: WhOutProductListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 14:25
 */
@ApiModel(value = "出库产品列表", description = "出库产品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutProductListEnter extends PageEnter {

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
