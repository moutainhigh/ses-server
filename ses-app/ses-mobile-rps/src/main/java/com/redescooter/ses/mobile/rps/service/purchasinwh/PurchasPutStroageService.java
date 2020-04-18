package com.redescooter.ses.mobile.rps.service.purchasinwh;

import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;

public interface PurchasPutStroageService {
    /**
     * 采购仓库待入库信息
     *
     * @param
     * @return
     */
    PageResult<PutStorageResult> purchasPutStroageList(PageEnter enter);

    /**
     * 采购仓库待入库详情信息
     *
     * @param
     * @return
     */
    PageResult<PurchasDetailsListResult> storageDetailsList(PurchasDetailsEnter enter);

    /**
     * 有无ID
     *
     * @param
     * @return
     */
    WhetherIdResult whetherId(PurchasDetailsEnter enter);
    /*
     */

    /**
     * 有ID入库成功信息
     *
     * @param
     * @return
     */

    HaveIdPartsResult haveIdPartsResult(PurchasDetailsEnter enter);


    /**
     * 无ID入库信息
     *
     * @param
     * @return
     */
    NotIdPartsResult notIdPartsResult(PurchasDetailsEnter enter);

    NotIdPartsSucceedResult notIdPartsSucceedResult(NotIdEnter enter);

    GeneralResult savePurchasingNode(SaveNodeEnter enter);
}

