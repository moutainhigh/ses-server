package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderMapper extends BaseMapper<CorExpressOrder> {
    long countByExample(CorExpressOrderExample example);

    int deleteByExample(CorExpressOrderExample example);

    List<CorExpressOrder> selectByExample(CorExpressOrderExample example);

    int updateByExampleSelective(@Param("record") CorExpressOrder record, @Param("example") CorExpressOrderExample example);

    int updateByExample(@Param("record") CorExpressOrder record, @Param("example") CorExpressOrderExample example);
}