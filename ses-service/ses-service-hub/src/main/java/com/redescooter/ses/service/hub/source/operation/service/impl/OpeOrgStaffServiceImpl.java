package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.service.hub.source.operation.dao.OpeOrgStaffMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeOrgStaff;
import com.redescooter.ses.service.hub.source.operation.service.OpeOrgStaffService;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 23/12/2019 11:23 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("operation")
@Service
public class OpeOrgStaffServiceImpl extends ServiceImpl<OpeOrgStaffMapper, OpeOrgStaff> implements OpeOrgStaffService {

    @Override
    public int updateBatch(List<OpeOrgStaff> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOrgStaff> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOrgStaff record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOrgStaff record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
