package com.redescooter.ses.web.ros.controller.other.production;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
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
 * @Version???1.3
 * @create: 2020/03/19 13:50
 */
@Log4j2
@Api(tags = {"???????????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/purchasing")
public class PurchasingController {

    @Autowired
    private PurchasingService purchasingService;

    @Autowired
    private RpsServvice rpsServvice;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "?????????????????????", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "?????????????????????", response = Map.class)
    public Response<Map<String, String>> statusList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.statusList(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "???????????????", response = PurchasingResult.class)
    public Response<PageResult<PurchasingResult>> list(@ModelAttribute @ApiParam("????????????") PurchasingListEnter enter) {
        return new Response<>(purchasingService.list(enter));
    }

    @PostMapping(value = "/paymentType")
    @ApiOperation(value = "?????????????????????", response = Map.class)
    public Response<Map<String, String>> paymentType(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.paymentType(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("????????????") SavePurchasingEnter enter) {
        return new Response<>(purchasingService.save(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "????????????????????????", response = ConsigneeResult.class)
    public Response<List<ConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.consigneeList(enter));
    }

    @PostMapping(value = "/factoryList")
    @ApiOperation(value = "?????????????????????", response = FactoryCommonResult.class)
    public Response<List<FactoryCommonResult>> factoryList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.factoryList(enter));
    }

    @PostMapping(value = "/queryPurchasProductList")
    @ApiOperation(value = "????????????????????????", response = PruchasingItemResult.class)
    public Response<List<PruchasingItemResult>> queryPurchasProductList(@ModelAttribute @ApiParam("????????????") PruchasingItemListEnter enter) {
        return new Response<>(purchasingService.queryPurchasProductList(enter));
    }

    @PostMapping(value = "/productType")
    @ApiOperation(value = "??????????????????", response = Map.class)
    public Response<Map<String, String>> productType(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.productType(enter));
    }

    @PostMapping(value = "/pruchasingDetailProductList")
    @ApiOperation(value = "???????????????????????????", response = PruchasingItemResult.class)
    public Response<List<PruchasingItemResult>> pruchasingDetailProductList(@ModelAttribute @ApiParam("????????????") PruchasingDetailProductEnter enter) {
        return new Response<>(purchasingService.pruchasingDetailProductList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "??????", response = PurchasingResult.class)
    public Response<PurchasingResult> detail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.detail(enter));
    }

    @PostMapping(value = "/purchasingNode")
    @ApiOperation(value = "???????????????", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> purchasingNode(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.purchasingNode(enter));
    }


    @GetMapping(value = "/export")
    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> export(@ApiParam("???????????? id") Long id,HttpServletResponse response) {
        return new Response<>(purchasingService.purchasingExport(id,response));
    }


    @PostMapping(value = "/paymentDetail")
    @ApiOperation(value = "????????????", response = PaymentDetailResullt.class)
    public Response<PaymentDetailResullt> paymentDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.paymentDetail(enter));
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "??????", response = GeneralResult.class)
    public Response<GeneralResult> pay(@ModelAttribute @ApiParam("????????????") PayEnter enter) {
        return new Response<>(purchasingService.pay(enter));
    }

    @PostMapping(value = "/supplierList")
    @ApiOperation(value = "???????????????", response = Map.class)
    public Response<List<FactoryCommonResult>> supplierList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(purchasingService.supplierList(enter));
    }

    @PostMapping(value = "/queryFactorySupplier")
    @ApiOperation(value = "????????????????????????????????????", response = QueryFactorySupplierResult.class)
    public Response<QueryFactorySupplierResult> queryFactorySupplier(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.queryFactorySupplier(enter));
    }

    @PostMapping(value = "/saveFactoryAnnex")
    @ApiOperation(value = "??????????????????", response = GeneralResult.class)
    public Response<GeneralResult> saveFactoryAnnex(@ModelAttribute @ApiParam("????????????") SaveFactoryAnnexEnter enter) {
        return new Response<>(purchasingService.saveFactoryAnnex(enter));
    }

    @PostMapping(value = "/qcCountByStatus")
    @ApiOperation(value = "qc??????", response = Map.class)
    public Response<Map<String, Integer>> qcCountByStatus(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.qcCountByStatus(enter));
    }

    @PostMapping(value = "/qcList")
    @ApiOperation(value = "QC ??????list", response = QcInfoResult.class)
    public Response<List<QcInfoResult>> qcList(@ModelAttribute @ApiParam("????????????") QcItemListEnter enter) {
        return new Response<>(purchasingService.qcList(enter));
    }

    @PostMapping(value = "/qcFailExport")
    @ApiOperation(value = "QC ?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> qcFailExport(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.qcFailExport(enter));
    }

    @PostMapping(value = "/cancel")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    public Response<GeneralResult> cancel(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(purchasingService.cancel(enter));
    }

}
