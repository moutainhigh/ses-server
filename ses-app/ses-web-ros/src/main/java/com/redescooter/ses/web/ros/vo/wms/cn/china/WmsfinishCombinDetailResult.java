package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 16:25
 */
@Data
@ApiModel("成品库组装件库存的详情出参")
public class WmsfinishCombinDetailResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 组装件编号
     */
    @ApiModelProperty("组装件编号")
    private String combinNo;

    /**
     * 中文名称
     */
    @ApiModelProperty("中文名称")
    private String cnName;

    /**
     * 英文名称
     */
    @ApiModelProperty("英文名称")
    private String enName;

    @ApiModelProperty("入库记录")
    private List<WmsStockRecordResult> recordList;

}
