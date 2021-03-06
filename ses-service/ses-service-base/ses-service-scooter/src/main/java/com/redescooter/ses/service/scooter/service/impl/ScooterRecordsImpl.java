package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.scooter.ScooterActionResult;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.ScooterUpdateRecordCheckEnter;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterRecordService;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordEnter;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordResult;
import com.redescooter.ses.api.scooter.vo.SaveScooterRecordEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListResult;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterRecordServiceMapper;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterActionTraceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterUpdateRecord;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterUpdateRecordService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.map.MapUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName:ScooterRecordsImpl
 * @description: ScooterRecordsImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2019/12/27 15:43
 */
@DubboService
@Slf4j
public class ScooterRecordsImpl implements ScooterRecordService {

    @Autowired
    private ScoScooterActionTraceMapper scoScooterActionTraceMapper;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private ScooterRecordServiceMapper scooterRecordServiceMapper;

    @Autowired
    private ScoScooterUpdateRecordService scoScooterUpdateRecordService;

    /**
     * ????????????????????????
     *
     * @param enter
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void saveScooterRecords(List<SaveScooterRecordEnter<BaseScooterEnter>> enter) {
        if (CollectionUtils.isEmpty(enter)) {
            return;
        }
        List<ScoScooterActionTrace> scoScooterActionTraceList=new ArrayList<>();
        enter.forEach(item->{
            ScoScooterActionTrace scoScooterActionTrace = buildScoScooterActionTraceSingle(item);
            scoScooterActionTraceList.add(scoScooterActionTrace);
        });
        scoScooterActionTraceMapper.batchInsert(scoScooterActionTraceList);
    }

    /**
     * ????????????????????????
     *
     * @param enter ???ScooterId
     */
    @Override
    public PageResult<ScooterRecordListResult> scooterRecordList(ScooterRecordListEnter enter) {
        return null;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MobileRepairRecordResult> mobileRepairRecord(MobileRepairRecordEnter enter) {
        int count = scooterRecordServiceMapper.mobileRepairRecordCount(enter);

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MobileRepairRecordResult> result = scooterRecordServiceMapper.mobileRepairRecordList(enter);

        return null;
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public BooleanResult checkScooterUpdateRecord(ScooterUpdateRecordCheckEnter enter) {
        if (StringUtils.isBlank(enter.getTabletSn()) || StringUtils.isBlank(enter.getVersionCode()) || StringUtils.isBlank(enter.getUpdateCode())) {
            throw new ScooterException(ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getMessage());
        }

        String tabletSn = enter.getTabletSn();
        String versionCode = enter.getVersionCode();
        String updateCode = enter.getUpdateCode();

        // tabletSn????????????????????????????????????????????????,???????????????
        LambdaQueryWrapper<ScoScooterUpdateRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterUpdateRecord::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterUpdateRecord::getTabletSn, tabletSn);
        List<ScoScooterUpdateRecord> modelList = scoScooterUpdateRecordService.list(wrapper);
        if (CollectionUtils.isEmpty(modelList)) {
            return new BooleanResult(Boolean.FALSE);
        }
        Set<String> versionCodeSet = modelList.stream().map(o -> o.getVersionCode()).collect(Collectors.toSet());
        if (!versionCodeSet.contains(versionCode)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // ????????????1,???????????????
        if ("1".equals(versionCode)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // ??????????????????,???????????????
        LambdaQueryWrapper<ScoScooterUpdateRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScoScooterUpdateRecord::getDr, Constant.DR_FALSE);
        lqw.eq(ScoScooterUpdateRecord::getVersionCode, versionCode);
        lqw.last("limit 1");
        ScoScooterUpdateRecord record = scoScooterUpdateRecordService.getOne(lqw);
        if (null == record) {
            return new BooleanResult(Boolean.FALSE);
        }
        // db?????????updateCode????????????updateCode?????????,???????????????
        if (!record.getUpdateCode().equals(updateCode)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // ???????????????updateCode???????????????1???,???????????????
        LambdaQueryWrapper<ScoScooterUpdateRecord> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooterUpdateRecord::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooterUpdateRecord::getUpdateCode, record.getUpdateCode());
        qw.eq(ScoScooterUpdateRecord::getFlag, 2);
        List<ScoScooterUpdateRecord> list = scoScooterUpdateRecordService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // ??????db??????????????????,updateCode????????????????????????,updateCode?????????,?????????
        if (StringUtils.equals(record.getVersionCode(), versionCode) && StringUtils.equals(record.getUpdateCode(), updateCode)) {
            log.info("???????????????,??????true");
            // ?????????uuid?????????
            record.setFlag(2);
            scoScooterUpdateRecordService.updateById(record);
            return new BooleanResult(Boolean.TRUE);
        }
        return new BooleanResult(Boolean.FALSE);
    }

    private ScoScooterActionTrace buildScoScooterActionTraceSingle(SaveScooterRecordEnter<BaseScooterEnter> item) {
        ScoScooterActionTrace scoScooterActionTrace = new ScoScooterActionTrace();
        scoScooterActionTrace.setId(idAppService.getId(SequenceName.SCO_SCOOTER_ACTION_TRACE));
        scoScooterActionTrace.setDr(0);
        scoScooterActionTrace.setTenantId(item.getTenantId());
        scoScooterActionTrace.setUserId(item.getUserId());
        scoScooterActionTrace.setScooterId(item.getT().getId());
        scoScooterActionTrace.setUserLatitude(item.getLatitude());
        scoScooterActionTrace.setUserLongitule(item.getLongitude());
        scoScooterActionTrace.setUserLocationGeohash(MapUtil.geoHash(item.getLongitude().toString(),item.getLatitude().toString()));
        scoScooterActionTrace.setActionType(item.getActionType());
        scoScooterActionTrace.setActionResult(ScooterActionResult.SUCCESS.getValue());
        scoScooterActionTrace.setActionTime(new Date());
        scoScooterActionTrace.setBattery(item.getT().getBattery());
        scoScooterActionTrace.setLongitule(item.getT().getLongitule());
        scoScooterActionTrace.setLatitude(item.getT().getLatitude());
        scoScooterActionTrace.setGeohash(MapUtil.geoHash(item.getT().getLongitule().toString(),item.getT().getLatitude().toString()));
        scoScooterActionTrace.setCreatedBy(item.getUserId());
        scoScooterActionTrace.setCreatedTime(new Date());
        scoScooterActionTrace.setUpdatedBy(item.getUserId());
        scoScooterActionTrace.setUpdatedTime(new Date());
        return scoScooterActionTrace;
    }
}
