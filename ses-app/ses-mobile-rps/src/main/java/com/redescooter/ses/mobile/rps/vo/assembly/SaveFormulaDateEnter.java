package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
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

    @ApiModelProperty(value = "id 子表Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "Id 不能为空")
    private Long id;

    @ApiModelProperty(value = "条码打印结果")
    @NotNull(code = com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode.PRINT_CODE_RESULT,message = "条码打印结果不能为空")
    private Boolean printCodeResult;

    @ApiModelProperty(value = "部件列表 格式：[\n" +
            "    {\n" +
            "        \"id\":123,\n" +
            "        \"qty\":1,\n" +
            "        \"serialN\":\"2312321312\"\n" +
            "    }\n" +
            "]")
    @NotNull(code = com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode.PART_LIST_JSON,message = "部件列表不能为空")
    private String partListJson;
}
