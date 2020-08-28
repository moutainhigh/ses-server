package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SellsyDocumentEnter
 * @description: SellsyDocumentEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 14:57
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentOneEnter {
    
    @ApiModelProperty(value = "单据类型",required = true)
    private String doctype;
    
    @ApiModelProperty(value = "文件标识",required = true)
    private int docid;
}
