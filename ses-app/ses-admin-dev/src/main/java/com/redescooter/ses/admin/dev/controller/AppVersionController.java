package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.ReleaseAppVersionParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 应用版本接口管理
 * @author assert
 * @date 2020/12/3 13:51
 */
@Api(tags = {"应用版本管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/app/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionController {

    @Reference
    private AppVersionService appVersionService;

    /**
     * 查询版本列表信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.foundation.vo.app.AppVersionDTO>
     * @author assert
     * @date 2020/12/3
    */
    @ApiOperation(value = "查询版本列表信息")
    @GetMapping(value = "/query")
    public Response<AppVersionDTO> queryAppVersion(@ModelAttribute QueryAppVersionParamDTO paramDTO) {
        return null;
    }

    /**
     * 创建新版本
     * @param appVersionDTO
     * @return com.redescooter.ses.api.common.vo.base.Response
     * @author assert
     * @date 2020/12/3
    */
    @ApiOperation(value = "创建新版本")
    @PostMapping(value = "")
    public Response<GeneralResult> insertAppVersion(@ModelAttribute AppVersionDTO appVersionDTO) {
        return null;
    }

    /**
     * 发布版本
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/3
    */
    @ApiOperation(value = "发布版本")
    @PostMapping(value = "/release")
    public Response<GeneralResult> releaseAppVersion(@ModelAttribute ReleaseAppVersionParamDTO paramDTO) {
        return null;
    }

    /**
     * 根据id删除版本信息
     * @param id
     * @return com.redescooter.ses.api.common.vo.base.Response
     * @author assert
     * @date 2020/12/3
    */
    @ApiOperation(value = "删除版本", notes = "根据id删除版本信息")
    @PutMapping(value = "/{id}")
    public Response deleteAppVersionById(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * 获取不同应用版本类型数量
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.String,java.lang.Integer>>
     * @author assert
     * @date 2020/12/3
    */
    @ApiOperation(value = "获取不同应用版本类型数量")
    @GetMapping(value = "/getAppVersionTypeCount")
    public Response<Map<String, Integer>> getAppVersionTypeCount() {
        return null;
    }

}
