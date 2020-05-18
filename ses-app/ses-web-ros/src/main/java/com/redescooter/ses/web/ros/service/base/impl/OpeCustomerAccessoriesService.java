package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCustomerAccessoriesService extends IService<OpeCustomerAccessories>{


    int updateBatch(List<OpeCustomerAccessories> list);

    int batchInsert(List<OpeCustomerAccessories> list);

    int insertOrUpdate(OpeCustomerAccessories record);

    int insertOrUpdateSelective(OpeCustomerAccessories record);

}
