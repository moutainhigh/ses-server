package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPurchasePartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPurchasePartsBMapper extends BaseMapper<OpeProductionPurchasePartsB> {
    int updateBatch(List<OpeProductionPurchasePartsB> list);

    int batchInsert(@Param("list") List<OpeProductionPurchasePartsB> list);

    int insertOrUpdate(OpeProductionPurchasePartsB record);

    int insertOrUpdateSelective(OpeProductionPurchasePartsB record);
}