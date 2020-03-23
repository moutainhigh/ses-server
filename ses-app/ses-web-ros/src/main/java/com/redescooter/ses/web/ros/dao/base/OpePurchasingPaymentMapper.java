package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingPayment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingPaymentMapper extends BaseMapper<OpePurchasingPayment> {
    int updateBatch(List<OpePurchasingPayment> list);

    int batchInsert(@Param("list") List<OpePurchasingPayment> list);

    int insertOrUpdate(OpePurchasingPayment record);

    int insertOrUpdateSelective(OpePurchasingPayment record);
}