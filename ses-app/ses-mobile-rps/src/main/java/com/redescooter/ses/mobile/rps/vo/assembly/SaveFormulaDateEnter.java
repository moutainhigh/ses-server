package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveFormulaDateEnter
 * @description: SaveFormulaDateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 09:53
 */
@ApiModel(value = "保存产品组装数据", description = "保存产品组装数据")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveFormulaDateEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件列表 格式：[\n" +
            "    {\n" +
            "        \"id\":123,\n" +
            "        \"qty\":1,\n" +
            "        \"serialN\":\"2312321312\"\n" +
            "    }\n" +
            "]")
    private String partListJson;
}
