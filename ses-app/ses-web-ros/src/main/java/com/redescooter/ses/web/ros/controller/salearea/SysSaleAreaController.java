package com.redescooter.ses.web.ros.controller.salearea;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.salearea.SaleAreaService;
import com.redescooter.ses.web.ros.vo.salearea.RoleAreaEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaOpEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaSaveEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameSysSaleAreaController
 * @Description
 * @Author Aleks
 * @Date2020/9/3 14:02
 * @Version V1.0
 **/
@Api(tags = {"销售区域控制层"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/sale/area")
public class SysSaleAreaController {

     @Autowired
    private SaleAreaService saleAreaService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增销售区域", response = GeneralResult.class)
    public Response<GeneralResult> saleAreaSave(@ModelAttribute @ApiParam("请求参数") SaleAreaSaveEnter enter) {
        return new Response(saleAreaService.saleAreaSave(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除销售区域", response = GeneralResult.class)
    public Response<GeneralResult> saleAreaDetele(@ModelAttribute @ApiParam("请求参数") SaleAreaOpEnter enter) {
        return new Response(saleAreaService.saleAreaDetele(enter));
    }


}
