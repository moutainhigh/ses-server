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
import com.redescooter.ses.web.delivery.dao.DriverServiceMapper;
import com.redescooter.ses.web.delivery.dao.EdScooterServiceMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import com.redescooter.ses.web.delivery.service.EdScooterService;
import com.redescooter.ses.web.delivery.service.base.CorTenantScooterService;
import com.redescooter.ses.web.delivery.vo.edscooter.ChanageStatusEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterGreenDataResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterListEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterResult;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName:MobileServiceImpl
 * @description: EdScooterServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/01/14 16:01
 */
@Service
public class EdScooterServiceImpl implements EdScooterService {

    @Autowired
    private EdScooterServiceMapper edScooterServiceMapper;

    @Autowired
    private CorTenantScooterService corTenantScooterService;

    @Autowired
    private DriverServiceMapper driverServiceMapper;

    @DubboReference
    private ScooterService scooterService;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = edScooterServiceMapper.countByStatus(enter);
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResultList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TenantScooterStatusEnums status : TenantScooterStatusEnums.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        //??????????????????????????????
        map.remove(TenantScooterStatusEnums.CHARGING.getValue());
        return map;
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<EdScooterResult> list(EdScooterListEnter enter) {
        Integer count = edScooterServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<EdScooterResult> resultList = edScooterServiceMapper.list(enter);

        List<BaseScooterResult> scooterResultList = scooterService.scooterInfor(resultList.stream().map(EdScooterResult::getId).collect(Collectors.toList()));
        scooterResultList.forEach(item -> {

            Optional.ofNullable(item).ifPresent(it -> {
                resultList.forEach(result -> {
                    if (item.getId().equals(result.getId())) {
                        result.setBattery(item.getBattery());
                        result.setModel(item.getModel());
                        result.setMileage(item.getTotalmileage().toString());
                    }
                });
            });
        });
        return PageResult.create(enter, count, resultList);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DriverListResult> offDrivers(GeneralEnter enter) {
        return driverServiceMapper.offDrivers(enter);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public EdScooterResult detail(IdEnter enter) {
        EdScooterResult result = edScooterServiceMapper.detail(enter);
        if (result == null) {
            return result;
        }
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getId());

        List<EdScooterResult> results = edScooterServiceMapper.driverUserProfile(scooterIdList);
        if (CollectionUtils.isNotEmpty(results)) {
            results.forEach(scooterResult -> {
                if (scooterResult.getId() != null && scooterResult.getId().equals(scooterResult.getId())) {
                    result.setDriverId(scooterResult.getDriverId());
                    result.setDriverFirstName(scooterResult.getDriverFirstName());
                    result.setDriverLastName(scooterResult.getDriverLastName());
                }
            });
        }
        List<BaseScooterResult> baseScooterResults = scooterService.scooterInfor(scooterIdList);
        if (baseScooterResults.size() > 0) {
            result.setBattery(baseScooterResults.get(0).getBattery());
            result.setMobilePicture(baseScooterResults.get(0).getPictures());
        }
        return result;
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public EdScooterGreenDataResult scooterGreenData(IdEnter enter) {

        EdScooterGreenDataResult result = edScooterServiceMapper.scooterGreenData(enter);
        if (result == null) {
            return new EdScooterGreenDataResult();
        }
        List<Long> scooterIdList = new ArrayList<>();
        scooterIdList.add(result.getId());
        List<BaseScooterResult> baseScooterResults = scooterService.scooterInfor(scooterIdList);
        if (baseScooterResults.size() > 0) {
            result.setBattery(baseScooterResults.get(0).getBattery());
            result.setMileage(baseScooterResults.get(0).getTotalmileage().toString());
            result.setNextMaintenanceKm(baseScooterResults.get(0).getNextMaintenanceKm().toString());
        }

        return result;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<EdScooterHistroyResult> assignMobileHistroy(EdScooterHistroyEnter enter) {
        int total = 0;
        // ????????? ????????????
        int usedCount = edScooterServiceMapper.assignEdScooterHistroyCount(enter);
        // ???????????? ????????????
        int usingCount = edScooterServiceMapper.usingAssignEdScooterHistroyCount(enter);

        total = total + usedCount + usingCount;

        if (total == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<EdScooterHistroyResult> result = new ArrayList<>();

        List<EdScooterHistroyResult> usedList = edScooterServiceMapper.assignEdScooterHistroyList(enter);

        List<EdScooterHistroyResult> usingList = edScooterServiceMapper.usingAssignEdScooterHistroyList(enter);
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
        // ??????????????????
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
     * todo ???????????? ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<EdScooterHistroyResult> repairMobileHistroy(EdScooterHistroyEnter enter) {
        return null;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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

        // ??????????????????
        UpdateStatusEnter updateStatusEnter = new UpdateStatusEnter();
        BeanUtils.copyProperties(enter, updateStatusEnter);
        updateStatusEnter.setId(enter.getId());
        updateStatusEnter.setAvailableStatus(enter.getStatus());
        scooterService.updateStatus(updateStatusEnter);

        return new GeneralResult(enter.getRequestId());
    }
}
