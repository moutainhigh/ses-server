package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorDriver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 8:31 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Transactional
public interface CorDriverService extends IService<CorDriver> {


    int updateBatch(List<CorDriver> list);

    int batchInsert(List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

}
