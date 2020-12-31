package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeCombinOrderService extends IService<OpeCombinOrder> {


    int updateBatch(List<OpeCombinOrder> list);

    int batchInsert(List<OpeCombinOrder> list);

    int insertOrUpdate(OpeCombinOrder record);

    int insertOrUpdateSelective(OpeCombinOrder record);

}


