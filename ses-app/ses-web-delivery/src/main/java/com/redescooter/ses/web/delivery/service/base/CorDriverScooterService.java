package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:22 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Transactional
public interface CorDriverScooterService extends IService<CorDriverScooter> {


    int updateBatch(List<CorDriverScooter> list);

    int batchInsert(List<CorDriverScooter> list);

    int insertOrUpdate(CorDriverScooter record);

    int insertOrUpdateSelective(CorDriverScooter record);

}
