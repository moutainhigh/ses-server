package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCustomer;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 8:50 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
public interface OpeCustomerService extends IService<OpeCustomer> {


    int updateBatch(List<OpeCustomer> list);

    int batchInsert(List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);

}






