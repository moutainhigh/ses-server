package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameInWhouseListEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:48
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单的列表传参",description = "入库单的列表传参")
public class InWhouseListEnter extends PageEnter {

    @ApiModelProperty("入库单状态，1： 草稿，:10：待质检，20：质检中，30：已入库")
    private Integer inWhStatus;


}
