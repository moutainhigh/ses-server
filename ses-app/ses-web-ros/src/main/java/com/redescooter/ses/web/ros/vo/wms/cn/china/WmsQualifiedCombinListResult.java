package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:56
 */
@Data
@ApiModel("不合格品库组装件列表出参")
public class WmsQualifiedCombinListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("组装件编号")
    private String combinNo;

    @ApiModelProperty("中文名称")
    private String cnName;

    @ApiModelProperty("英文名称")
    private String enName;

    @ApiModelProperty("法文名称")
    private String frName;

    @ApiModelProperty("库存数量")
    private Integer qty;

}
