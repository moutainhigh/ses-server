package com.redescooter.ses.mobile.rps.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * @ClassName:BussinessNumberService
 * @description: BussinessNumberService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 20:37
 */
public interface BussinessNumberService {
    /**
     * 来料质检 OpePurchasBQc 批次号 id为采购单子表Id
     *
     * @param enter
     * @return
     */
    String materialQcBatchNo(IdEnter enter);

    /**
     * @Author kyle
     * @Description //批次号生成
     * @Date  2020/4/29 15:33
     * @Param
     * @return
     **/
    String getBatchNum(IdEnter enter);
}
