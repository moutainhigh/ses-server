package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsProductBMapper extends BaseMapper<OpePartsProductB> {
    int updateBatch(List<OpePartsProductB> list);

    int batchInsert(@Param("list") List<OpePartsProductB> list);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);
}