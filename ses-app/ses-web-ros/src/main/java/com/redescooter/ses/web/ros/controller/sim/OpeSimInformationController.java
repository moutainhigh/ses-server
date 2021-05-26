package com.redescooter.ses.web.ros.controller.sim;

import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
