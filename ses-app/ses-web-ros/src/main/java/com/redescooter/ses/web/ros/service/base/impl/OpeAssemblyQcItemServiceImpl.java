package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyQcItemMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcItem;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyQcItemService;
@Service
public class OpeAssemblyQcItemServiceImpl extends ServiceImpl<OpeAssemblyQcItemMapper, OpeAssemblyQcItem> implements OpeAssemblyQcItemService{

    @Override
    public int updateBatch(List<OpeAssemblyQcItem> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeAssemblyQcItem> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeAssemblyQcItem record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeAssemblyQcItem record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
