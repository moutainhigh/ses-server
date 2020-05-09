package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeWhseService extends IService<OpeWhse> {


    int batchInsert(List<OpeWhse> list);

    int insertOrUpdate(OpeWhse record);

    int insertOrUpdateSelective(OpeWhse record);

}

