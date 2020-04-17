package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeWhse;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeWhseService extends IService<OpeWhse> {


    int batchInsert(List<OpeWhse> list);

    int insertOrUpdate(OpeWhse record);

    int insertOrUpdateSelective(OpeWhse record);

}



