package com.redescooter.ses.web.ros.controller.production;

import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
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

import java.util.List;

/**
 * @ClassName:AssemblyController
 * @description: AssemblyController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 19:13
 */
@Log4j2
@Api(tags = {"组装单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "调拨单类型", response = SaveAssemblyProductResult.class)
    public Response<List<SaveAssemblyProductResult>> countByType(@ModelAttribute @ApiParam("请求参数") SaveAssemblyProductEnter enter) {
        return new Response<>(assemblyService.queryAssemblyProduct(enter));
    }
}
