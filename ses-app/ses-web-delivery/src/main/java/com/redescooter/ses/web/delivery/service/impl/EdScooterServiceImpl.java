package com.redescooter.ses.web.delivery.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.tenant.TenantScooterStatusEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.web.delivery.dao.MobileServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.service.EdScooterService;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.vo.mobile.ChanageStatusEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileHistroyResult;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MobileServiceImpl
 * @description: EdScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 16:01
 */
@Service
public class EdScooterServiceImpl implements EdScooterService {

    @Autowired
    private MobileServiceMapper mobileServiceMapper;

    @Autowired
    private CorTenantScooterService corTenantScooterService;

    @Reference
    private ScooterService scooterService;

    /**
     * 状态分组
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
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
        int total = 0;
        // 已还车 数量统计
        int usedCount = mobileServiceMapper.assignMobileHistroyCount(enter);
        // 使用中的 数量统计
        int usingCount = mobileServiceMapper.usingAssignMobileHistroyCount(enter);

        total = total + usedCount + usingCount;

        if (total == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MobileHistroyResult> result = new ArrayList<>();

        List<MobileHistroyResult> usedList = mobileServiceMapper.assignMobileHistroyList(enter);

        List<MobileHistroyResult> usingList = mobileServiceMapper.usingAssignMobileHistroyList(enter);
        if (CollectionUtils.isNotEmpty(usedList)) {
            result.addAll(usedList);
        }
        if (CollectionUtils.isNotEmpty(usingList)) {
            if (CollectionUtils.isNotEmpty(result)) {
                result.addAll(0, usingList);
            } else {
                result.addAll(usingList);
            }
        }

        List<Long> scooterIds = new ArrayList<>();
        // 获取车辆数据
        result.forEach(item -> {
            scooterIds.add(item.getScooterId());
        });
        List<BaseScooterResult> baseScooterResultList = scooterService.scooterInfor(scooterIds);

        result.forEach(item -> {
            baseScooterResultList.forEach(scooter -> {
                item.setBattery(scooter.getBattery());
            });
        });


        return PageResult.create(enter, total, result);
    }

    /**
     * todo 暂无数据 暂时不做
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileHistroyResult> repairMobileHistroy(MobileHistroyEnter enter) {
        return null;
    }

    /**
     * 修改车辆状态
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult chanageScooterStatus(ChanageStatusEnter enter) {
        QueryWrapper<CorTenantScooter> corTenantScooterQueryWrapper = new QueryWrapper<>();
        corTenantScooterQueryWrapper.eq(CorTenantScooter.COL_SCOOTER_ID, enter.getId());
        CorTenantScooter corTenantScooter = corTenantScooterService.query().eq(CorTenantScooter.COL_SCOOTER_ID, enter.getId()).one();
        if (StringUtils.equals(enter.getStatus(), corTenantScooter.getStatus())) {
            return new GeneralResult(enter.getRequestId());
        }
        corTenantScooter.setStatus(enter.getStatus());
        corTenantScooter.setUpdatedBy(enter.getUserId());
        corTenantScooter.setUpdatedTime(new Date());
        corTenantScooterService.updateById(corTenantScooter);

        // 更新车辆数据
        UpdateStatusEnter updateStatusEnter = new UpdateStatusEnter();
        BeanUtils.copyProperties(enter, updateStatusEnter);
        updateStatusEnter.setId(enter.getId());
        updateStatusEnter.setAvailableStatus(enter.getStatus());
        scooterService.updateStatus(updateStatusEnter);

        return new GeneralResult(enter.getRequestId());
    }
}
