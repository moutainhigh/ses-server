package com.redescooter.ses.mobile.rps.controller.preparematerial;

import com.alibaba.druid.filter.AutoLoad;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName:PrepareMaterialController
 * @description: PrepareMaterialController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:29
 */
@Log4j2
@Api(tags = {"备料模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/preparematerial")
public class PrepareMaterialController {

    @Autowired
    private PrepareMaterialService prepareMaterialService;

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = PrepareMaterialListResult.class)
    public Response<PageResult<PrepareMaterialListResult>> list(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(prepareMaterialService.list(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = PrepareMaterialDetailResult.class)
    public Response<PageResult<PrepareMaterialDetailResult>> detail(@ModelAttribute @ApiParam("请求参数") PrepareMaterialDetailEnter enter) {
        return new Response<>(prepareMaterialService.detail(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/save")
    @ApiOperation(value = "备料数据保存", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SavePrepareMaterialEnter enter) {
        return new Response<>(prepareMaterialService.save(enter));
    }
}
