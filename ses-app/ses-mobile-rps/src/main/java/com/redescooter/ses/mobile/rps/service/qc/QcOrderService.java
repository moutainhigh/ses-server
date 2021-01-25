package com.redescooter.ses.mobile.rps.service.qc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;

import java.util.Map;

/**
 * 质检相关业务接口
 * @author assert
 * @date 2021/1/12 17:58
 */
public interface QcOrderService {

    /**
     * 质检单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/21
     */
    Map<Integer, Integer> getQcOrderTypeCount(GeneralEnter enter);

    /**
     * 质检类型数量统计
     * @param paramDTO
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/21
     */
    Map<Integer, Integer> getQcTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 质检单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.qc.QueryQcOrderResultDTO>
     * @author assert
     * @date 2021/1/25
    */
    PageResult<QueryQcOrderResultDTO> getQcOrderList(QueryQcOrderParamDTO paramDTO);

    /**
     * 开始质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/25
    */
    GeneralResult startQc(IdEnter enter);

    /**
     * 根据id查询质检单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.qc.QcOrderDetailDTO
     * @author assert
     * @date 2021/1/25
    */
    QcOrderDetailDTO getQcOrderDetailById(IdEnter enter);

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
     * @return com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO
     * @author assert
     * @date 2021/1/4
     */
    SaveScanCodeResultDTO saveQcResult(SaveQcResultParamDTO paramDTO);

    /**
     * 完成质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/25
    */
    GeneralResult completeQc(IdEnter enter);

}
