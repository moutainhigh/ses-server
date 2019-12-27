package com.redescooter.ses.service.scooter.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;

import lombok.extern.log4j.Log4j;

/**
 * @ClassName:ScooterServiceImpl
 * @description: ScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:04
 */
@Service
@Log4j
public class ScooterServiceImpl implements ScooterService {

    @Autowired
    private ScooterServiceMapper scooterServiceMapper;

    @Override
    public List<BaseScooterResult> scooterInfor(List<Long> enter) {
        return scooterServiceMapper.scooterInfor(enter);
    }

    @Override
    public GeneralResult saveScooter(BaseScooterResult enter) {
        return null;
    }
}
