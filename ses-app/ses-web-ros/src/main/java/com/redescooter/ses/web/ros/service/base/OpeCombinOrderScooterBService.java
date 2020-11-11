package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeCombinOrderScooterBService extends IService<OpeCombinOrderScooterB>{


    int updateBatch(List<OpeCombinOrderScooterB> list);

    int batchInsert(List<OpeCombinOrderScooterB> list);

    int insertOrUpdate(OpeCombinOrderScooterB record);

    int insertOrUpdateSelective(OpeCombinOrderScooterB record);

}
