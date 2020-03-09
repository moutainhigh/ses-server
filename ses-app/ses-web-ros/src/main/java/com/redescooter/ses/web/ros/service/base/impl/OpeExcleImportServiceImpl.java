package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeExcleImportMapper;
import com.redescooter.ses.web.ros.dm.OpeExcleImport;
import com.redescooter.ses.web.ros.service.base.OpeExcleImportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeExcleImportServiceImpl extends ServiceImpl<OpeExcleImportMapper, OpeExcleImport> implements OpeExcleImportService {

    @Override
    public int updateBatch(List<OpeExcleImport> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeExcleImport> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeExcleImport record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeExcleImport record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertOrUpdateWithBLOBs(OpeExcleImport record) {
        return baseMapper.insertOrUpdateWithBLOBs(record);
    }
}

