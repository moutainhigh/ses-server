package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.service.hub.source.operation.dao.SpecificGroupMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/10 17:14
 */
@Service
public class SpecificServiceImpl implements SpecificService {

    @Resource
    private SpecificGroupMapper specificGroupMapper;


    @Override
    public List<SelectBaseResultDTO> getSpecificGroupList() {
        return specificGroupMapper.getSpecificGroupList();
    }

    @Override
    public SpecificGroupDTO getSpecificGroupById(Long id) {
        return specificGroupMapper.getSpecificGroupById(id);
    }

}
