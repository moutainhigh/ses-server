package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyCustomer;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellsyCustomerMapper extends BaseMapper<SellsyCustomer> {
    int updateBatch(List<SellsyCustomer> list);

    int batchInsert(@Param("list") List<SellsyCustomer> list);

    int insertOrUpdate(SellsyCustomer record);

    int insertOrUpdateSelective(SellsyCustomer record);
}