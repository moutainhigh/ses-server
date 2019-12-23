package com.redescooter.ses.service.hub.source.corporate.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.corporate.dm.CorDriver;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 23/12/2019 11:20 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("corporate")
public interface CorDriverService extends IService<CorDriver> {


    int updateBatch(List<CorDriver> list);

    int batchInsert(List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

}
