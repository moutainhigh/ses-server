package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDelivery;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:32 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
public interface CorDeliveryService extends IService<CorDelivery> {


    int updateBatch(List<CorDelivery> list);

    int batchInsert(List<CorDelivery> list);

    int insertOrUpdate(CorDelivery record);

    int insertOrUpdateSelective(CorDelivery record);

}
