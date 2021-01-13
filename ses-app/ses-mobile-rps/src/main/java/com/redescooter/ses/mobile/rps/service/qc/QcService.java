package com.redescooter.ses.mobile.rps.service.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO;

/**
 * 质检相关业务接口
 * @author assert
 * @date 2021/1/12 17:58
 */
public interface QcService {

    /**
     * 根据产品id查询产品质检模板信息
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO
     * @author assert
     * @date 2021/1/6
     */
    QueryQcTemplateResultDTO getQcTemplateByIdAndType(QueryQcTemplateParamDTO paramDTO);

    /**
     * 保存质检结果
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/4
     */
    GeneralResult saveQcResult(SaveQcResultParamDTO paramDTO);

}
