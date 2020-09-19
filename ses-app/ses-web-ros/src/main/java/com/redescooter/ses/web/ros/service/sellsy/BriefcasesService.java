package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesDeleteEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcasesListResult;
import org.springframework.web.multipart.MultipartFile;

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
     * todo 上传文件 需要调试 SDK 具体和Sellsy 沟通后 在进行
     *
     * @param enter
     * @return
     */
    SellsyBriefcaseUploadFileResult briefcasesUploadFile(SellsyBriefcasesUploadFileEnter enter, MultipartFile multipartFile);

    /**
     * 附件删除
     * 
     * @param enter
     */
    void briefcasesDelete(SellsyBriefcasesDeleteEnter enter);
}
