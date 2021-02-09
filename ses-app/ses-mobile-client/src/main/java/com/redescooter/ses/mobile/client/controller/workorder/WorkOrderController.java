package com.redescooter.ses.mobile.client.controller.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderSaveOrUpdateEnter;
import com.redescooter.ses.api.foundation.service.workorder.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassNameWordOrderController
 * @Description
 * @Author Aleks
 * @Date2020/12/3 16:28
 * @Version V1.0
 **/
@Api(tags = {"工单控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/work/order")
public class WorkOrderController {

    @DubboReference
    private WorkOrderService workOrderService;

    @PostMapping(value = "/workOrderSave")
    @ApiOperation(value = "工单新增", response = GeneralResult.class)
    public Response<GeneralResult> workOrderSave(@ModelAttribute @ApiParam("请求参数") WorkOrderSaveOrUpdateEnter enter) {
        return new Response(workOrderService.workOrderSave(enter));
    }
}
