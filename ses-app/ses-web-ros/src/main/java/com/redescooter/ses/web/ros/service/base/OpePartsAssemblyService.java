package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsAssembly;

@Transactional
public interface OpePartsAssemblyService extends IService<OpePartsAssembly> {


    int updateBatch(List<OpePartsAssembly> list);

    int batchInsert(List<OpePartsAssembly> list);

    int insertOrUpdate(OpePartsAssembly record);

    int insertOrUpdateSelective(OpePartsAssembly record);

}

