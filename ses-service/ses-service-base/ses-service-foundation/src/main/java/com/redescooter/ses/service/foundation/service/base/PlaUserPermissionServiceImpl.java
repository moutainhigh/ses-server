package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPermissionMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaUserPermissionService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaUserPermissionServiceImpl extends ServiceImpl<PlaUserPermissionMapper, PlaUserPermission> implements PlaUserPermissionService{

    @Override
    public int updateBatch(List<PlaUserPermission> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaUserPermission> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaUserPermission record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaUserPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
