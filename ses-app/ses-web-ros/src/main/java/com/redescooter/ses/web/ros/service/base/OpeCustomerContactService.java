package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCustomerContactService extends IService<OpeCustomerContact>{


    int updateBatch(List<OpeCustomerContact> list);

    int batchInsert(List<OpeCustomerContact> list);

    int insertOrUpdate(OpeCustomerContact record);

    int insertOrUpdateSelective(OpeCustomerContact record);

}
