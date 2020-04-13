package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductionImportMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionImport;

import java.util.List;

import com.redescooter.ses.web.ros.service.base.OpeProductionImportService;

@Service
public class OpeProductionImportServiceImpl extends ServiceImpl<OpeProductionImportMapper, OpeProductionImport> implements OpeProductionImportService {

    @Override
    public int updateBatch(List<OpeProductionImport> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionImport> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionImport record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionImport record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
