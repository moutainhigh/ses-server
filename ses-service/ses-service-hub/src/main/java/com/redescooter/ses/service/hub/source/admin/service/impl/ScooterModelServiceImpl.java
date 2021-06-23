package com.redescooter.ses.service.hub.source.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooterUpdateEnter;
import com.redescooter.ses.service.hub.source.admin.dao.base.AdmScooterMapper;
import com.redescooter.ses.service.hub.source.admin.service.base.AdmScooterService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/13 14:32
 */
@DubboService
public class ScooterModelServiceImpl implements ScooterModelService {

    @Autowired
    private AdmScooterService admScooterService;

    @Autowired
    private AdmScooterMapper admScooterMapper;

    /**
     * 根据平板序列号查询车辆信息
     */
    @Override
    @DS("admin")
    public AdmScooter getScooterBySn(String rsn) {
        LambdaQueryWrapper<AdmScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(AdmScooter::getDr, Constant.DR_FALSE);
        qw.eq(AdmScooter::getSn, rsn);
        qw.last("limit 1");
        AdmScooter admScooter = admScooterService.getOne(qw);
        return admScooter;
    }

    /**
     * 新增车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    @DS("admin")
    public void insertScooter(AdmScooter scooter) {
        admScooterService.save(scooter);
    }

    /**
     * 根据id查询详情
     */
    @Override
    @DS("admin")
    public AdmScooter getScooterById(Long id) {
        return admScooterService.getById(id);
    }

    /**
     * 修改adm_scooter
     */
    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateAdmScooter(AdmScooterUpdateEnter enter) {
        AdmScooter scooter = new AdmScooter();
        scooter.setId(enter.getId());
        scooter.setScooterController(enter.getScooterController());
        scooter.setGroupId(enter.getGroupId());
        scooter.setGroupName(enter.getGroupName());
        scooter.setUpdatedBy(0L);
        scooter.setUpdatedTime(new Date());
        admScooterService.updateById(scooter);
    }

    /**
     * 删除oms的车辆
     */
    @Override
    @DS("admin")
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteOmsScooter(String tabletSn) {
        admScooterMapper.deleteScooter(tabletSn);
    }

}
