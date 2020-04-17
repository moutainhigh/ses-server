package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasPayment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasPaymentMapper extends BaseMapper<OpePurchasPayment> {
    int updateBatch(List<OpePurchasPayment> list);

    int batchInsert(@Param("list") List<OpePurchasPayment> list);

    int insertOrUpdate(OpePurchasPayment record);

    int insertOrUpdateSelective(OpePurchasPayment record);
}