package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderCombinBMapper;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderCombinBService;
@Service
public class OpeCombinOrderCombinBServiceImpl extends ServiceImpl<OpeCombinOrderCombinBMapper, OpeCombinOrderCombinB> implements OpeCombinOrderCombinBService{

    @Override
    public int updateBatch(List<OpeCombinOrderCombinB> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeCombinOrderCombinB> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeCombinOrderCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeCombinOrderCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
