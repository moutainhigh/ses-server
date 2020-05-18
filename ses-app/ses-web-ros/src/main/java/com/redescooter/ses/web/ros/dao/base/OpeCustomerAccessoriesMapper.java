package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCustomerAccessoriesMapper extends BaseMapper<OpeCustomerAccessories> {
    int updateBatch(List<OpeCustomerAccessories> list);

    int batchInsert(@Param("list") List<OpeCustomerAccessories> list);

    int insertOrUpdate(OpeCustomerAccessories record);

    int insertOrUpdateSelective(OpeCustomerAccessories record);
}