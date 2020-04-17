package com.redescooter.ses.mobile.rps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQc;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBQcService;
import com.redescooter.ses.tool.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    private OpePurchasBQcService opePurchasBQcservice;

    /**
     * 来料质检 OpePurchasBQc 批次号 id为采购单子表Id
     *
     * @param enter
     * @return
     */
    @Override
    public String materialQcBatchNo(IdEnter enter) {
        QueryWrapper<OpePurchasBQc> opePurchasBQcQueryWrapper = new QueryWrapper<>();
        opePurchasBQcQueryWrapper.eq(OpePurchasBQc.COL_DR, 0);
        opePurchasBQcQueryWrapper.eq(OpePurchasBQc.COL_PURCHAS_B_ID, enter.getId());
        opePurchasBQcQueryWrapper.orderByAsc(OpePurchasBQc.COL_CREATED_TIME);
        opePurchasBQcQueryWrapper.last("limit 1");
        OpePurchasBQc purchasBQc = opePurchasBQcservice.getOne(opePurchasBQcQueryWrapper);
        //如果是同一天 批次号 后三位累加
        //规则 “PN”+“年份”+“日期”+“3位数递增”。例如：PN20200202001
        if (purchasBQc != null) {
            if (DateUtils.isSameDay(purchasBQc.getCreatedTime(), new Date())) {
                String batchNo = purchasBQc.getBatchNo();
                Integer num = Integer.valueOf(batchNo.substring(batchNo.length() - 3, batchNo.length()));
                return batchNo.substring(0, batchNo.length() - 3) + num.toString();
            }
        }
        return new StringBuilder(ProductContractEnums.MATERIALQCBATCHNO.getCode()).append(DateUtil.getDateTime(new Date(), DateConstant.YMD)).toString();
    }
}
