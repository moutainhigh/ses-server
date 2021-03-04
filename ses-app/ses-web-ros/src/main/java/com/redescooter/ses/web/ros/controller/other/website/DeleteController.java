package com.redescooter.ses.web.ros.controller.other.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.DeleteService;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:DeleteController
 * @description: DeleteCustomerController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/07 13:40
 */

@Slf4j
@Api(tags = {"删除业务"})
@RestController
@RequestMapping(value = "/delete", method = RequestMethod.POST)
public class DeleteController {

    @Autowired
    private DeleteService deleteService;

    @IgnoreLoginCheck
    @DeleteMapping(value = "/customer")
    @ApiOperation(value = "删除客户", response = GeneralResult.class)
    public Response<GeneralResult> deleteCustomer(@ModelAttribute @ApiParam("请求参数") StorageEamilEnter enter) {
        return new Response<>(deleteService.deleteCustomer(enter));
    }

    @IgnoreLoginCheck
    @DeleteMapping(value = "/inquiry")
    @ApiOperation(value = "删除询价单", response = GeneralResult.class)
    public Response<GeneralResult> deleteInquiry(@ModelAttribute @ApiParam("请求参数") StorageEamilEnter enter) {
        return new Response<>(deleteService.deleteInquiry(enter));
    }

}