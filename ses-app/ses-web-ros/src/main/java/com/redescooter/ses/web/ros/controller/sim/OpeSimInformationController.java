package com.redescooter.ses.web.ros.controller.sim;

import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Charles
 * @since 2021-05-26 20:58:06
 */
@Api(tags = "sim卡信息(OpeSimInformation)")
@RestController
@RequestMapping("/ope/sim/information")
public class OpeSimInformationController {

    @Autowired
    private OpeSimInformationService opeSimInformationService;

    @PostMapping(value = "/details")
    @ApiOperation(value = "Sim卡信息")
    public Response<MenuTreeResult> details() throws IOException {
        opeSimInformationService.getCurrentBalance();

        return new Response<>();
    }

}
