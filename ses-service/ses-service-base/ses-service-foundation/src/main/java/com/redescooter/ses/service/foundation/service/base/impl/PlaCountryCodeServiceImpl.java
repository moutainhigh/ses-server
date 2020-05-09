package com.redescooter.ses.service.foundation.service.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dao.base.PlaCountryCodeMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaCountryCode;
import com.redescooter.ses.service.foundation.service.base.PlaCountryCodeService;

/**
 * @author Mr.lijiating
 * @Date: 27/12/2019 4:05 下午
 * @ClassName: PlaCountryCodeServiceImpl
 * @Function: TODO
 * @version V1.0
 */
@Service
public class PlaCountryCodeServiceImpl extends ServiceImpl<PlaCountryCodeMapper, PlaCountryCode>
    implements PlaCountryCodeService {

    @Override
    public int updateBatch(List<PlaCountryCode> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaCountryCode> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaCountryCode record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaCountryCode record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
