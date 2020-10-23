package com.redescooter.ses.mobile.rps.vo.printentry;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/23 14:29
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "", description = "")
@Data
@EqualsAndHashCode(callSuper = true)
public class PrintEnteyEnter extends PageEnter {
    @ApiModelProperty(value = "模块名称 1、来料质检、2、采购入库 3、带备料 4、整车组装，5、整车质检，6、生产入库", allowableValues = "1,2,3,4,5,6")
    private String businessModule;

    @ApiModelProperty(value = "关键子")
    private String keyword;
}
