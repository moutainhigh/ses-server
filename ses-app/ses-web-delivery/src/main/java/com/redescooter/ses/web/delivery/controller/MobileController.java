package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.MobileService;
import com.redescooter.ses.web.delivery.vo.mobile.MobileListEnter;
import com.redescooter.ses.web.delivery.vo.mobile.MobileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName:MobileController
 * @description: MobileController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 09:54
 */
@Api(tags = {"Mobile模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/mobile", method = RequestMethod.POST)
public class MobileController {

    @Autowired
    private MobileService mobileService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(mobileService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "车辆列表", response = MobileResult.class)
    public Response<PageResult<MobileResult>> list(@ModelAttribute @ApiParam("请求参数") MobileListEnter enter) {
        return new Response<>(mobileService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "车辆详情", response = MobileResult.class)
    public Response<MobileResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(mobileService.detail(enter));
    }

}
