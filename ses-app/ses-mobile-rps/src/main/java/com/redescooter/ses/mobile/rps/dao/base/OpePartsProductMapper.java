package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpePartsProductMapper extends BaseMapper<OpePartsProduct> {
    int updateBatch(List<OpePartsProduct> list);

    int updateBatchSelective(List<OpePartsProduct> list);

    int batchInsert(@Param("list") List<OpePartsProduct> list);

    int insertOrUpdate(OpePartsProduct record);

    int insertOrUpdateSelective(OpePartsProduct record);
}