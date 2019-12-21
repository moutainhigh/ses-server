package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaAppVersionMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.service.impl.base.PlaAppVersionService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaAppVersionServiceImpl extends ServiceImpl<PlaAppVersionMapper, PlaAppVersion> implements PlaAppVersionService{

    @Override
    public int updateBatch(List<PlaAppVersion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaAppVersion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaAppVersion record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaAppVersion record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
