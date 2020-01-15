package com.redescooter.ses.web.delivery.service.impl;


import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.web.delivery.dao.MobileServiceMapper;
import com.redescooter.ses.web.delivery.service.MobileService;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileResult;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MobileServiceImpl
 * @description: MobileServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 16:01
 */
@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    private MobileServiceMapper mobileServiceMapper;

    @Reference
    private ScooterService scooterService;

    /**
     * 状态分组
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> statusByCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = mobileServiceMapper.statusByCount(enter);
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResultList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TenantScooterStatusEnums status : TenantScooterStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileResult> list(MobileListEnter enter) {
        Integer count = mobileServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MobileResult> resultList = mobileServiceMapper.list(enter);
        List<Long> scooterIdList = new ArrayList<>();
        resultList.forEach(item -> {
            scooterIdList.add(item.getId());
        });
        List<BaseScooterResult> scooterResultList = scooterService.scooterInfor(scooterIdList);
        scooterResultList.forEach(item -> {
            resultList.forEach(result -> {
                if (item.getId().equals(result.getId())) {
                    result.setBattery(item.getBattery());
                    result.setModel(item.getModel());
                    result.setMileage(item.getTotalmileage().toString());
                }
            });
        });
        return PageResult.create(enter, count, resultList);
    }

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    @Override
    public MobileResult detail(IdEnter enter) {
        MobileResult result = mobileServiceMapper.detail(enter);
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getId());
        List<BaseScooterResult> baseScooterResults = scooterService.scooterInfor(scooterIdList);
        result.setBattery(baseScooterResults.get(0).getBattery());
        result.setMobilePicture(baseScooterResults.get(0).getPictures());
        result.setLicensePlate(baseScooterResults.get(0).getLicensePlatePicture());
        result.setModel(baseScooterResults.get(0).getModel());
        result.setMileage(baseScooterResults.get(0).getTotalmileage().toString());
        result.setNextMaintenanceKm(baseScooterResults.get(0).getNextMaintenanceKm().toString());
        return result;
    }

    /**
     * 分配记录
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileHistroyResult> assignMobileHistroy(MobileHistroyEnter enter) {
        return null;
    }
}
