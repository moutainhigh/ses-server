package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesDeleteEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.BriefcasesService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesFolderResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesListResult;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BriefcasesServiceImpl implements BriefcasesService {

    @Autowired
    private SellsyService sellsyService;

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
    public SellsyBriefcaseUploadFileResult briefcasesUploadFile(SellsyBriefcasesUploadFileEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.UPDATE.getValue())
                .method(SellsyMethodConstant.Briefcases_UploadFile).params(enter).build();
        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

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
