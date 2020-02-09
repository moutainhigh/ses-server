package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressDeliveryDetailMapper extends BaseMapper<CorExpressDeliveryDetail> {
    long countByExample(CorExpressDeliveryDetailExample example);

    int deleteByExample(CorExpressDeliveryDetailExample example);

    List<CorExpressDeliveryDetail> selectByExample(CorExpressDeliveryDetailExample example);

    int updateByExampleSelective(@Param("record") CorExpressDeliveryDetail record, @Param("example") CorExpressDeliveryDetailExample example);

    int updateByExample(@Param("record") CorExpressDeliveryDetail record, @Param("example") CorExpressDeliveryDetailExample example);
}