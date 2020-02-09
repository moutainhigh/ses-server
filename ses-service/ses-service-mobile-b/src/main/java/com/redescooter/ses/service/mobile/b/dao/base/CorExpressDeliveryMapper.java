package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressDeliveryMapper extends BaseMapper<CorExpressDelivery> {
    long countByExample(CorExpressDeliveryExample example);

    int deleteByExample(CorExpressDeliveryExample example);

    List<CorExpressDelivery> selectByExample(CorExpressDeliveryExample example);

    int updateByExampleSelective(@Param("record") CorExpressDelivery record, @Param("example") CorExpressDeliveryExample example);

    int updateByExample(@Param("record") CorExpressDelivery record, @Param("example") CorExpressDeliveryExample example);
}