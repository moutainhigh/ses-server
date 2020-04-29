package com.redescooter.ses.mobile.rps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasLotTraceService;
import com.redescooter.ses.tool.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName:BussinessNumberServiceImpl
 * @description: BussinessNumberServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 20:40
 */
@Service
public class BussinessNumberServiceImpl implements BussinessNumberService {

    @Autowired
    private OpePurchasLotTraceService opePurchasLotTraceService;

    /**
     * 来料质检 OpePurchasBQc 批次号 id为采购单子表Id
     *
     * @param enter
     * @return
     */
    @Override
    public String materialQcBatchNo(IdEnter enter) {

        OpePurchasLotTrace opePurchasLotTrace = opePurchasLotTraceService.getOne(new LambdaQueryWrapper<OpePurchasLotTrace>().eq(OpePurchasLotTrace::getPurchasId, enter.getId()));

        //如果是同一天 批次号 后三位累加
        //规则 “PN”+“年份”+“日期”+“3位数递增”。例如：PN20200202001
        if (opePurchasLotTrace != null) {
            //同一天副用 同一个批次号
            if (DateUtils.isSameDay(opePurchasLotTrace.getCreatedTime(), new Date())) {
                return opePurchasLotTrace.getBatchNo();
            } else {
                //不是同一天批次号累加
                String batchNo = opePurchasLotTrace.getBatchNo();
                Integer num = Integer.valueOf(batchNo.substring(batchNo.length() - 3, batchNo.length())) + 1;
                return batchNo.substring(0, batchNo.length() - 3) + num.toString();
            }
        }
        //没有质检记录 返回当前新的批次号
        return new StringBuilder(ProductContractEnums.MATERIALQCBATCHNO.getCode()).append(DateUtil.getDateTime(new Date(), DateConstant.YMD)).append("001").toString();
    }

    /**
     * @return
     * @Author kyle
     * @Description //批次号生成
     * @Date 2020/4/29 15:33
     * @Param
     **/
    @Override
    public String getBatchNum() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
