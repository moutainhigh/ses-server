package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRefundOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeRefundOrderMapper extends BaseMapper<OpeRefundOrder> {
    int updateBatch(List<OpeRefundOrder> list);

    int batchInsert(@Param("list") List<OpeRefundOrder> list);

    int insertOrUpdate(OpeRefundOrder record);

    int insertOrUpdateSelective(OpeRefundOrder record);
}