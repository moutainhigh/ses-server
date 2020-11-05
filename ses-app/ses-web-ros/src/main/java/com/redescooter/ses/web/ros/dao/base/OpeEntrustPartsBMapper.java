package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustPartsB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeEntrustPartsBMapper extends BaseMapper<OpeEntrustPartsB> {
    int updateBatch(List<OpeEntrustPartsB> list);

    int batchInsert(@Param("list") List<OpeEntrustPartsB> list);

    int insertOrUpdate(OpeEntrustPartsB record);

    int insertOrUpdateSelective(OpeEntrustPartsB record);
}