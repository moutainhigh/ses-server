package com.redescooter.ses.web.ros.controller.delete;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.delete.DeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:45
 */
@Api(value = "删除数据控制器", tags = "删除数据控制器")
@CrossOrigin
@RestController
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    private DeleteService deleteService;

    @PostMapping(value = "/deleteCustomer")
    @ApiOperation(value = "删除客户对应关系", response = BooleanResult.class)
    @IgnoreLoginCheck
    public Response<BooleanResult> deleteCustomer(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(deleteService.deleteCustomer(enter));
    }

    /**
     * 删除车辆bom
     * 入参是车辆bom的id
     */
    @PostMapping("/bom/scooter")
    @ApiOperation(value = "删除车辆bom", tags = "删除车辆bom")
    @IgnoreLoginCheck
    public Response<GeneralResult> deleteScooterBom(@ModelAttribute IdEnter enter) {
        return new Response<>(deleteService.deleteScooterBom(enter));
    }

    /**
     * 删除组装件bom
     * 入参是组装件bom的id
     */
    @PostMapping("/bom/combin")
    @ApiOperation(value = "删除组装件bom", tags = "删除组装件bom")
    @IgnoreLoginCheck
    public Response<GeneralResult> deleteCombinBom(@ModelAttribute IdEnter enter) {
        return new Response<>(deleteService.deleteCombinBom(enter));
    }

    /**
     * 删除车辆
     * 入参是平板序列号
     */
    @PostMapping("/scooter")
    @ApiOperation(value = "删除车辆", tags = "删除车辆")
    @IgnoreLoginCheck
    public Response<GeneralResult> deleteScooter(@ModelAttribute StringEnter enter) {
        return new Response<>(deleteService.deleteScooter(enter));
    }

    /**
     * 删除部件
     * 入参是部件id
     */
    @PostMapping("/part")
    @ApiOperation(value = "删除部件", tags = "删除部件")
    @IgnoreLoginCheck
    public Response<GeneralResult> deletePart(@ModelAttribute IdEnter enter) {
        return new Response<>(deleteService.deletePart(enter));
    }

    /**
     * 清空定金
     * @param enter
     * @return
     */
    @PostMapping("/deleteDeposit")
    @ApiOperation(value = "清空定金", tags = "清空定金")
    @IgnoreLoginCheck
    public Response<GeneralResult> deleteDeposit(@ModelAttribute GeneralEnter enter) {
        return new Response<>(deleteService.deleteDeposit(enter));
    }

}
