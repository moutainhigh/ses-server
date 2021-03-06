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
 * @Version：1.3
 * @create: 2020/03/31 19:13
 */
@Log4j2
@Api(tags = {"组装单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "组装单类型", response = Map.class)
    public Response<Map<String, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "组装单状态列表", response = Map.class)
    public Response<Map<String, String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.statusList(enter));
    }

    @PostMapping(value = "/paymentTypeList")
    @ApiOperation(value = "组装单支付类型", response = Map.class)
    public Response<Map<String, String>> paymentTypeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.paymentTypeList(enter));
    }

    @PostMapping(value = "/queryAssemblyProduct")
    @ApiOperation(value = "新增组装单可组装的商品列表", response = SaveAssemblyProductResult.class)
    public Response<List<SaveAssemblyProductResult>> queryAssemblyProduct(@ModelAttribute @ApiParam("请求参数") SaveAssemblyProductEnter enter) {
        return new Response<>(assemblyService.queryAssemblyProduct(enter));
    }

    @PostMapping(value = "/saveAssembly")
    @ApiOperation(value = "新增组装单", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> saveAssembly(@ModelAttribute @ApiParam("请求参数") SaveAssemblyEnter enter) {
        return new Response<>(assemblyService.saveAssembly(enter));
    }

    @PostMapping(value = "/factoryList")
    @ApiOperation(value = "工厂列表", response = FactoryCommonResult.class)
    public Response<List<FactoryCommonResult>> factoryList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.factoryList(enter));
    }

    @PostMapping(value = "/consigneeList")
    @ApiOperation(value = "收获人列表", response = ConsigneeResult.class)
    public Response<List<ConsigneeResult>> consigneeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.consigneeList(enter));
    }

    @PostMapping(value = "/ordinary/list")
    @ApiOperation(value = "普通权限组装列表", response = AssemblyResult.class)
    public Response<PageResult<AssemblyResult>> ordinaryList(@ModelAttribute @ApiParam("请求参数") AssemblyListEnter enter) {
        return new Response<>(assemblyService.ordinaryList(enter));
    }

    @PostMapping(value = "/property/list")
    @ApiOperation(value = "财务权限组装列表", response = AssemblyResult.class)
    public Response<PageResult<AssemblyResult>> propertyList(@ModelAttribute @ApiParam("请求参数") AssemblyListEnter enter) {
        return new Response<>(assemblyService.list(enter));
    }

    @PostMapping(value = "/ordinary/detail")
    @ApiOperation(value = "非财务人员组装单普通详情", response = AssemblyResult.class)
    public Response<AssemblyResult> ordinaryDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.ordinaryDetail(enter));
    }

    @PostMapping(value = "/property/detail")
    @ApiOperation(value = "财务人员组装单详情", response = AssemblyResult.class)
    public Response<AssemblyResult> propertyDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.detail(enter));
    }

    @PostMapping(value = "/ordinary/assemblyNode")
    @ApiOperation(value = "非财务人员组装单节点", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> ordinaryAssemblyNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.ordinaryAssemblyNode(enter));
    }

    @PostMapping(value = "/property/assemblyNode")
    @ApiOperation(value = "财务人员组装单节点", response = CommonNodeResult.class)
    public Response<List<CommonNodeResult>> propertyAssemblyNode(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.assemblyNode(enter));
    }

    @PostMapping(value = "/qcResultList")
    @ApiOperation(value = "质检结果列表", response = Map.class)
    public Response<Map<String, String>> qcResultList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(assemblyService.qcResultList(enter));
    }

    @PostMapping(value = "/assemblyQcInfo")
    @ApiOperation(value = "组装单质检记录", response = AssemblyQcInfoResult.class)
    public Response<List<AssemblyQcInfoResult>> assemblyQcInfo(@ModelAttribute @ApiParam("请求参数") AssemblyQcInfoEnter enter) {
        return new Response<>(assemblyService.assemblyQcInfo(enter));
    }

    @PostMapping(value = "/assemblyQcInfoItem")
    @ApiOperation(value = "组装单质检记录条目", response = productItemResult.class)
    public Response<List<AssemblyQcItemResult>> assemblyQcInfoItem(@ModelAttribute @ApiParam("请求参数") AssemblyQcInfoItemEnter enter) {
        return new Response<>(assemblyService.assemblyQcInfoItem(enter));
    }

    @PostMapping(value = "/assemblyQcItemView")
    @ApiOperation(value = "组装单质检条目质检结果信息", response = AssemblyQcItemViewResult.class)
    public Response<AssemblyQcItemViewResult> assemblyQcItemView(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.assemblyQcItemView(enter));
    }

    @PostMapping(value = "/viewItemTemplate")
    @ApiOperation(value = "质检结果模板信息", response = AssemblyQcItemViewItemTemplateResult.class)
    public Response<List<AssemblyQcItemViewItemTemplateResult>> viewItemTemplate(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.viewItemTemplate(enter));
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "组装单数据导出", response = GeneralResult.class)
    public Response<GeneralResult> export(@ApiParam("请求参数 id") Long id) {
        IdEnter enter = new IdEnter();
        enter.setId(id);
        assemblyService.export(enter);
        return new Response<>();
    }

    @PostMapping(value = "/setPaymentAssembly")
    @ApiOperation(value = "组装单设置付款信息", response = GeneralResult.class)
    public Response<GeneralResult> setPaymentAssembly(@ModelAttribute @ApiParam("请求参数") SetPaymentAssemblyEnter enter) {
        return new Response<>(assemblyService.setPaymentAssembly(enter));
    }

    @PostMapping(value = "/cancle")
    @ApiOperation(value = "取消组装单", response = GeneralResult.class)
    public Response<GeneralResult> cancle(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.cancle(enter));
    }

    @PostMapping(value = "/startPrepare")
    @ApiOperation(value = "开始组装单备料", response = GeneralResult.class)
    public Response<GeneralResult> startPrepare(@ModelAttribute @ApiParam("请求参数") StartPrepareEnter enter) {
        return new Response<>(assemblyService.startPrepare(enter));
    }
//
//    @PostMapping(value = "/startAssembly")
//    @ApiOperation(value = "开始组装单", response = GeneralResult.class)
//    public Response<GeneralResult> startAssembly(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
//        return new Response<>(assemblyService.startAssembly(enter));
//    }
//
//    @PostMapping(value = "/startQc")
//    @ApiOperation(value = "开始组装单质检", response = GeneralResult.class)
//    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
//        return new Response<>(assemblyService.startQc(enter));
//    }
//
//    @PostMapping(value = "/completeQc")
//    @ApiOperation(value = "组装单质检完成", response = GeneralResult.class)
//    public Response<GeneralResult> completeQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
//        return new Response<>(assemblyService.completeQc(enter));
//    }
//
//    @PostMapping(value = "/inWh")
//    @ApiOperation(value = "组装单入库", response = GeneralResult.class)
//    public Response<GeneralResult> inWh(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
//        return new Response<>(assemblyService.inWh(enter));
//    }

    @PostMapping(value = "/paymentDetail")
    @ApiOperation(value = "组装单支付信息", response = PaymentDetailResullt.class)
    public Response<PaymentDetailResullt> paymentDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.paymentDetail(enter));
    }

    @PostMapping(value = "/pay")
    @ApiOperation(value = "组装单支付", response = GeneralResult.class)
    public Response<GeneralResult> pay(@ModelAttribute @ApiParam("请求参数") PayEnter enter) {
        return new Response<>(assemblyService.pay(enter));
    }

    @PostMapping(value = "/ordinary/productItemList")
    @ApiOperation(value = "非财务人员组装单中商品列表", response = productItemResult.class)
    public Response<List<productItemResult>> ordinaryProductItemList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.productItemList(enter));
    }

    @PostMapping(value = "/property/productItemList")
    @ApiOperation(value = "财务人员组装单中商品列表", response = productItemResult.class)
    public Response<List<productItemResult>> propertyProductItemList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.productItemList(enter));
    }

    @PostMapping(value = "/productAssemblyTrace")
    @ApiOperation(value = "组装单组装记录", response = productItemResult.class)
    public Response<List<ProductAssemblyTraceResult>> productAssemblyTrace(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.productAssemblyTrace(enter));
    }

    @PostMapping(value = "/productAssemblyTraceItem")
    @ApiOperation(value = "组装单组装记录条目", response = productItemResult.class)
    public Response<List<ProductAssemblyTraceItemResult>> productAssemblyTraceItem(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(assemblyService.productAssemblyTraceItem(enter));
    }
}
