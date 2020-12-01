package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeLogisticsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeLogisticsOrderMapper extends BaseMapper<OpeLogisticsOrder> {
    int updateBatch(List<OpeLogisticsOrder> list);

    int batchInsert(@Param("list") List<OpeLogisticsOrder> list);

    int insertOrUpdate(OpeLogisticsOrder record);

    int insertOrUpdateSelective(OpeLogisticsOrder record);
}