package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductAssemblyBMapper;
import com.redescooter.ses.web.ros.dm.OpeProductAssemblyB;
import com.redescooter.ses.web.ros.service.base.OpeProductAssemblyBService;
@Service
public class OpeProductAssemblyBServiceImpl extends ServiceImpl<OpeProductAssemblyBMapper, OpeProductAssemblyB> implements OpeProductAssemblyBService{

    @Override
    public int updateBatch(List<OpeProductAssemblyB> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeProductAssemblyB> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeProductAssemblyB record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeProductAssemblyB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
