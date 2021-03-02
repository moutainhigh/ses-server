package com.redescooter.ses.mobile.rps.controller.home;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.home.RpsHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * RPS首页接口管理
 *
 * @author assert
 * @date 2021/2/3 9:39
 */
@Api(tags = {"RPS首页接口管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
public class RpsHomeController {

    @Autowired
    private RpsHomeService rpsHomeService;

    /**
     * 获取所有单据数量
     *
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map < java.lang.Integer, java.lang.Integer>>
     * @author assert
     * @date 2021/2/3
     */
    @ApiOperation(value = "获取所有单据数量")
    @PostMapping(value = "/countByAllOrder")
    public Response<Map<Integer, Integer>> getAllOrderCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(rpsHomeService.getAllOrderCount(enter));
    }

}
