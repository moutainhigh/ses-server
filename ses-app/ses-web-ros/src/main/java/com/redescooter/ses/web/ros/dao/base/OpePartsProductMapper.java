package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePartsProductMapper extends BaseMapper<OpePartsProduct> {
    int updateBatch(List<OpePartsProduct> list);

    int batchInsert(@Param("list") List<OpePartsProduct> list);

    int insertOrUpdate(OpePartsProduct record);

    int insertOrUpdateSelective(OpePartsProduct record);
}