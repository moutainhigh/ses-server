package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;

import java.util.List;

public interface OpeCustomerAccessoriesService extends IService<OpeCustomerAccessories>{


    int updateBatch(List<OpeCustomerAccessories> list);

    int batchInsert(List<OpeCustomerAccessories> list);

    int insertOrUpdate(OpeCustomerAccessories record);

    int insertOrUpdateSelective(OpeCustomerAccessories record);

}
