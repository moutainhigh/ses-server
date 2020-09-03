package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptTypeResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameSysPositionController
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:20
 * @Version V1.0
 **/
@Api(tags = {"岗位模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/position")
public class SysPositionController{
    @Autowired
    private SysPositionService sysPositionService;

    @PostMapping(value = "/selectPositionType")
    @ApiOperation(value = "查询岗位类型", response = PositionTypeResult.class)
    public Response<List<PositionTypeResult>> selectDept(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(sysPositionService.selectPositionType(enter));
    }





}

