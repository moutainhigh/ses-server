package com.redescooter.ses.mobile.rps.vo.printentry;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintEntryOrderEnter extends PageEnter {

    @ApiModelProperty(value = "单据Id")
    private Long id;

    @ApiModelProperty(value = "模块名称 1、来料质检、2、采购入库 3、带备料 4、整车组装，5、整车质检，6、生产入库", allowableValues = "1,2,3,4,5,6")
    private String businessModule;
}
