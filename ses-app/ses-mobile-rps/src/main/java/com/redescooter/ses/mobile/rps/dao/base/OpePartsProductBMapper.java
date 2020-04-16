package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsProductBMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePartsProductB record);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);

    int insertSelective(OpePartsProductB record);

    OpePartsProductB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePartsProductB record);

    int updateByPrimaryKey(OpePartsProductB record);

    int updateBatch(List<OpePartsProductB> list);

    int updateBatchSelective(List<OpePartsProductB> list);

    int batchInsert(@Param("list") List<OpePartsProductB> list);
}