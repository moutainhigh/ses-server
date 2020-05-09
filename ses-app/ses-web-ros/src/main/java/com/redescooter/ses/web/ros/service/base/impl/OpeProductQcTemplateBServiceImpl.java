package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductQcTemplateBMapper;
import com.redescooter.ses.web.ros.dm.OpeProductQcTemplateB;
import com.redescooter.ses.web.ros.service.base.OpeProductQcTemplateBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeProductQcTemplateBServiceImpl extends ServiceImpl<OpeProductQcTemplateBMapper, OpeProductQcTemplateB> implements OpeProductQcTemplateBService {

    @Override
    public int updateBatch(List<OpeProductQcTemplateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductQcTemplateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductQcTemplateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductQcTemplateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



