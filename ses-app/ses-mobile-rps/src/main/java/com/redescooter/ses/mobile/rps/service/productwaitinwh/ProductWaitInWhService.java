package com.redescooter.ses.mobile.rps.service.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.IdEnter;
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
    <T> PageResult<T> productWaitWhItemList(ProductOrAllocateWaitInWhIdEnter enter);


    /**
     * @Author kyle
     * @Description //1、根据具体的部品id查询生产仓库待入库详情
     * @Date  2020/4/14 17:50
     * @Param [enter]
     * @return
     **/
    AllocateWaitInWhItemResult allocateWaitInWhItem(IdEnter enter);


    /**
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date  2020/4/14 17:52
     * @Param [enter]
     * @return
     **/
    ProductWaitInWhInfoResult productWaitInWhInfoIn(ProductWaitInWhIdItemEnter enter);
}
