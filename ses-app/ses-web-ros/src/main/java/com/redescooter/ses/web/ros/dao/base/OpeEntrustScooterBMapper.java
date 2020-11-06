package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustScooterB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeEntrustScooterBMapper extends BaseMapper<OpeEntrustScooterB> {
    int updateBatch(List<OpeEntrustScooterB> list);

    int batchInsert(@Param("list") List<OpeEntrustScooterB> list);

    int insertOrUpdate(OpeEntrustScooterB record);

    int insertOrUpdateSelective(OpeEntrustScooterB record);
}