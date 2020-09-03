package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.website.DeleteService;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value = "/customer")
    @ApiOperation(value = "删除客户", response = GeneralResult.class)
    public Response<GeneralResult> deleteCustomer(@ModelAttribute @ApiParam("请求参数") StorageEamilEnter email) {
        return new Response<>(deleteService.deleteCustomer(email));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/inquiry")
    @ApiOperation(value = "删除询价单", response = GeneralResult.class)
    public Response<GeneralResult> deleteInquiry(@ModelAttribute @ApiParam("请求参数") StorageEamilEnter email) {
        return new Response<>(deleteService.deleteInquiry(email));
    }

}
