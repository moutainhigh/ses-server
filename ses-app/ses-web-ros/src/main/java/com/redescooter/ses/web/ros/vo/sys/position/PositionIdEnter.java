package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNamePositionIdEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/8 14:55
 * @Version V1.0
 **/
@Data
public class PositionIdEnter extends GeneralEnter {

    @Autowired
    private Long positionId;

}
