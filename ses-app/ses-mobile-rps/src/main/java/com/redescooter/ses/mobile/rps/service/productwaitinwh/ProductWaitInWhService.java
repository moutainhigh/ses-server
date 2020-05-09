package com.redescooter.ses.mobile.rps.service.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.productwaitinwh.*;

/**
 * @ClassNameProWaitInWHService
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:46
 * @Version V1.0
 **/


public interface ProductWaitInWhService {
    /**
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date  2020/4/14 17:51
     * @Param [enter]
     * @return
     **/
    PageResult<AllocateAndProductResult> productWaitInWhList(PageEnter enter);

    /**
     * @Author kyle
     * @Description //1、根据组装单id查询对应的待入库商品详情列表
     * @Date  2020/4/14 17:49
     * @Param [enter]
     * @return
     **/
    PageResult<ProductWaitInWhItemResult> productWaitWhItemList(ProductDetailEnter enter);

    /**
     * 调拨单详情列表
     * @param enter
     * @return
     */
    PageResult<ProductWaitInWhItemResult> allocateDetaiList(ProductDetailEnter enter);

    /**
     * 组装单详情列表
     * @param enter
     * @return
     */
    PageResult<ProductWaitInWhItemResult> assemblyDetaiList(ProductDetailEnter enter);

    /**
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date  2020/4/14 17:52
     * @Param [enter]
     * @return
     **/
    ProductWaitInWhInfoResult setProductWaitInWhInfo(ProductWaitInWhIdItemEnter enter);
}
