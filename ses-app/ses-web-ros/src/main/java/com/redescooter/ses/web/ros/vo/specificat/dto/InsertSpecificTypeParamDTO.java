package com.redescooter.ses.web.ros.vo.specificat.dto;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 新增/修改规格类型入参 DTO
 * @author assert
 * @date 2020/12/7 16:34
 */
@Data
@ApiModel(value = "新增/修改规格类型入参")
public class InsertSpecificTypeParamDTO extends GeneralEnter {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @NotEmpty(code = ValidationExceptionCode.SPECIFICATION_NAME_IS_NOT_EMPTY, message = "规格名称不能为空")
    @Regexp(value = RegexpConstant.SPECIFICATNAME, code = ValidationExceptionCode.SPECIFICATION_TYPE_NAME_ILLEGAL, message = "规格名称仅支持大写字母、数字和“-”")
    @ApiModelProperty(value = "规格名称", dataType = "String")
    private String specificatName;

    @ApiModelProperty(value = "规格编码", dataType = "String")
    private String code;

    @NotNull(code = ValidationExceptionCode.GROUP_ID_IS_EMPTY, message = "分组id不能为空")
    @ApiModelProperty(value = "分组id", dataType = "Long")
    private Long groupId;

    @NotEmpty(code = ValidationExceptionCode.DEF_IS_NOT_EMPTY, message = "自定义项不能为空")
    @ApiModelProperty(value = "规格自定义项分组集合(前端传入json数组)", dataType = "jsonArray")
    private String st;

    @ApiModelProperty(value = "创建人", dataType = "Long", hidden = true)
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", dataType = "Date", hidden = true)
    private Date createdTime;

    @ApiModelProperty(value = "更新人", dataType = "Long", hidden = true)
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间", dataType = "Date", hidden = true)
    private Date updatedTime;

}
