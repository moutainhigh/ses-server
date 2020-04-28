package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyQcItemService extends IService<OpeAssemblyQcItem> {


    int updateBatch(List<OpeAssemblyQcItem> list);

    int batchInsert(List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);

}

