package com.redescooter.ses.service.foundation.service.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaSysSequence;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaSysSequenceMapper;
import com.redescooter.ses.service.foundation.service.impl.base.PlaSysSequenceService;
/**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
@Service
public class PlaSysSequenceServiceImpl extends ServiceImpl<PlaSysSequenceMapper, PlaSysSequence> implements PlaSysSequenceService{

    @Override
    public int updateBatch(List<PlaSysSequence> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<PlaSysSequence> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaSysSequence record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaSysSequence record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
