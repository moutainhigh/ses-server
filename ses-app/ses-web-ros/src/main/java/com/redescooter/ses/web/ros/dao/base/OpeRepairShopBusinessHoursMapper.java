package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopBusinessHours;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairShopBusinessHoursMapper extends BaseMapper<OpeRepairShopBusinessHours> {
    int updateBatch(List<OpeRepairShopBusinessHours> list);

    int batchInsert(@Param("list") List<OpeRepairShopBusinessHours> list);

    int insertOrUpdate(OpeRepairShopBusinessHours record);

    int insertOrUpdateSelective(OpeRepairShopBusinessHours record);
}