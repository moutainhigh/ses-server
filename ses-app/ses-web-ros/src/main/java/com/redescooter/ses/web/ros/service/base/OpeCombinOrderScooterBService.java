package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;

import java.util.List;

public interface OpeCombinOrderScooterBService extends IService<OpeCombinOrderScooterB> {


    int updateBatch(List<OpeCombinOrderScooterB> list);

    int batchInsert(List<OpeCombinOrderScooterB> list);

    int insertOrUpdate(OpeCombinOrderScooterB record);

    int insertOrUpdateSelective(OpeCombinOrderScooterB record);

}


