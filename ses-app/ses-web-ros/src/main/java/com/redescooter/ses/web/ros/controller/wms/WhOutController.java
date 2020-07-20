package com.redescooter.ses.web.ros.controller.wms;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutOrderListResult;
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

import java.util.Map;


@Log4j2
@Api(tags = {"仓储库存可用"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/whOut/")
public class WhOutController {
    @Autowired
    private WhOutService whOutService;

    @PostMapping(value = "/statusByCount")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String,Integer>> stockAvailableList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.statusByCount(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "状态列表", response = Map.class)
    public Response<Map<String,String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(whOutService.statusList(enter));
    }

    @PostMapping(value = "/whOrderList")
    @ApiOperation(value = "订单列表", response = WhOutOrderListResult.class)
    public Response<PageResult<WhOutOrderListResult>> whOrderList(@ModelAttribute @ApiParam("请求参数") WhOutOrderListEnter enter) {
        return new Response<>(whOutService.whOrderList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = WhOutDetailResult.class)
    public Response<WhOutDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.detail(enter));
    }

    @PostMapping(value = "/nodeDetail")
    @ApiOperation(value = "订单节点", response = CommonNodeResult.class)
    public Response<CommonNodeResult> nodeDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(whOutService.nodeDetail(enter));
    }

    @PostMapping(value = "/detailProductList")
    @ApiOperation(value = "详情产品列表", response = WhOutDetailProductPartListResult.class)
    public Response<PageResult<WhOutDetailProductPartListResult>> detailProductPartList(@ModelAttribute @ApiParam("请求参数") WhOutDetailProductPartListEnter enter) {
        return new Response<>(whOutService.detailProductPartList(enter));
    }
}
