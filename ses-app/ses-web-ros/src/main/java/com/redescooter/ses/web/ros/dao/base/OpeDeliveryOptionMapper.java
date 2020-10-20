package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeDeliveryOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeDeliveryOptionMapper extends BaseMapper<OpeDeliveryOption> {
    int updateBatch(List<OpeDeliveryOption> list);

    int updateBatchSelective(List<OpeDeliveryOption> list);

    int batchInsert(@Param("list") List<OpeDeliveryOption> list);

    int insertOrUpdate(OpeDeliveryOption record);

    int insertOrUpdateSelective(OpeDeliveryOption record);
}