package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameBomNameData
 * @Description
 * @Author Aleks
 * @Date2020/10/20 13:07
 * @Version V1.0
 **/
@Data
public class BomNameData {

    @ApiModelProperty("组装件编号")
    private String bomNo;

    @ApiModelProperty("组装件id")
    private Long productionCombinBomId;
}
