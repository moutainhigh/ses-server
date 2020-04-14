package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBMapper extends BaseMapper<OpePurchasB> {
    int batchInsert(@Param("list") List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);
}