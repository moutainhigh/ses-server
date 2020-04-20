package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasPayment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasPaymentMapper extends BaseMapper<OpePurchasPayment> {
    int updateBatch(List<OpePurchasPayment> list);

    int batchInsert(@Param("list") List<OpePurchasPayment> list);

    int insertOrUpdate(OpePurchasPayment record);

    int insertOrUpdateSelective(OpePurchasPayment record);
}