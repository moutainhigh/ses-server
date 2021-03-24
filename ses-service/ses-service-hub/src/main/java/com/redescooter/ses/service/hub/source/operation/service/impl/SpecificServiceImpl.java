package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.service.hub.source.operation.dao.SpecificDefGroupMapper;
import com.redescooter.ses.service.hub.source.operation.dao.SpecificDefMapper;
import com.redescooter.ses.service.hub.source.operation.dao.SpecificGroupMapper;
import com.redescooter.ses.service.hub.source.operation.dao.SpecificTypeMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/10 17:14
 */
@DubboService
@DS("operation")
public class SpecificServiceImpl implements SpecificService {

    @Resource
    private SpecificGroupMapper specificGroupMapper;

    @Resource
    private SpecificTypeMapper specificTypeMapper;

    @Resource
    private SpecificDefGroupMapper specificDefGroupMapper;

    @Resource
    private SpecificDefMapper specificDefMapper;

    @Override
    public List<SelectBaseResultDTO> getSpecificGroupList() {
        return specificGroupMapper.getSpecificGroupList();
    }

    @Override
    public SpecificGroupDTO getSpecificGroupById(Long id) {
        return specificGroupMapper.getSpecificGroupById(id);
    }

    @Override
    public List<SelectBaseResultDTO> getScooterModelList(Long groupId) {
        return specificTypeMapper.getScooterModelList(groupId);
    }

    @Override
    public SpecificTypeDTO getSpecificTypeById(Long id) {
        return specificTypeMapper.getSpecificTypeById(id);
    }

    @Override
    public List<SpecificDefGroupDTO> getSpecificDefGroupBySpecificId(Long specificId) {
        return specificDefGroupMapper.getSpecificDefGroupBySpecificId(specificId);
    }

    @Override
    public List<SpecificDefDTO> getSpecificDefBySpecificId(Long specificId) {
        return specificDefMapper.getSpecificDefBySpecificId(specificId);
    }

    @Override
    public SpecificTypeDTO getSpecificTypeByName(String name) {
        return specificTypeMapper.getSpecificTypeByName(name);
    }

}
