package com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBriefcaseModelEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBriefcaseTypeEnums;

import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "公文包列表", description = "公文包列表")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyBriefcasesListSearchEnter {

    @ApiModelProperty(value = "公文包类型")
    private SellsyBriefcaseTypeEnums linkedtype;

    @ApiModelProperty(value = "关联Id 指相对应的单据Id")
    private int linkedid;

    @ApiModelProperty(value = "公文包型号")
    private SellsyBriefcaseModelEnums mode;

}
