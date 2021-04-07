package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchasePartsB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeProductionPurchasePartsBMapper extends BaseMapper<OpeProductionPurchasePartsB> {
    int updateBatch(List<OpeProductionPurchasePartsB> list);

    int batchInsert(@Param("list") List<OpeProductionPurchasePartsB> list);

    int insertOrUpdate(OpeProductionPurchasePartsB record);

    int insertOrUpdateSelective(OpeProductionPurchasePartsB record);
}
