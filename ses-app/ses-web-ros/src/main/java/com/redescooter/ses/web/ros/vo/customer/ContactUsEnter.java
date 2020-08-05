package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassNameContactUsEnter
 * @Description
 * @Author Aleks
 * @Date2020/8/5 10:03
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactUsEnter extends GeneralEnter {

    @ApiModelProperty("主键id")
    private Long id;

}
