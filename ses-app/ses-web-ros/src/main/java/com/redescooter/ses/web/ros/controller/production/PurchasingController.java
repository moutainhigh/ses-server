package com.redescooter.ses.web.ros.controller.production;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.RpsServvice;
import com.redescooter.ses.web.ros.service.production.purchasing.PurchasingService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingDetailProductEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.QueryFactorySupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:Purchasing
 * @description: Purchasing
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 13:50
 */
@Log4j2
@Api(tags = {"采购单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/purchasing")
public class PurchasingController {

    @Autowired
    private PurchasingService purchasingService;

    @Autowired
    private RpsServvice rpsServvice;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "采购单类型统计", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "采购单状态集合", response = Map.class)
    public Response<Map<String, String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.statusList(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "采购单列表", response = PurchasingResult.class)
    public Response<PageResult<PurchasingResult>> list(@ModelAttribute @ApiParam("请求参数") PurchasingListEnter enter) {
        return new Response<>(purchasingService.list(enter));
    }

    @PostMapping(value = "/paymentType")
    @ApiOperation(value = "采购单付款方式", response = Map.class)
    public Response<Map<String, String>> paymentType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.paymentType(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存采购单", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SavePurchasingEnter enter) {
        return new Response<>(purchasingService.save(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "采购单收件人列表", response = ConsigneeResult.class)
    public Response<List<ConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.consigneeList(enter));
    }

    @PostMapping(value = "/factoryList")
    @ApiOperation(value = "采购单工厂列表", response = FactoryCommonResult.class)
    public Response<List<FactoryCommonResult>> factoryList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.factoryList(enter));
    }

    @PostMapping(value = "/queryPurchasProductList")
    @ApiOperation(value = "可采购的商品列表", response = PruchasingItemResult.class)
    public Response<List<PruchasingItemResult>> queryPurchasProductList(@ModelAttribute @ApiParam("请求参数") PruchasingItemListEnter enter) {
        return new Response<>(purchasingService.queryPurchasProductList(enter));
    }

    @PostMapping(value = "/productType")
    @ApiOperation(value = "采购产品类型", response = Map.class)
    public Response<Map<String, String>> productType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.productType(enter));
    }

    @PostMapping(value = "/pruchasingDetailProductList")
    @ApiOperation(value = "采购单详情商品列表", response = PruchasingItemResult.class)
    public Response<List<PruchasingItemResult>> pruchasingDetailProductList(@ModelAttribute @ApiParam("请求参数") PruchasingDetailProductEnter enter) {
        return new Response<>(purchasingService.pruchasingDetailProductList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = PurchasingResult.class)
    public Response<PurchasingResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.detail(enter));
    }

    @PostMapping(value = "/purchasingNode")
    @ApiOperation(value = "采购单节点", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> purchasingNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.purchasingNode(enter));
    }


    @GetMapping(value = "/export")
    @ApiOperation(value = "采购单信息导出", response = GeneralResult.class)
    public Response<GeneralResult> export(@ApiParam("请求参数 id") Long id,HttpServletResponse response) {
        return new Response<>(purchasingService.purchasingExport(id,response));
    }


    @PostMapping(value = "/paymentDetail")
    @ApiOperation(value = "付款详情", response = PaymentDetailResullt.class)
    public Response<PaymentDetailResullt> paymentDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.paymentDetail(enter));
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "支付", response = GeneralResult.class)
    public Response<GeneralResult> pay(@ModelAttribute @ApiParam("请求参数") PayEnter enter) {
        return new Response<>(purchasingService.pay(enter));
    }

    @PostMapping(value = "/supplierList")
    @ApiOperation(value = "供应商列表", response = Map.class)
    public Response<List<FactoryCommonResult>> supplierList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.supplierList(enter));
    }

    @PostMapping(value = "/queryFactorySupplier")
    @ApiOperation(value = "查询采购单代工厂及供应商", response = QueryFactorySupplierResult.class)
    public Response<QueryFactorySupplierResult> queryFactorySupplier(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.queryFactorySupplier(enter));
    }

    @PostMapping(value = "/saveFactoryAnnex")
    @ApiOperation(value = "保存工厂附件", response = GeneralResult.class)
    public Response<GeneralResult> saveFactoryAnnex(@ModelAttribute @ApiParam("请求参数") SaveFactoryAnnexEnter enter) {
        return new Response<>(purchasingService.saveFactoryAnnex(enter));
    }

    @PostMapping(value = "/qcCountByStatus")
    @ApiOperation(value = "qc状态", response = Map.class)
    public Response<Map<String, Integer>> qcCountByStatus(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.qcCountByStatus(enter));
    }

    @PostMapping(value = "/qcList")
    @ApiOperation(value = "QC 条目list", response = QcInfoResult.class)
    public Response<List<QcInfoResult>> qcList(@ModelAttribute @ApiParam("请求参数") QcItemListEnter enter) {
        return new Response<>(purchasingService.qcList(enter));
    }

    @PostMapping(value = "/qcFailExport")
    @ApiOperation(value = "QC 未通过数据导出", response = GeneralResult.class)
    public Response<GeneralResult> qcFailExport(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.qcFailExport(enter));
    }

    @PostMapping(value = "/cancel")
    @ApiOperation(value = "取消采购单", response = GeneralResult.class)
    public Response<GeneralResult> cancel(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.cancel(enter));
    }


}
