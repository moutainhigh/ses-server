package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesDeleteEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesListResult;

/**
 * 文件上传
 */
public interface BriefcasesService {

    /**
     * 查询文件列表
     * 
     * @param enter
     * @return
     */
    SellsyBriefcasesListResult queryBriefcasesList(SellsyBriefcasesListEnter enter);

    /**
     * 上传文件
     * 
     * @param enter
     * @return
     */
    SellsyBriefcaseUploadFileResult briefcasesUploadFile(SellsyBriefcasesUploadFileEnter enter);

    /**
     * 附件删除
     * 
     * @param enter
     */
    void briefcasesDelete(SellsyBriefcasesDeleteEnter enter);
}
