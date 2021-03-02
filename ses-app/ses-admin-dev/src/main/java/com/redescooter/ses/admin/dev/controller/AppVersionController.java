package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.FileUploadService;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.version.ReleaseAppVersionParamDTO;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO;
import com.redescooter.ses.api.foundation.vo.app.InsertAppVersionDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionParamDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 应用版本接口管理
 *
 * @author assert
 * @date 2020/12/3 13:51
 */
@Api(tags = {"应用版本管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/app/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionController {

    @DubboReference
    private AppVersionService appVersionService;

    @Resource
    private FileUploadService fileUploadService;

    /**
     * 查询应用版本列表
     *
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/3
     */
    @ApiOperation(value = "查询应用版本列表")
    @GetMapping(value = "/query")
    public Response<PageResult<QueryAppVersionResultDTO>> queryAppVersion(@ModelAttribute QueryAppVersionParamDTO paramDTO) {
        return new Response<>(appVersionService.queryAppVersion(paramDTO));
    }

    /**
     * 创建应用版本
     *
     * @param appVersionDTO
     * @return com.redescooter.ses.api.common.vo.base.Response
     * @author assert
     * @date 2020/12/3
     */
    @ApiOperation(value = "创建应用版本")
    @PostMapping(value = "/insert")
    public Response<Integer> insertAppVersion(@ModelAttribute InsertAppVersionDTO appVersionDTO) {
        return new Response<>(appVersionService.insertAppVersion(appVersionDTO));
    }

    /**
     * 修改应用版本信息
     *
     * @param appVersionDTO
     * @return com.redescooter.ses.api.common.vo.base.Response
     * @author assert
     * @date 2020/12/4
     */
    @ApiOperation(value = "修改应用版本信息")
    @PostMapping(value = "/update")
    public Response<Integer> updateAppVersion(@ModelAttribute InsertAppVersionDTO appVersionDTO) {
        return new Response<>(appVersionService.updateAppVersion(appVersionDTO));
    }

    /**
     * 发布版本
     *
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/3
     */
    @ApiOperation(value = "发布版本")
    @PostMapping(value = "/release")
    public Response<Integer> releaseAppVersion(@ModelAttribute ReleaseAppVersionParamDTO paramDTO) {
        return new Response(appVersionService.releaseAppVersion(paramDTO));
    }

    /**
     * 根据id删除版本信息
     *
     * @param id
     * @return com.redescooter.ses.api.common.vo.base.Response
     * @author assert
     * @date 2020/12/3
     */
    @ApiOperation(value = "删除版本", notes = "根据id删除版本信息")
    @DeleteMapping(value = "/delete/{id}")
    public Response<Integer> deleteAppVersionById(@PathVariable("id") Long id) {
        return new Response(appVersionService.deleteAppVersionById(id));
    }

    /**
     * 根据id查询应用版本详情
     *
     * @param id
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/4
     */
    @ApiOperation(value = "查询应用版本详情", notes = "根据id查询应用版本详情")
    @GetMapping(value = "/detail/{id}")
    public Response<QueryAppVersionResultDTO> getAppVersionById(@PathVariable("id") Long id) {
        return new Response<>(appVersionService.getAppVersionById(id));
    }

    /**
     * 获取所有应用版本类型数量
     *
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map < java.lang.String, java.lang.Integer>>
     * @author assert
     * @date 2020/12/3
     */
    @ApiOperation(value = "获取所有应用版本类型数量", notes = "返回map的key等同于页面tab名称")
    @GetMapping(value = "/countByType")
    public Response<Map<String, Integer>> getAppVersionTypeCount() {
        return new Response<>(appVersionService.getAppVersionTypeCount());
    }

    /**
     * 查询所有应用正在使用的版本
     *
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO>
     * @author assert
     * @date 2020/12/8
     */
    @ApiOperation(value = "查询所有应用正在使用的版本")
    @GetMapping(value = "/getAllActiveAppVersion")
    public Response<List<QueryAppVersionResultDTO>> getAllActiveAppVersion() {
        return new Response<>(appVersionService.getAllActiveAppVersion());
    }

    /**
     * 上传应用更新包
     *
     * @param file
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.foundation.vo.app.FileUploadResultDTO>
     * @author assert
     * @date 2020/12/4
     */
    @ApiOperation(value = "上传应用更新包")
    @PostMapping(value = "/fileUpload")
    public Response<FileUploadResultDTO> fileUpload(@RequestParam("file") MultipartFile file) {
        // 文件上传操作在当前服务中完成,禁止跨服务操作
        return new Response<>(fileUploadService.fileUpload(file));
    }

    /**
     * 根据type查询当前应用版本号信息
     *
     * @param type
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.List < SelectBaseResultDTO>>
     * @author assert
     * @date 2020/12/7
     */
    @ApiOperation(value = "获取当前应用版本号信息", notes = "根据type查询当前应用所有版本号信息")
    @GetMapping(value = "/getAppVersionByType/{type}")
    public Response<List<SelectBaseResultDTO>> getAppVersionByType(@PathVariable("type") Integer type) {
        return new Response<>(appVersionService.getAppVersionByType(type));
    }

    /**
     * 根据标签全模糊检索应用标签信息
     *
     * @param label
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.List < java.lang.String>>
     * @author assert
     * @date 2020/12/8
     */
    @ApiOperation(value = "查询应用版本标签信息", notes = "根据标签全模糊检索应用标签信息")
    @GetMapping(value = "/getAppVersionLabelByLabel/{label}")
    public Response<List<String>> getAppVersionLabelByLabel(@PathVariable("label") String label) {
        return new Response<>(appVersionService.getAppVersionLabelByLabel(label));
    }

}
