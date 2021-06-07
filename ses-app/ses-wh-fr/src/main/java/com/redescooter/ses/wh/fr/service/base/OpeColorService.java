package com.redescooter.ses.wh.fr.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.wh.fr.dm.OpeColor;

import java.util.List;

public interface OpeColorService extends IService<OpeColor> {


    int updateBatch(List<OpeColor> list);

    int batchInsert(List<OpeColor> list);

    int insertOrUpdate(OpeColor record);

    int insertOrUpdateSelective(OpeColor record);

}
