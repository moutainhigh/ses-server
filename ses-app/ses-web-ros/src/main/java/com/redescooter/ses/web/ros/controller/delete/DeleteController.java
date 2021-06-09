package com.redescooter.ses.web.ros.controller.delete;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
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
    public Response<BooleanResult> deleteCustomer(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(deleteService.deleteCustomer(enter));
    }









}
