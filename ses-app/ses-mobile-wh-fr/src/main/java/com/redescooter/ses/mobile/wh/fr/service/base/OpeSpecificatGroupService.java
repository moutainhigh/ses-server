package com.redescooter.ses.mobile.wh.fr.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.wh.fr.dm.OpeSpecificatGroup;

import java.util.List;

public interface OpeSpecificatGroupService extends IService<OpeSpecificatGroup> {


    int updateBatch(List<OpeSpecificatGroup> list);

    int updateBatchSelective(List<OpeSpecificatGroup> list);

    int batchInsert(List<OpeSpecificatGroup> list);

    int insertOrUpdate(OpeSpecificatGroup record);

    int insertOrUpdateSelective(OpeSpecificatGroup record);

}
