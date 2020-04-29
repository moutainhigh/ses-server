package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplateB;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyQcItemService extends IService<OpeAssemblyQcItem> {


    int updateBatch(List<OpeAssemblyQcItem> list);

    int batchInsert(List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);

    int updateBatchSelective(List<OpeAssemblyQcItem> list);

}

