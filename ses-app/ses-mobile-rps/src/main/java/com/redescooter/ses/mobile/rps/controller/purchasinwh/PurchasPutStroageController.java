package com.redescooter.ses.mobile.rps.controller.purchasinwh;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.impl.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.PutStorageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Api(tags = {"采购仓库待入库信息"})
@CrossOrigin
@RestController
@RequestMapping(value = "/purchas/PutStroage/")
public class PurchasPutStroageController{

    @Autowired
    private PurchasPutStroageService purchasPutStroageService;

    @PostMapping(value = "/PutStroageList")
    @ApiOperation(value = "采购仓库待入库信息", response = PutStorageResult.class)
    public Response<List<PutStorageResult>> purchasPutStroageList(@ModelAttribute @ApiParam("请求参数") PageResult  enter) {
        List<PutStorageResult> putStorageResult=purchasPutStroageService.purchasPutStroageList();
        return new Response<>();
    }


}
