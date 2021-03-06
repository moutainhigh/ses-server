package com.redescooter.ses.web.ros.service.restproductionorder.purchas;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;

import java.util.List;

/**
 * @ClassName:ProductionPurchasService
 * @description: ProductionPurchasService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:39
 */
public interface ProductionPurchasService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:52 下午
     * @Param: enter
     * @Return: ProductionPurchasListResult
     * @desc: 列表
     */
    PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 3:06 下午
     * @Param: enter
     * @Return: ProductionPurchasDetailResult
     * @desc: 详情
     */
    ProductionPurchasDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 4:18 下午
     * @Param: enter
     * @Return: List<PurchasDetailProductListResult>
     * @desc: 产品详情
     */
    List<PurchasDetailProductListResult> detailProductList(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 5:02 下午
     * @Param: enter
     * @Return: List<AssociatedOrderResult>
     * @desc: 关联订单
     */
    List<AssociatedOrderResult> associatedOrder(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 4:08 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存采购单
     */
    GeneralResult save(SaveProductionPurchasEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 10:36 上午
     * @Param: enter
     * @Return: PurchasPartListResult
     * @desc: 可采购的产品列表
     */
    PageResult<PurchasPartListResult> purchasPartList(PurchasPartListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/11 3:21 下午
     * @Param: enter
     * @Return: SupplierListResult
     * @desc: 供应商列表
     */
    List<SupplierListResult> supplierList(GeneralEnter enter);

    /**
     * @Description
     * @Author: alexx
     * @Date: 2020/11/12 2:51 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 删除
     */
    GeneralResult close(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:52 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 取消订单
     */
    GeneralResult cancel(CancelOrderEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 下单
     */
    GeneralResult delete(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/12 2:53 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 下单
     */
    GeneralResult bookOrder(IdEnter enter);


    /**
     * @return
     * @Author Aleks
     * @Description 部件入库单准备质检时，将关联的部件入库单的状态变为待入库
     * @Date 2020/11/16 10:39
     * @Param
     **/
    void statusToBeStored(Long productionPurchaseId, Long userId);


    /**
     * @return
     * @Author Aleks
     * @Description 部件入库单确认入库时，将关联的部件入库单的状态变为部分入库或已入库
     * @Date 2020/11/16 11:35
     * @Param
     **/
    void statusToPartWhOrAllInWh(Long productionPurchaseId, Long inWhId, Long userId);

    /**
     * 生成组装单的质检单(提供给rps使用)
     */
    GeneralResult generatorQcOrderByCombin(IdEnter enter);

    /**
     * 生成出库单的质检单(提供给rps使用)
     */
    GeneralResult generatorQcOrderByOutBound(IdEnter enter);

    /**
     * 确认到货
     */
    GeneralResult arrived(IdEnter enter);

}
