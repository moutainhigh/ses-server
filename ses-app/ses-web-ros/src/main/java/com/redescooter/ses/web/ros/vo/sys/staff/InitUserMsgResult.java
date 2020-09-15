package com.redescooter.ses.web.ros.vo.sys.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @ClassNameInitUserMsgResult
 * @Description
 * @Author Aleks
 * @Date2020/9/14 16:45
 * @Version V1.0
 **/
@Data
public class InitUserMsgResult implements Serializable {

    @ApiModelProperty("可查看数据的部门的id,如果这个东西为空，则表示只能查看自己的数据")
    private Set<Long> deptIds;

}
