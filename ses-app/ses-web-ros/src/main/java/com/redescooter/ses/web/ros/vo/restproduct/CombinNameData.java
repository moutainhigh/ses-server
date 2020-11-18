package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameCombinData
 * @Description
 * @Author Aleks
 * @Date2020/10/20 13:05
 * @Version V1.0
 **/
@Data
public class CombinNameData {

//    @ApiModelProperty("组装件id")
//    private Long productionCombinBomId;

    @ApiModelProperty("组装件名称")
    private String combinName;

}
