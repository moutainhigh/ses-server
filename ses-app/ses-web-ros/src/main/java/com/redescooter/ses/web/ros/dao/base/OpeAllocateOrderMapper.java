package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeAllocateOrderMapper extends BaseMapper<OpeAllocateOrder> {
    int updateBatch(List<OpeAllocateOrder> list);

    int batchInsert(@Param("list") List<OpeAllocateOrder> list);

    int insertOrUpdate(OpeAllocateOrder record);

    int insertOrUpdateSelective(OpeAllocateOrder record);
}