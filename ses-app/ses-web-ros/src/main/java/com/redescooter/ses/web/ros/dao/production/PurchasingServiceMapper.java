package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:PurchasingServiceMapper
 * @description: PurchasingServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/20 14:32
 */
public interface PurchasingServiceMapper {
    /**
     * 采购商品列表
     *
     * @param enter
     * @return
     */
    List<PruchasingItemResult> pruchasingProductList(@Param("enter") PruchasingItemListEnter enter, @Param("productTypeList") List<String> productTypeList);
}
