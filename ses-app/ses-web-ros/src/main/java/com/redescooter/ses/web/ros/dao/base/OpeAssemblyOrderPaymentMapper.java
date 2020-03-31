package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyOrderPaymentMapper extends BaseMapper<OpeAssemblyOrderPayment> {
    int updateBatch(List<OpeAssemblyOrderPayment> list);

    int batchInsert(@Param("list") List<OpeAssemblyOrderPayment> list);

    int insertOrUpdate(OpeAssemblyOrderPayment record);

    int insertOrUpdateSelective(OpeAssemblyOrderPayment record);
}