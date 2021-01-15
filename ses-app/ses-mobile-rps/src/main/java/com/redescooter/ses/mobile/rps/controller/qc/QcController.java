package com.redescooter.ses.mobile.rps.controller.qc;

import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.qc.QcService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO;
import com.redescooter.ses.mobile.rps.vo.qc.SaveQcResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 质检单接口管理
 * @author assert
 * @date 2021/1/14 13:51
 */
@Api(tags = {"质检单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/qc", produces = MediaType.APPLICATION_JSON_VALUE)
public class QcController {

    @Resource
    private QcService qcService;


    /**
     * 根据产品id查询产品质检模板信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO>
     * @author assert
     * @date 2021/1/6
     */
    @ApiOperation(value = "出库单质检模板", notes = "根据产品id查询产品质检模板信息")
    @PostMapping(value = "/qcTemplate")
    public Response<QueryQcTemplateResultDTO> getQcTemplateByIdAndType(@ModelAttribute QueryQcTemplateParamDTO paramDTO) {
        return new Response<>(qcService.getQcTemplateByIdAndType(paramDTO));
    }

    /**
     * 保存质检结果
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.SaveQcResultDTO>
     * @author assert
     * @date 2021/1/4
     */
    @ApiOperation(value = "保存质检结果")
    @PostMapping(value = "/saveQcResult")
    public Response<SaveQcResultDTO> saveQcResult(@ModelAttribute SaveQcResultParamDTO paramDTO) {
        return new Response<>(qcService.saveQcResult(paramDTO));
    }

}
