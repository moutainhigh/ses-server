package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveOrderFormResult
 * @description: SaveOrderFormResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/14 09:57
 */
@ApiModel(value = "Inquiry result return", description = "Inquiry result return")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveOrderFormResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;
}
