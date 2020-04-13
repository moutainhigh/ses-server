package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderSparepart;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairOrderSparepartMapper extends BaseMapper<OpeRepairOrderSparepart> {
    int updateBatch(List<OpeRepairOrderSparepart> list);

    int batchInsert(@Param("list") List<OpeRepairOrderSparepart> list);

    int insertOrUpdate(OpeRepairOrderSparepart record);

    int insertOrUpdateSelective(OpeRepairOrderSparepart record);
}