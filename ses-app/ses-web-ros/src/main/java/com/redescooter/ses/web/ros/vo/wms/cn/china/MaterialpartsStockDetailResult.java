package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 18:46
 */
@Data
@ApiModel("原料库部件详情出参")
public class MaterialpartsStockDetailResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("部件编号")
    private String partsNo;

    @ApiModelProperty("部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("英文名字")
    private String enName;

    @ApiModelProperty("中文名字")
    private String cnName;

    @ApiModelProperty("入库记录")
    private List<WmsStockRecordResult> recordList;

}
