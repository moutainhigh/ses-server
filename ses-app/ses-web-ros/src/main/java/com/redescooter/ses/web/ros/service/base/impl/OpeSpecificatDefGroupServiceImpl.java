package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.service.base.OpeSpecificatDefGroupService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDefGroup;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatDefGroupMapper;

/**
 * @author assert
 * @date 2020/12/8 11:10
 */
@Service
public class OpeSpecificatDefGroupServiceImpl implements OpeSpecificatDefGroupService {

    @Resource
    private OpeSpecificatDefGroupMapper opeSpecificatDefGroupMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeSpecificatDefGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeSpecificatDefGroup record) {
        return opeSpecificatDefGroupMapper.insert(record);
    }

    @Override
    public int insertSelective(OpeSpecificatDefGroup record) {
        return opeSpecificatDefGroupMapper.insertSelective(record);
    }

    @Override
    public OpeSpecificatDefGroup selectByPrimaryKey(Long id) {
        return opeSpecificatDefGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeSpecificatDefGroup record) {
        return opeSpecificatDefGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeSpecificatDefGroup record) {
        return opeSpecificatDefGroupMapper.updateByPrimaryKey(record);
    }

}

