package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcItem;

import java.util.List;

public interface OpeAssemblyQcItemService extends IService<OpeAssemblyQcItem> {


    int updateBatch(List<OpeAssemblyQcItem> list);

    int batchInsert(List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);

}

