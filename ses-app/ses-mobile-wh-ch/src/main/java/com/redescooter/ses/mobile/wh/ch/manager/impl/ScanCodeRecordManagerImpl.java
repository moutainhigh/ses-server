package com.redescooter.ses.mobile.wh.ch.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.wh.ch.constant.SequenceName;
import com.redescooter.ses.mobile.wh.ch.dao.base.OpeCodebaseRsnMapper;
import com.redescooter.ses.mobile.wh.ch.dm.OpeCodebaseRsn;
import com.redescooter.ses.mobile.wh.ch.dm.OpeScanCodeRecord;
import com.redescooter.ses.mobile.wh.ch.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.wh.ch.exception.SesMobileChWhException;
import com.redescooter.ses.mobile.wh.ch.manager.ScanCodeRecordManager;
import com.redescooter.ses.mobile.wh.ch.service.base.OpeScanCodeRecordService;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordEnter;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordReqEnter;
import com.redescooter.ses.mobile.wh.ch.vo.ScanCodeRecordResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ScanCodeRecordManagerImpl
 * @Description 部件扫码记录 业务逻辑
 * @Author Charles
 * @Date 2021/06/04 16:13
 */
@Slf4j
@Service
public class ScanCodeRecordManagerImpl implements ScanCodeRecordManager {

    @Autowired
    private OpeScanCodeRecordService recordService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * rsn 服务
     */
    @Autowired
    private OpeCodebaseRsnMapper resqMapper;

    /**
     * @Title: insertScanCodeRecode
     * @Description: //
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/7 12:02 下午
     * @Author: Charles
     */
    @Override
    public boolean insertScanCodeRecode(ScanCodeRecordEnter enter) {
        try {
            boolean checkResult = checkScanCodeRecode(enter);
            OpeScanCodeRecord record = new OpeScanCodeRecord();
            BeanUtils.copyProperties(enter, record);
            if (checkCodeRecordEnter(enter)) {
                record.setStatus(2);
            }
            if (!checkResult && null != enter.getId()) {
                record.setUpdatedBy(enter.getUserId());
                record.setUpdatedTime(new Date());
                return recordService.updateById(record);
            }
            record.setId(idAppService.getId(SequenceName.OPE_SCAN_CODE_RECORD));
            record.setCreatedBy(enter.getUserId());
            record.setCreatedTime(new Date());
            return recordService.saveOrUpdate(record);
        } catch (Exception e) {
            throw new SesMobileChWhException(ExceptionCodeEnums.INSERT_SCAN_CODE_RECORD_WRONG.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
    }

    /**
     * @Title: checkRsn
     * @Description: // 验证rsn码
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/4 5:09 下午
     * @Author: Charles
     */
    @Override
    public boolean checkRsn(ScanCodeRecordReqEnter enter) {
        LambdaQueryWrapper<OpeCodebaseRsn> opeCodes = new LambdaQueryWrapper<OpeCodebaseRsn>();
        opeCodes.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        opeCodes.eq(OpeCodebaseRsn::getRsn, enter.getSearchContent());
        int count = resqMapper.selectCount(opeCodes);
        return 0 == count ? false : true;
    }

    /**
     * @Title: scanCodeRecordList
     * @Description: //
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.web.ros.vo.app.ScanCodeRecordResult>
     * @Date: 2021/6/7 12:02 下午
     * @Author: Charles
     */
    @Override
    public PageResult<ScanCodeRecordResult> scanCodeRecordList(ScanCodeRecordReqEnter enter) {
        PageResult<ScanCodeRecordResult> pageResult = new PageResult<ScanCodeRecordResult>();
        pageResult.setPageNo(enter.getPageNo());
        pageResult.setPageSize(enter.getPageSize());
        List<ScanCodeRecordResult> list = recordService.scanCodeRecordList(enter.getSearchContent());
        if (CollectionUtils.isNotEmpty(list)) {
            pageResult.setRowTotal(list.size());
            pageResult.setList(list);
        }
        return pageResult;
    }

    /**
     * @Title: scanCodeRecordDetail
     * @Description: //
     * @Param: [enter]
     * @Return: com.redescooter.ses.web.ros.vo.app.ScanCodeRecordEnter
     * @Date: 2021/6/7 12:02 下午
     * @Author: Charles
     */
    @Override
    public ScanCodeRecordEnter scanCodeRecordDetail(ScanCodeRecordReqEnter enter) {
        LambdaQueryWrapper<OpeScanCodeRecord> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(OpeScanCodeRecord::getDr, Constant.DR_FALSE);
        lambdaQuery.eq(OpeScanCodeRecord::getId, enter.getRequestId());
        OpeScanCodeRecord record = recordService.getOne(lambdaQuery);
        if (null == record) {
            return null;
        }
        ScanCodeRecordEnter recordEnter = new ScanCodeRecordEnter();
        BeanUtils.copyProperties(record, recordEnter);
        return recordEnter;
    }

    /**
     * @Title: checkScanCodeRecode
     * @Description: //
     * @Param: [enter]
     * @Return: boolean
     * @Date: 2021/6/7 12:02 下午
     * @Author: Charles
     */
    @Override
    public boolean checkScanCodeRecode(ScanCodeRecordEnter enter) {
        LambdaQueryWrapper<OpeScanCodeRecord> lambdaQuery = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(enter.getRsn())) {
            lambdaQuery.eq(OpeScanCodeRecord::getRsn, enter.getRsn());
        }
        if (StringUtils.isNotBlank(enter.getScooterName())) {
            lambdaQuery.eq(OpeScanCodeRecord::getScooterName, enter.getScooterName());
        }
        if (StringUtils.isNotBlank(enter.getBbiSn())) {
            lambdaQuery.eq(OpeScanCodeRecord::getBbiSn, enter.getBbiSn());
        }
        if (StringUtils.isNotBlank(enter.getMotorSn())) {
            lambdaQuery.eq(OpeScanCodeRecord::getMotorSn, enter.getMotorSn());
        }
        if (StringUtils.isNotBlank(enter.getMeterSn())) {
            lambdaQuery.eq(OpeScanCodeRecord::getMeterSn, enter.getMeterSn());
        }
        if (StringUtils.isNotBlank(enter.getMeterImei())) {
            lambdaQuery.eq(OpeScanCodeRecord::getMeterImei, enter.getMeterImei());
        }
        if (StringUtils.isNotBlank(enter.getMeterBt())) {
            lambdaQuery.eq(OpeScanCodeRecord::getMeterBt, enter.getMeterBt());
        }
        int count = recordService.count(lambdaQuery);
        return 0 == count ? true : false;
    }

    private boolean checkCodeRecordEnter(ScanCodeRecordEnter enter) {
        if (StringUtils.isBlank(enter.getScooterName())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getBbiSn())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getMeterBt())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getMeterImei())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getMeterSn())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getMotorSn())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getMeterWifi())) {
            return false;
        }
        if (StringUtils.isBlank(enter.getRsn())) {
            return false;
        }
        return true;
    }
}
