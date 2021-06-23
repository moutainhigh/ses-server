package com.redescooter.ses.service.mobile.c.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConUserScooterMapper extends BaseMapper<ConUserScooter> {
    int updateBatch(List<ConUserScooter> list);

    int batchInsert(@Param("list") List<ConUserScooter> list);

    int insertOrUpdate(ConUserScooter record);

    int insertOrUpdateSelective(ConUserScooter record);

    /**
     * 删除客户车辆关系表
     */
    int deleteUserScooter(@Param("scooterId") Long scooterId);

}