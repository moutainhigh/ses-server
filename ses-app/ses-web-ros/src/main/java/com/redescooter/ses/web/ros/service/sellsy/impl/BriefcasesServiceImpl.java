package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.exception.ThirdExceptionCodeEnums;
import com.redescooter.ses.web.ros.service.sellsy.BriefcasesService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesDeleteEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesFolderResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesListResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
public class BriefcasesServiceImpl implements BriefcasesService {

    @Autowired
    private SellsyService sellsyService;

    @Autowired
    private FileAppService fileAppService;

    @Autowired
    private SellsyConfig sellsyConfig;

    @Override
    public SellsyBriefcasesListResult queryBriefcasesList(SellsyBriefcasesListEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Briefcases_GetList).params(enter).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        switch (enter.getSearch().getMode()) {
            case files:
                return new SellsyBriefcasesListResult(
                        sellsyService.jsonMaptoList(sellsyGeneralResult, new SellsyBriefcasesFileResult()), null);
            case folders:
                return new SellsyBriefcasesListResult(null,
                        sellsyService.jsonMaptoList(sellsyGeneralResult, new SellsyBriefcasesFolderResult()));
            default:
                return new SellsyBriefcasesListResult();
        }
    }

    /**
     * 上传文件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyBriefcaseUploadFileResult briefcasesUploadFile(SellsyBriefcasesUploadFileEnter enter, MultipartFile multipartFile) {
        InputStream downloadInputStream = null;
        if (StringUtils.isNotEmpty(enter.getFileUrl()) && multipartFile == null) {
            //通过阿里云下载文件
            downloadInputStream = fileAppService.download(enter.getFileUrl());
            //获取文件下
            try {
                multipartFile = new MockMultipartFile(String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()),
                        ContentType.APPLICATION_OCTET_STREAM.toString(), downloadInputStream);
                //关输入流
                downloadInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //阿里云文件地址为空，或者阿里云文件地址不为空并且 本地上传的文件也不为空  优先级： 本地文件 > 阿里云文件
        //如果均为空 抛异常
        if (multipartFile.isEmpty()) {
            throw new SesWebRosException(ThirdExceptionCodeEnums.SELLSY_FILE_IS_EMPTY.getCode(), ThirdExceptionCodeEnums.SELLSY_FILE_IS_EMPTY.getMessage());
        }

        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.UPDATE.getValue())
                        .method(SellsyMethodConstant.Briefcases_UploadFile).params(enter).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecutionByFile(sellsyExecutionEnter, multipartFile);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyBriefcaseUploadFileResult());
    }

    /**
     * 附件删除
     *
     * @param enter
     */
    @Transactional
    @Override
    public void briefcasesDelete(SellsyBriefcasesDeleteEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.DELETE.getValue())
                        .method(SellsyMethodConstant.Briefcases_Delete).params(enter).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
    }
}
