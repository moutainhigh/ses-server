package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameRosCheckAnnounSafeCode
 * @Description
 * @Author Aleks
 * @Date2020/9/23 19:46
 * @Version V1.0
 **/
@Data
public class RosCheckAnnounSafeCode extends GeneralEnter {

    @ApiModelProperty("发布人id")
    private Long principal;


    @ApiModelProperty("安全码")
    private String safeCode;


}
