package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionCombinBomService;

import java.util.Collection;

/**
*@author assert
*@date 2021/1/8 13:48
*/
@Service
public class OpeProductionCombinBomServiceImpl extends ServiceImpl<OpeProductionCombinBomMapper, OpeProductionCombinBom> implements OpeProductionCombinBomService{


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(OpeProductionCombinBom record) {
        return 0;
    }

    @Override
    public int insertSelective(OpeProductionCombinBom record) {
        return 0;
    }

    @Override
    public OpeProductionCombinBom selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionCombinBom record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(OpeProductionCombinBom record) {
        return 0;
    }
}
