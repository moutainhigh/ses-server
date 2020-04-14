package com.redescooter.ses.mobile.rps.service.impl.purchasinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;

public interface PurchasPutStroageService {
 /**
  *  采购仓库待入库信息
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
  * 有ID入库成功信息
  *
  * @param
  * @return
  */
 PageResult<HaveIDPartsResult> haveIDPartsResult(PurchasDetailsEnter enter);

 /**
  * 无ID入库信息
  *
  * @param
  * @return
  */
 PageResult<NotIDPartsResult> notIDPartsResult(PurchasDetailsEnter enter);
 /**
  * 无ID入库成功信息
  *
  * @param
  * @return
  */
 PageResult<NotIDPartsSucceedResult> otIDPartsSucceedResult(PurchasDetailsEnter enter);
}
