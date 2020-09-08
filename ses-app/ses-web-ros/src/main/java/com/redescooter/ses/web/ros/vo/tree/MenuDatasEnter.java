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

    @ApiModelProperty("类型，查菜单传2，查目录传1")
    private Integer type;

}
