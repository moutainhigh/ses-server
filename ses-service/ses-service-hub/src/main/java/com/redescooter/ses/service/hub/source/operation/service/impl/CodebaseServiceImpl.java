package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.hub.service.operation.CodebaseService;
import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCodebaseRsn;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCodebaseVin;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCodebaseVinService;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeSimInformationService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 20:21
 */
@DubboService
@Slf4j
public class CodebaseServiceImpl implements CodebaseService {

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeSimInformationService opeSimInformationService;

    /**
     * 校验rsn在码库是否存在
     */
    @Override
    @DS("operation")
    public boolean checkRsn(String rsn) {
        LambdaQueryWrapper<OpeCodebaseRsn> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseRsn::getStatus, 1);
        qw.eq(OpeCodebaseRsn::getRsn, rsn);
        int count = opeCodebaseRsnService.count(qw);
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改码库rsn为已用
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("operation")
    public void updateRsn(String rsn, Long userId) {
        LambdaQueryWrapper<OpeCodebaseRsn> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseRsn::getStatus, 1);
        qw.eq(OpeCodebaseRsn::getRsn, rsn);
        qw.last("limit 1");
        OpeCodebaseRsn model = opeCodebaseRsnService.getOne(qw);
        if (null != model) {
            model.setStatus(2);
            model.setUpdatedBy(userId);
            model.setUpdatedTime(new Date());
            opeCodebaseRsnService.updateById(model);
        }
    }

    /**
     * 校验vin在码库是否存在
     */
    @Override
    @DS("operation")
    public boolean checkVin(String vinCode) {
        LambdaQueryWrapper<OpeCodebaseVin> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseVin::getStatus, 1);
        qw.eq(OpeCodebaseVin::getVin, vinCode);
        int count = opeCodebaseVinService.count(qw);
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改码库vin为已用
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("operation")
    public void updateVin(String vinCode, Long userId) {
        LambdaQueryWrapper<OpeCodebaseVin> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseVin::getStatus, 1);
        qw.eq(OpeCodebaseVin::getVin, vinCode);
        qw.last("limit 1");
        OpeCodebaseVin model = opeCodebaseVinService.getOne(qw);
        if (null != model) {
            model.setStatus(2);
            model.setUpdatedBy(userId);
            model.setUpdatedTime(new Date());
            opeCodebaseVinService.updateById(model);
        }
    }

    /**
     * 保存sim卡信息
     */
    @Override
    @DS("operation")
    public void saveSim(OpeSimInformation enter) {
        opeSimInformationService.save(enter);
    }

}
