package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeOrderStatusFlowMapper extends BaseMapper<OpeOrderStatusFlow> {
    int updateBatch(List<OpeOrderStatusFlow> list);

    int batchInsert(@Param("list") List<OpeOrderStatusFlow> list);

    int insertOrUpdate(OpeOrderStatusFlow record);

    int insertOrUpdateSelective(OpeOrderStatusFlow record);
}