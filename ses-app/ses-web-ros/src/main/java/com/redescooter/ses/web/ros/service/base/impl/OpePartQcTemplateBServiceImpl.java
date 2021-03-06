package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpePartQcTemplateBMapper;
import com.redescooter.ses.web.ros.dm.OpePartQcTemplateB;
import com.redescooter.ses.web.ros.service.base.OpePartQcTemplateBService;

@Service
public class OpePartQcTemplateBServiceImpl extends ServiceImpl<OpePartQcTemplateBMapper, OpePartQcTemplateB> implements OpePartQcTemplateBService {

    @Override
    public int updateBatch(List<OpePartQcTemplateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartQcTemplateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartQcTemplateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartQcTemplateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}





