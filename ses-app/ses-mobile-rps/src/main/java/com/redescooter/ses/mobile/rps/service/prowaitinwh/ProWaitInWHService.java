package com.redescooter.ses.mobile.rps.service.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.*;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult;

/**
 * @ClassNameProWaitInWHService
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:46
 * @Version V1.0
 **/
public interface ProWaitInWHService {
    /**
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date  2020/4/14 17:51
     * @Param [enter]
     * @return
     **/
    PageResult<ProWaitInWHLOneResult> proWaitInWHList(PageEnter enter);

    /**
     * @Author kyle
     * @Description //1、根据组装单id查询对应的待入库商品详情列表
     * @Date  2020/4/14 17:49
     * @Param [enter]
     * @return
     **/
    PageResult<ProWaitInWHItemResult> proWaitWHItemList(ProWaitInWHIdEnter enter);


    /**
     * @Author kyle
     * @Description //1、根据具体的部品id查询生产仓库待入库详情
     * @Date  2020/4/14 17:50
     * @Param [enter]
     * @return
     **/
    ProWaitInWHInfoResult proWaitInWHInfoOut(IdEnter enter);


    /**
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date  2020/4/14 17:52
     * @Param [enter]
     * @return
     **/
    ProWaitInWHInfoResult proWaitInWHInfoIn(ProWaitInWHInfoEnter enter);
}
