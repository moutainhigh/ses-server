package com.redescooter.ses.web.ros.controller.other.production;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoItemEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewItemTemplateResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.StartPrepareEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.productItemResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyController
 * @description: AssemblyController
 * @author: Alex
 * @Version???1.3
 * @create: 2020/03/31 19:13
 */
@Log4j2
@Api(tags = {"???????????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "???????????????", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "?????????????????????", response = Map.class)
    public Response<Map<String, String>> statusList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.statusList(enter));
    }

    @PostMapping(value = "/paymentTypeList")
    @ApiOperation(value = "?????????????????????", response = Map.class)
    public Response<Map<String, String>> paymentTypeList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.paymentTypeList(enter));
    }

    @PostMapping(value = "/queryAssemblyProduct")
    @ApiOperation(value = "???????????????????????????????????????", response = SaveAssemblyProductResult.class)
    public Response<List<SaveAssemblyProductResult>> queryAssemblyProduct(@ModelAttribute @ApiParam("????????????") SaveAssemblyProductEnter enter) {
        return new Response<>(assemblyService.queryAssemblyProduct(enter));
    }

    @PostMapping(value = "/saveAssembly")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> saveAssembly(@ModelAttribute @ApiParam("????????????") SaveAssemblyEnter enter) {
        return new Response<>(assemblyService.saveAssembly(enter));
    }

    @PostMapping(value = "/factoryList")
    @ApiOperation(value = "????????????", response = FactoryCommonResult.class)
    public Response<List<FactoryCommonResult>> factoryList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.factoryList(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "???????????????", response = ConsigneeResult.class)
    public Response<List<ConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.consigneeList(enter));
    }

    @PostMapping(value = "/ordinary/list")
    @ApiOperation(value = "????????????????????????", response = AssemblyResult.class)
    public Response<PageResult<AssemblyResult>> ordinaryList(@ModelAttribute @ApiParam("????????????") AssemblyListEnter enter) {
        return new Response<>(assemblyService.ordinaryList(enter));
    }

    @PostMapping(value = "/property/list")
    @ApiOperation(value = "????????????????????????", response = AssemblyResult.class)
    public Response<PageResult<AssemblyResult>> propertyList(@ModelAttribute @ApiParam("????????????") AssemblyListEnter enter) {
        return new Response<>(assemblyService.list(enter));
    }

    @PostMapping(value = "/ordinary/detail")
    @ApiOperation(value = "????????????????????????????????????", response = AssemblyResult.class)
    public Response<AssemblyResult> ordinaryDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.ordinaryDetail(enter));
    }

    @PostMapping(value = "/property/detail")
    @ApiOperation(value = "???????????????????????????", response = AssemblyResult.class)
    public Response<AssemblyResult> propertyDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.detail(enter));
    }

    @PostMapping(value = "/ordinary/assemblyNode")
    @ApiOperation(value = "??????????????????????????????", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> ordinaryAssemblyNode(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.ordinaryAssemblyNode(enter));
    }

    @PostMapping(value = "/property/assemblyNode")
    @ApiOperation(value = "???????????????????????????", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> propertyAssemblyNode(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.assemblyNode(enter));
    }

    @PostMapping(value = "/qcResultList")
    @ApiOperation(value = "??????????????????", response = Map.class)
    public Response<Map<String, String>> qcResultList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(assemblyService.qcResultList(enter));
    }

    @PostMapping(value = "/assemblyQcInfo")
    @ApiOperation(value = "?????????????????????", response = AssemblyQcInfoResult.class)
    public Response<List<AssemblyQcInfoResult>> assemblyQcInfo(@ModelAttribute @ApiParam("????????????") AssemblyQcInfoEnter enter) {
        return new Response<>(assemblyService.assemblyQcInfo(enter));
    }

    @PostMapping(value = "/assemblyQcInfoItem")
    @ApiOperation(value = "???????????????????????????", response = productItemResult.class)
    public Response<List<AssemblyQcItemResult>> assemblyQcInfoItem(@ModelAttribute @ApiParam("????????????") AssemblyQcInfoItemEnter enter) {
        return new Response<>(assemblyService.assemblyQcInfoItem(enter));
    }

    @PostMapping(value = "/assemblyQcItemView")
    @ApiOperation(value = "???????????????????????????????????????", response = AssemblyQcItemViewResult.class)
    public Response<AssemblyQcItemViewResult> assemblyQcItemView(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.assemblyQcItemView(enter));
    }

    @PostMapping(value = "/viewItemTemplate")
    @ApiOperation(value = "????????????????????????", response = AssemblyQcItemViewItemTemplateResult.class)
    public Response<List<AssemblyQcItemViewItemTemplateResult>> viewItemTemplate(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.viewItemTemplate(enter));
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> export(@ApiParam("???????????? id") Long id) {
        IdEnter enter = new IdEnter();
        enter.setId(id);
        assemblyService.export(enter);
        return new Response<>();
    }

    @PostMapping(value = "/setPaymentAssembly")
    @ApiOperation(value = "???????????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> setPaymentAssembly(@ModelAttribute @ApiParam("????????????") SetPaymentAssemblyEnter enter) {
        return new Response<>(assemblyService.setPaymentAssembly(enter));
    }

    @PostMapping(value = "/cancle")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    public Response<GeneralResult> cancle(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.cancle(enter));
    }

    @PostMapping(value = "/startPrepare")
    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> startPrepare(@ModelAttribute @ApiParam("????????????") StartPrepareEnter enter) {
        return new Response<>(assemblyService.startPrepare(enter));
    }
//
//    @PostMapping(value = "/startAssembly")
//    @ApiOperation(value = "???????????????", response = GeneralResult.class)
//    public Response<GeneralResult> startAssembly(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
//        return new Response<>(assemblyService.startAssembly(enter));
//    }
//
//    @PostMapping(value = "/startQc")
//    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
//    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
//        return new Response<>(assemblyService.startQc(enter));
//    }
//
//    @PostMapping(value = "/completeQc")
//    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
//    public Response<GeneralResult> completeQc(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
//        return new Response<>(assemblyService.completeQc(enter));
//    }
//
//    @PostMapping(value = "/inWh")
//    @ApiOperation(value = "???????????????", response = GeneralResult.class)
//    public Response<GeneralResult> inWh(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
//        return new Response<>(assemblyService.inWh(enter));
//    }

    @PostMapping(value = "/paymentDetail")
    @ApiOperation(value = "?????????????????????", response = PaymentDetailResullt.class)
    public Response<PaymentDetailResullt> paymentDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.paymentDetail(enter));
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    public Response<GeneralResult> pay(@ModelAttribute @ApiParam("????????????") PayEnter enter) {
        return new Response<>(assemblyService.pay(enter));
    }

    @PostMapping(value = "/ordinary/productItemList")
    @ApiOperation(value = "???????????????????????????????????????", response = productItemResult.class)
    public Response<List<productItemResult>> ordinaryProductItemList(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.productItemList(enter));
    }

    @PostMapping(value = "/property/productItemList")
    @ApiOperation(value = "????????????????????????????????????", response = productItemResult.class)
    public Response<List<productItemResult>> propertyProductItemList(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.productItemList(enter));
    }

    @PostMapping(value = "/productAssemblyTrace")
    @ApiOperation(value = "?????????????????????", response = productItemResult.class)
    public Response<List<ProductAssemblyTraceResult>> productAssemblyTrace(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.productAssemblyTrace(enter));
    }

    @PostMapping(value = "/productAssemblyTraceItem")
    @ApiOperation(value = "???????????????????????????", response = productItemResult.class)
    public Response<List<ProductAssemblyTraceItemResult>> productAssemblyTraceItem(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(assemblyService.productAssemblyTraceItem(enter));
    }
}
