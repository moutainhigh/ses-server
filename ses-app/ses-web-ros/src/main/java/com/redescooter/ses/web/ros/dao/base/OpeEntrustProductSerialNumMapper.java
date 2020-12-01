package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustProductSerialNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeEntrustProductSerialNumMapper extends BaseMapper<OpeEntrustProductSerialNum> {
    int updateBatch(List<OpeEntrustProductSerialNum> list);

    int batchInsert(@Param("list") List<OpeEntrustProductSerialNum> list);

    int insertOrUpdate(OpeEntrustProductSerialNum record);

    int insertOrUpdateSelective(OpeEntrustProductSerialNum record);
}