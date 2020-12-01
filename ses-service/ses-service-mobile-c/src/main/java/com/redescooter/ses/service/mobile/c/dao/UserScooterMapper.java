package com.redescooter.ses.service.mobile.c.dao;

import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import org.apache.ibatis.annotations.Param;

/**
 * 司机分配车辆相关Mapper接口
 * @author assert
 * @date 2020/11/27 14:31
 */
public interface UserScooterMapper {

    /**
     * 根据userId、status查询司机分配车辆信息
     * @param userId
     * @param status
     * @return com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter
     * @author assert
     * @date 2020/11/27
    */
    ConUserScooter getUserScooterByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

}
