package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:ChinaInOrOutCountResult
 * @description: ChinaInOrOutCountResult
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/27 15:15
 */
@Data
@ApiModel(value = "中国仓库出入库的数据统计出参对象")
public class ChinaInOrOutCountResult extends GeneralResult {

    @ApiModelProperty("出入库类型，1：今日入库，2：今日出库")
    private Integer type;

    @ApiModelProperty("成品库数量")
    private Integer finishWhNum = 0;

    @ApiModelProperty("原料库数量")
    private Integer materialWhNum = 0;

    @ApiModelProperty("不合格品库数量")
    private Integer unqualifiedWhNum = 0;

    @ApiModelProperty("总数量")
    private Integer countNum = 0;

}
