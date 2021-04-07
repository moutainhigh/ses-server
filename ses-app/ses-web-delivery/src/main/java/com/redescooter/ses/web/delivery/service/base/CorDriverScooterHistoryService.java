package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDriverScooterHistory;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 8:56 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
public interface CorDriverScooterHistoryService extends IService<CorDriverScooterHistory> {


    int updateBatch(List<CorDriverScooterHistory> list);

    int batchInsert(List<CorDriverScooterHistory> list);

    int insertOrUpdate(CorDriverScooterHistory record);

    int insertOrUpdateSelective(CorDriverScooterHistory record);

}
