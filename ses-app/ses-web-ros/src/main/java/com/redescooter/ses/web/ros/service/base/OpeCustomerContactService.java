package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeCustomerContactService extends IService<OpeCustomerContact>{


    int updateBatch(List<OpeCustomerContact> list);

    int batchInsert(List<OpeCustomerContact> list);

    int insertOrUpdate(OpeCustomerContact record);

    int insertOrUpdateSelective(OpeCustomerContact record);

}
