package com.redescooter.ses.web.ros.controller.warehouse.fr;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrInWhOrderService;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 法国仓库入库单控制器
 * 此控制器存在的目的是:上第一个版本要上分车,但是不上rps,所以新增此控制器用于将整车入库到法国仓库内以便能进行分车,待rps上线后,此控制器可废弃
 * @Author Chris
 * @Date 2021/4/15 10:03
 */
@Api(tags = {"法国入库单控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/warehouse/fr/in")
public class FrInWhOrderController {

    @Autowired
    private FrInWhOrderService frInWhOrderService;

    /**
     * 新增法国仓库入库单
     */
    @PostMapping("/add")
    @AvoidDuplicateSubmit
    @ApiOperation(value = "新增法国仓库入库单", response = GeneralResult.class)
    public Response<GeneralResult> addFrInWhOrder(@ModelAttribute FrInWhOrderAddEnter enter) {
        return new Response<>(frInWhOrderService.addFrInWhOrder(enter));
    }

    /**
     * 确认入库
     */
    @PostMapping("/confirm")
    @ApiOperation(value = "确认入库", response = GeneralResult.class)
    public Response<GeneralResult> confirmInWh(@ModelAttribute IdEnter enter) {
        return new Response<>(frInWhOrderService.confirmInWh(enter));
    }

}
