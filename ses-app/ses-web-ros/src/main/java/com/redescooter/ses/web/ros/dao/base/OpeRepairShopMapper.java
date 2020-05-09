package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShop;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairShopMapper extends BaseMapper<OpeRepairShop> {
    int updateBatch(List<OpeRepairShop> list);

    int batchInsert(@Param("list") List<OpeRepairShop> list);

    int insertOrUpdate(OpeRepairShop record);

    int insertOrUpdateSelective(OpeRepairShop record);
}