package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpePartsProductBMapper extends BaseMapper<OpePartsProductB> {
    int updateBatch(List<OpePartsProductB> list);

    int batchInsert(@Param("list") List<OpePartsProductB> list);

    int insertOrUpdate(OpePartsProductB record);

    int insertOrUpdateSelective(OpePartsProductB record);
}