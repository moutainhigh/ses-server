package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;
import com.redescooter.ses.service.hub.source.consumer.service.base.HubConUserScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:ScooterService
 * @description: ScooterMobileCService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 19:17
 */
@DS("consumer")
@DubboService
public class HubCustomerScooterServiceImpl implements CusotmerScooterService {

    @Autowired
    private HubConUserScooterService hubConUserScooterService;

    @DubboReference
    private  IdAppService idAppService;

    /**
     * 查询车辆分配信息
     *
     * @param enter 入参Id 为scooterId
     * @return
     */
    @Override
    public QueryDriverScooterResult queryDriverScooter(GeneralEnter enter) {
        QueryWrapper<HubConUserScooter> conUserScooterQueryWrapper = new QueryWrapper<>();
        if (enter.getUserId() != null && enter.getUserId() != 0) {
            conUserScooterQueryWrapper.eq(HubConUserScooter.COL_USER_ID, enter.getUserId());
        }
        conUserScooterQueryWrapper.eq(HubConUserScooter.COL_DR, 0);
        HubConUserScooter hubConUserScooter = hubConUserScooterService.getOne(conUserScooterQueryWrapper);
        QueryDriverScooterResult result=null;
        if (hubConUserScooter != null) {
            result = new QueryDriverScooterResult();
            BeanUtils.copyProperties(hubConUserScooter, result);
        }
        return result;
    }

    /**
     * 保存车辆信息
     *
     * @param enter
     * @return
     */
    @Override
    //@GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveScooter(List<HubSaveScooterEnter> enter) {
        List<HubConUserScooter> saveHubConUserScooterList = new ArrayList<>();
        enter.forEach(item -> {
            saveHubConUserScooterList.add(
                    HubConUserScooter.builder()
                            .id(idAppService.getId(SequenceName.CON_USER_SCOOTER))
                            .dr(0)
                            .userId(item.getUserId())
                            .tenantId(item.getTenantId())
                            .scooterId(item.getScooterId())
                            .beginTime(new Date())
                            .endTime(null)
                            .status(ScooterStatusEnums.AVAILABLE.getValue())
                            .mileage(0d)
                            .createdBy(item.getUserId())
                            .createdTime(new Date())
                            .updatedBy(item.getUserId())
                            .createdTime(new Date())
                            .updatedTime(new Date())
                            .build()
            );
        });

        hubConUserScooterService.saveBatch(saveHubConUserScooterList);
        return null;
    }
}
