package com.redescooter.ses.web.website.vo.parts;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:37 上午
 * @Description 编辑配件入参
 **/

@ApiModel(value = "编辑配件入参", description = "编辑配件入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityPartsEnter extends AddPartsEnter{
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;
}
