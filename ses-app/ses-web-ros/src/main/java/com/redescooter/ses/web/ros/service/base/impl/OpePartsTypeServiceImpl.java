package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsTypeMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import com.redescooter.ses.web.ros.service.base.OpePartsTypeService;
@Service
public class OpePartsTypeServiceImpl extends ServiceImpl<OpePartsTypeMapper, OpePartsType> implements OpePartsTypeService{

    @Override
    public int updateBatch(List<OpePartsType> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePartsType> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePartsType record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePartsType record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
