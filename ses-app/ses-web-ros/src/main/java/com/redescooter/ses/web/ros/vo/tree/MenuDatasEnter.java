package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamemenuDatasEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/7 15:47
 * @Version V1.0
 **/
@Data
public class MenuDatasEnter extends GeneralEnter {

    @ApiModelProperty(value = "当前要编辑数据的id", required = true)
    private Long id;

}
