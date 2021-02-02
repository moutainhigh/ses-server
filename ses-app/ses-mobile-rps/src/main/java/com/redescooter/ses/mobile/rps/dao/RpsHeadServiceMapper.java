package com.redescooter.ses.mobile.rps.dao;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

/**
 * @ClassName:RpsHeadServiceMapper
 * @description: RpsHeadServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 16:35
 */
public interface RpsHeadServiceMapper {
    /**
     * 来料质检
     *
     * @param enter
     * @return
     */
    int rpsHeadMaterialsQc(GeneralEnter enter);

    /**
     * 采购入库
     *
     * @param enter
     * @return
     */
    int rpsHeadPurchasInWh(GeneralEnter enter);

//    /**
//     * 调拨备料
//     *
//     * @param enter
//     * @return
//     */
//    int rpsHeadPrepare(GeneralEnter enter);

    /**
     * 组装
     *
     * @param enter
     * @return
     */
    int rpsHeadAssembly(GeneralEnter enter);

    /**
     * 产品质检
     *
     * @param enter
     * @return
     */
    int rpsHeadProductQc(GeneralEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 11:50 上午
    * @Param:  enter
    * @Return: int
    * @desc: 出库单数量
    */
    int rpsHeadOutboundTotal(GeneralEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 11:51 上午
    * @Param:  enter
    * @Return: int
    * @desc: 委托单
    */
    int rpsHeadConsignTotal(GeneralEnter enter);


//    /**
//     * 生产入库
//     *
//     * @param enter
//     * @return
//     */
//    int rpsHeadProductionInWh(GeneralEnter enter);

//    /**
//     * 调拨备料
//     *
//     * @param enter
//     */
//    int rpsHeadPrepareAllocate(GeneralEnter enter);
}
