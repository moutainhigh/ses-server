package com.redescooter.ses.mobile.wh.fr.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.service.WmsStockService;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.wh.fr.constant.SequenceName;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeWmsStockRecordMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsScooterStock;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockRecord;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.wh.fr.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.wh.fr.exception.SesMobileFrWhException;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/28 15:05
 */
@DubboService
@Slf4j
public class WmsStockServiceImpl implements WmsStockService {

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeWmsStockRecordMapper opeWmsStockRecordMapper;

    /**
     * 校验
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult handle(String rsn, Long userId) {
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRelationType, 1);
        qw.eq(OpeWmsStockSerialNumber::getStockType, 2);
        qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        qw.last("limit 1");
        OpeWmsStockSerialNumber serialNumber = opeWmsStockSerialNumberService.getOne(qw);
        if (null == serialNumber) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.RSN_NOT_EXIST.getCode(), ExceptionCodeEnums.RSN_NOT_EXIST.getMessage());
        }
        Long relationId = serialNumber.getRelationId();
        OpeWmsScooterStock item = opeWmsScooterStockMapper.selectById(relationId);
        if (null == item) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_EMPTY.getMessage());
        }
        Long groupId = item.getGroupId();
        Long colorId = item.getColorId();

        // 法国仓库指定车型和颜色
        LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeWmsScooterStock::getGroupId, groupId);
        wrapper.eq(OpeWmsScooterStock::getColorId, colorId);
        wrapper.eq(OpeWmsScooterStock::getStockType, 2);
        wrapper.orderByDesc(OpeWmsScooterStock::getCreatedTime);
        List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(wrapper);
        OpeWmsScooterStock stock = null;
        if (CollectionUtils.isEmpty(stockList)) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
        }
        stock = stockList.get(0);
        log.info("当前库存信息为:[{}]", stock);
        // 得到原先库存的可用库存数量和已用库存数量
        Long stockId = stock.getId();
        Integer ableStockQty = stock.getAbleStockQty();
        Integer usedStockQty = stock.getUsedStockQty();

        if (ableStockQty < 1) {
            throw new SesMobileFrWhException(ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getCode(), ExceptionCodeEnums.SCOOTER_STOCK_IS_NOT_ENOUGH.getMessage());
        }

        // 原先库存的可用库存数量-1,已用库存数量+1
        OpeWmsScooterStock param = new OpeWmsScooterStock();
        param.setId(stockId);
        param.setAbleStockQty(ableStockQty - 1);
        param.setUsedStockQty(usedStockQty + 1);
        param.setUpdatedBy(userId);
        param.setUpdatedTime(new Date());
        opeWmsScooterStockMapper.updateById(param);

        // 根据rsn修改库存产品序列号表的库存状态为不可用
        serialNumber.setStockStatus(2);
        opeWmsStockSerialNumberService.updateById(serialNumber);

        // 新增出库记录
        if (null != stock) {
            log.info(" 新增出库记录");
            OpeWmsStockRecord record = new OpeWmsStockRecord();
            record.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_RECORD));
            record.setDr(Constant.DR_FALSE);
            record.setRelationId(stock.getId());
            record.setRelationType(7);
            record.setInWhQty(1);
            record.setRecordType(2);
            record.setStockType(2);
            record.setCreatedBy(userId);
            record.setCreatedTime(new Date());
            opeWmsStockRecordMapper.insert(record);
        }
        return new GeneralResult();
    }

}
