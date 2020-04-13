package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopSparepart;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairShopSparepartMapper extends BaseMapper<OpeRepairShopSparepart> {
    int updateBatch(List<OpeRepairShopSparepart> list);

    int batchInsert(@Param("list") List<OpeRepairShopSparepart> list);

    int insertOrUpdate(OpeRepairShopSparepart record);

    int insertOrUpdateSelective(OpeRepairShopSparepart record);
}