package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantNodeMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaTenantNodeService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaTenantNodeServiceImpl extends ServiceImpl<PlaTenantNodeMapper, PlaTenantNode> implements PlaTenantNodeService{

    @Override
    public int updateBatch(List<PlaTenantNode> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaTenantNode> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaTenantNode record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaTenantNode record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
