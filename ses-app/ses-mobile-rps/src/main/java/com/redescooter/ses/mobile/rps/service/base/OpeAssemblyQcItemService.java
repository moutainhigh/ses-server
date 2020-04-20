package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyQcItemService extends IService<OpeAssemblyQcItem>{


    int updateBatch(List<OpeAssemblyQcItem> list);

    int batchInsert(List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);

}
