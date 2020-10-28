package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNamePurchaseServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/10/26 15:25
 * @Version V1.0
 **/
public interface PurchaseOrderServiceMapper {

    int purchaseListTotal(@Param("enter") PuraseListEnter enter);

    List<PuraseListResult> purchaseList(@Param("enter") PuraseListEnter enter);

    PurchaseDetailResult purchaseDetail(@Param("id") Long id);

    List<PurchaseScooterDetailResult> purchaseScooter(@Param("id") Long id);

    List<PurchaseCombineDetailResult> purchaseCombin(@Param("id") Long id);

    List<PurchasePartsDetailResult> purchaseParts(@Param("id") Long id);

    List<AllocateNoDataResult> allocateNoData(@Param("enter") KeywordEnter enter);

}
