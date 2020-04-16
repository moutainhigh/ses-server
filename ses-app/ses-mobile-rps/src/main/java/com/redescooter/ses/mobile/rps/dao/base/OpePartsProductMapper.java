package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePartsProduct record);

    int insertOrUpdate(OpePartsProduct record);

    int insertOrUpdateSelective(OpePartsProduct record);

    int insertSelective(OpePartsProduct record);

    OpePartsProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePartsProduct record);

    int updateByPrimaryKey(OpePartsProduct record);

    int updateBatch(List<OpePartsProduct> list);

    int updateBatchSelective(List<OpePartsProduct> list);

    int batchInsert(@Param("list") List<OpePartsProduct> list);
}