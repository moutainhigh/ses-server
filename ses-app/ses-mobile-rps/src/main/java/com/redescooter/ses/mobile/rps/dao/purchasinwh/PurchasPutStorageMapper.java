package com.redescooter.ses.mobile.rps.dao.purchasinwh;


import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchasPutStorageMapper {
     /**
      *  采购仓库待入库信息
      *
      * @param
      * @return
      */
     List<PutStorageResult> putStorageResult();
     /**
      * 采购总行数
      *
      * @param
      * @return
      */
     int purchasListCount();
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
      * @return
      */
     int purchasDetailListCount();
     /**
      * 无ID入库信息
      *
      * @param
      * @return
      */
     NotIDPartsResult notIDPartsListResult(@Param("enter") PurchasDetailsEnter enter);

     /**
      * 无ID入库成功信息
      *
      * @param
      * @return
      */
     NotIDPartsSucceedResult notIDPartsSucceedListResultn(@Param("enter") PurchasDetailsEnter enter);

     /**
      * 有ID入库成功信息
      *
      * @param
      * @return
      */
     HaveIDPartsResult haveIDPartsResult(@Param("enter") PurchasDetailsEnter enter);
}
