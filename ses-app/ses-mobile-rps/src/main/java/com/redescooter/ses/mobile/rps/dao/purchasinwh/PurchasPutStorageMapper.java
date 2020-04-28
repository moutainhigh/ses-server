package com.redescooter.ses.mobile.rps.dao.purchasinwh;


import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchasPutStorageMapper {
     /**
      *  采购仓库待入库信息
      *
      * @param
      * @param enter
      * @return
      */
     List<PutStorageResult> putStorageResult(PageEnter enter);
     /**
      * 采购总行数
      *
      * @param
      * @param enter
      * @return
      */
     int purchasListCount(PageEnter enter);
     /**
      * 采购仓库待入库详情信息
      *
      * @param
      * @return
      */
     List<PurchasDetailsListResult> storageDetailsResult(@Param("enter") PurchasDetailsEnter enter);

     /**
      * 采购部件总行数
      *
      * @param
      * @param enter
      * @return
      */
     int purchasDetailListCount(PurchasDetailsEnter enter);
     /**
      * 无ID入库信息
      *
      * @param
      * @return
      */
     NotIdPartsResult notIdpartslistresult(@Param("enter") NotIdDetailsEnter enter);

     /**
      * 无ID入库成功信息
      *
      * @param
      * @return
      */
     NotIdPartsSucceedResult notIdPartsSucceedListResult(@Param("enter") NotIdEnter enter);

     /**
      * 有ID入库成功信息
      *
      * @param
      * @return
      */
     HaveIdPartsResult haveIdPartsResult(@Param("enter") HaveIdEnter enter);
}
