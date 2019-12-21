package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import com.redescooter.ses.service.foundation.dao.base.PlaPushResultMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaPushResultService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaPushResultServiceImpl extends ServiceImpl<PlaPushResultMapper, PlaPushResult> implements PlaPushResultService{

    @Override
    public int updateBatch(List<PlaPushResult> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaPushResult> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaPushResult record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaPushResult record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
