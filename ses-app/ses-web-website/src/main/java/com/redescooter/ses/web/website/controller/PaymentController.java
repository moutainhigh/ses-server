package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.PaymentTypeService;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 支付管理服务
 */
@Api(tags = {"Payment Management"})
@CrossOrigin
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "配送方式列表展示", response = GeneralResult.class)
    public Response<List<PaymentTypeDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(paymentTypeService.getPaymentTypeList(enter));
    }
}
