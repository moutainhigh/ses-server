package com.redescooter.ses.admin.dev.service.scooter.impl;

import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterMapper;
import com.redescooter.ses.admin.dev.dao.scooter.AdminScooterPartsMapper;
import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/12/8 18:45
 */
@Slf4j
@Service
public class AdminScooterServiceImpl implements AdminScooterService {

    @Resource
    private AdminScooterMapper adminScooterMapper;
    @Resource
    private AdminScooterPartsMapper adminScooterPartsMapper;
    @Reference
    private ColorService colorService;


    @Override
    public PageResult<AdminScooterDTO> queryAdminScooter(QueryAdminScooterParamDTO paramDTO) {
        return null;
    }

    @Override
    public int insertAdminScooter(InsertAdminScooterDTO adminScooterDTO) {
        return 0;
    }

}
