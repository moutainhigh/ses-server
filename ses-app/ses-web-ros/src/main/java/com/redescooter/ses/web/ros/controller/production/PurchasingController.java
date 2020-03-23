package com.redescooter.ses.web.ros.controller.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.purchasing.PurchasingService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PayEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "采购单状态统计", response = Map.class)
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

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = PurchasingResult.class)
    public Response<PurchasingResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.detail(enter));
    }

    @PostMapping(value = "/purchasingNode")
    @ApiOperation(value = "采购单节点", response = PurchasingNodeResult.class)
    public Response<List<PurchasingNodeResult>> purchasingNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.purchasingNode(enter));
    }

    @PostMapping(value = "/export")
    @ApiOperation(value = "采购单信息导出", response = GeneralResult.class)
    public Response<GeneralResult> export(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.export(enter));
    }

    @PostMapping(value = "/paymentDetail")
    @ApiOperation(value = "付款详情", response = PaymentDetailResullt.class)
    public Response<PaymentDetailResullt> paymentDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.paymentDetail(enter));
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "支付入参", response = GeneralResult.class)
    public Response<GeneralResult> pay(@ModelAttribute @ApiParam("请求参数") PayEnter enter) {
        return new Response<>(purchasingService.pay(enter));
    }

    @PostMapping(value = "/supplierList")
    @ApiOperation(value = "供应商列表", response = Map.class)
    public Response<List<FactoryCommonResult>> supplierList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.supplierList(enter));
    }

    @PostMapping(value = "/pruchasingProductList")
    @ApiOperation(value = "采购商品列表", response = PruchasingItemResult.class)
    public Response<List<PruchasingItemResult>> pruchasingProductList(@ModelAttribute @ApiParam("请求参数") PruchasingItemListEnter enter) {
        return new Response<>(purchasingService.pruchasingProductList(enter));
    }

    @PostMapping(value = "/saveFactoryAnnex")
    @ApiOperation(value = "保存工厂附件", response = GeneralResult.class)
    public Response<GeneralResult> saveFactoryAnnex(@ModelAttribute @ApiParam("请求参数") SaveFactoryAnnexEnter enter) {
        return new Response<>(purchasingService.saveFactoryAnnex(enter));
    }


    @PostMapping(value = "/startPurchasing")
    @ApiOperation(value = "开始采购", response = GeneralResult.class)
    public Response<GeneralResult> startPurchasing(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.startPurchasing(enter));
    }


    @PostMapping(value = "/startQc")
    @ApiOperation(value = "开始qc 质检", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.startQc(enter));
    }

    @PostMapping(value = "/againQc")
    @ApiOperation(value = "再次qc质检", response = GeneralResult.class)
    public Response<GeneralResult> againQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.againQc(enter));
    }

    @PostMapping(value = "/completeQc")
    @ApiOperation(value = "qc完成", response = GeneralResult.class)
    public Response<GeneralResult> completeQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.completeQc(enter));
    }

    @PostMapping(value = "/purchasingInWh")
    @ApiOperation(value = "入库", response = GeneralResult.class)
    public Response<GeneralResult> purchasingInWh(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.purchasingInWh(enter));
    }

    @PostMapping(value = "/qcCountByStatus")
    @ApiOperation(value = "qc状态", response = Map.class)
    public Response<Map<String, Integer>> qcCountByStatus(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchasingService.qcCountByStatus(enter));
    }

    @PostMapping(value = "/productType")
    @ApiOperation(value = "采购产品类型", response = Map.class)
    public Response<Map<String, Integer>> productType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchasingService.productType(enter));
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
}
