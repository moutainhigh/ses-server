package com.redescooter.ses.mobile.rps.dao.productwaitinwh;

import com.redescooter.ses.mobile.rps.vo.productwaitinwh.ProductDetailEnter;
import com.redescooter.ses.mobile.rps.vo.productwaitinwh.ProductWaitInWhItemResult;

import java.util.List;

/**
 * @ClassNameProWaitInWHMapper
 * @Description
 * @Author kyle
 * @Date2020/4/18 10:24
 * @Version V1.0
 **/

public interface ProductWaitInWhServiceMapper {
    int proWaitInWHListCount();

    /**
     * 调拨单详情列表
     * @param enter
     * @return
     */
    List<ProductWaitInWhItemResult> allocateDetaiList(ProductDetailEnter enter);

    /**
     * 组装单详情列表
     * @param enter
     * @return
     */
    List<ProductWaitInWhItemResult> assemblyDetaiList(ProductDetailEnter enter);
}
