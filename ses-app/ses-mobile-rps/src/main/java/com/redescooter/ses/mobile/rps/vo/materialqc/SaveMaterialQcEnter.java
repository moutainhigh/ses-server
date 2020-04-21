package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveMaterialQcEnter
 * @description: SaveMaterialQcEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:45
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveMaterialQcEnter extends GeneralEnter {

    @ApiModelProperty(value = "采购单子表Id")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不能为空")
    private Long id;

    @ApiModelProperty(value = "产品序列号")
    private String serialNum;

    @ApiModelProperty(value = "质检数量")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "数量不能为空")
    private Integer qty;

    @ApiModelProperty(value = "采购单质检记录 json格式：  [\n" +
            "    {\n" +
            "        \"id\":132,\n" +
            "        \"partQcResultListJson\":{\n" +
            "            \"id\":123,\n" +
            "            \"partQcTemplateId\":123\n" +
            "        }\n" +
            "    }\n" +
            "]")
    @NotNull(code = ValidationExceptionCode.PART_TRMPLATE_RESULT_IS_EMPTY,message = "部件模板结果模板为空")
    private String partTemplateListJson;
}
