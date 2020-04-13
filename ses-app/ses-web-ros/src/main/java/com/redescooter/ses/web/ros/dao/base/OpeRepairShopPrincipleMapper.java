package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopPrinciple;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairShopPrincipleMapper extends BaseMapper<OpeRepairShopPrinciple> {
    int updateBatch(List<OpeRepairShopPrinciple> list);

    int batchInsert(@Param("list") List<OpeRepairShopPrinciple> list);

    int insertOrUpdate(OpeRepairShopPrinciple record);

    int insertOrUpdateSelective(OpeRepairShopPrinciple record);
}