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

/**
 * @ClassName:ScooterRecordsImpl
 * @description: ScooterRecordsImpl
 * @author: Alex
 * @Version：1.3
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
     * 保存车辆操作记录
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
     * 查询车辆操作记录
     *
     * @param enter 为ScooterId
     */
    @Override
    public PageResult<ScooterRecordListResult> scooterRecordList(ScooterRecordListEnter enter) {
        return null;
    }

    /**
     * 维修记录
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
     * 校验平板升级更新记录
     */
    @Override
    public BooleanResult checkScooterUpdateRecord(ScooterUpdateRecordCheckEnter enter) {
        if (StringUtils.isBlank(enter.getTabletSn()) || StringUtils.isBlank(enter.getVersionCode()) || StringUtils.isBlank(enter.getUpdateCode())) {
            throw new ScooterException(ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getMessage());
        }

        String versionCode = enter.getVersionCode();
        String updateCode = enter.getUpdateCode();

        // 版本号为1,直接不合法
        if ("1".equals(versionCode)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // 版本号不存在,直接不合法
        LambdaQueryWrapper<ScoScooterUpdateRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScoScooterUpdateRecord::getDr, Constant.DR_FALSE);
        lqw.eq(ScoScooterUpdateRecord::getVersionCode, versionCode);
        lqw.last("limit 1");
        ScoScooterUpdateRecord record = scoScooterUpdateRecordService.getOne(lqw);
        if (null == record) {
            return new BooleanResult(Boolean.FALSE);
        }
        // db记录的updateCode和入参的updateCode不符合,直接不合法
        if (!record.getUpdateCode().equals(updateCode)) {
            return new BooleanResult(Boolean.FALSE);
        }

        // 仪表对应的updateCode使用超过了1次,直接不合法
        LambdaQueryWrapper<ScoScooterUpdateRecord> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooterUpdateRecord::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooterUpdateRecord::getUpdateCode, record.getUpdateCode());
        int count = scoScooterUpdateRecordService.count(qw);
        if (count > 1) {
            return new BooleanResult(Boolean.FALSE);
        }

        // 只有db记录的版本号,updateCode和入参传的版本号,updateCode都相等,才合法
        if (StringUtils.equals(record.getVersionCode(), versionCode) && StringUtils.equals(record.getUpdateCode(), updateCode)) {
            log.info("两者都相等,返回true");
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
