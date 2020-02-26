package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsAssemblyB;

@Transactional
public interface OpePartsAssemblyBService extends IService<OpePartsAssemblyB> {


    int updateBatch(List<OpePartsAssemblyB> list);

    int batchInsert(List<OpePartsAssemblyB> list);

    int insertOrUpdate(OpePartsAssemblyB record);

    int insertOrUpdateSelective(OpePartsAssemblyB record);

}

