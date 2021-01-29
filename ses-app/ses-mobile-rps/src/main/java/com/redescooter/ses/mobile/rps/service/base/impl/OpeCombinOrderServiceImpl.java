package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderService;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
@Service
public class OpeCombinOrderServiceImpl extends ServiceImpl<OpeCombinOrderMapper, OpeCombinOrder>
        implements OpeCombinOrderService {

    @Resource
    private OpeCombinOrderMapper opeCombinOrderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeCombinOrder record) {
        return opeCombinOrderMapper.insert(record);
    }

    @Override
    public int insertSelective(OpeCombinOrder record) {
        return opeCombinOrderMapper.insertSelective(record);
    }

    @Override
    public OpeCombinOrder selectByPrimaryKey(Long id) {
        return opeCombinOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinOrder record) {
        return opeCombinOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinOrder record) {
        return opeCombinOrderMapper.updateByPrimaryKey(record);
    }

}


