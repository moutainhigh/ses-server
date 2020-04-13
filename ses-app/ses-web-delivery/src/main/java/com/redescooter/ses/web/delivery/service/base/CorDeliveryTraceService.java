package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:32 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Transactional
public interface CorDeliveryTraceService extends IService<CorDeliveryTrace> {


    int batchInsert(List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);

}
