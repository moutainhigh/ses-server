package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeWhse;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeWhseService extends IService<OpeWhse> {


    int batchInsert(List<OpeWhse> list);

    int insertOrUpdate(OpeWhse record);

    int insertOrUpdateSelective(OpeWhse record);

}



