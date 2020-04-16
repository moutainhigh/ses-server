package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBMapper {
    int insert(OpePurchasB record);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);

    int insertSelective(OpePurchasB record);

    int batchInsert(@Param("list") List<OpePurchasB> list);
}