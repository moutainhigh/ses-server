package com.redescooter.ses.service.scooter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.redescooter.ses.api.scooter.vo.MobileRepairRecordEnter;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordResult;
import com.redescooter.ses.service.scooter.dao.ScooterRecordServiceMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.redescooter.ses.api.common.enums.scooter.ScooterActionResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.scooter.service.ScooterRecordService;
import com.redescooter.ses.api.scooter.vo.SaveScooterRecordEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListResult;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterActionTraceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;

/**
 * @ClassName:ScooterRecordsImpl
 * @description: ScooterRecordsImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:43
 */
@Service
public class ScooterRecordsImpl implements ScooterRecordService {

    @Autowired
    private ScoScooterActionTraceMapper scoScooterActionTraceMapper;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private ScooterRecordServiceMapper scooterRecordServiceMapper;

    /**
     * 保存车辆操作记录
     *
     * @param enter
     */
    @Transactional
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
