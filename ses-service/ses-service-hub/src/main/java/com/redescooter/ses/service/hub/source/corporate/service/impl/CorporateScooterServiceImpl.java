package com.redescooter.ses.service.hub.source.corporate.service.impl;

import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.service.corporate.CorporateScooterService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.service.hub.constant.SequenceName;
import com.redescooter.ses.service.hub.source.corporate.dm.CorTenantScooter;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorTenantScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:CorporateScooterService
 * @description: CorporateScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/26 11:55
 */
@DubboService
public class CorporateScooterServiceImpl implements CorporateScooterService {

    @Autowired
    private CorTenantScooterService corTenantScooterService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 保存整车
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult saveScooter(List<HubSaveScooterEnter> enter) {

        List<CorTenantScooter> saveConTenantScooterList = new ArrayList<>();
        enter.forEach(item -> {
            saveConTenantScooterList.add(
                    CorTenantScooter.builder()
                            .id(idAppService.getId(SequenceName.COR_TENANT_SCOOTER))
                            .dr(0)
                            .tenantId(item.getTenantId())
                            .model(item.getModel())
                            .scooterId(item.getScooterId())
                            .longitule(item.getLongitude())
                            .latitude(item.getLatitude())
                            .licensePlate(item.getLicensePlate())
                            .licensePlatePicture(item.getLicensePlatePicture())
                            .status(ScooterStatusEnums.AVAILABLE.getValue())
                            .createdBy(item.getUserId())
                            .createdTime(new Date())
                            .updatedBy(item.getUserId())
                            .createdTime(new Date())
                            .updatedTime(new Date())
                            .build()
            );
        });

        corTenantScooterService.saveBatch(saveConTenantScooterList);
        return null;
    }
}
