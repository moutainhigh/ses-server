package com.redescooter.ses.mobile.rps.service.combinorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.combinorder.*;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;

import java.util.Map;

/**
 * 组装单业务接口
 * @author assert
 * @date 2021/1/27 11:11
 */
public interface CombinationOrderService {

    /**
     * 组装单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/27
    */
    Map<Integer, Integer> getCombinationOrderTypeCount(GeneralEnter enter);

    /**
     * 组装单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderResultDTO>
     * @author assert
     * @date 2021/1/27
    */
    PageResult<QueryCombinationOrderResultDTO> getCombinationOrderList(QueryCombinationOrderParamDTO paramDTO);

    /**
     * 组装单开始组装
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/27
    */
    GeneralResult startCombination(IdEnter enter);

    /**
     * 根据id查询组装单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.combinorder.CombinationOrderDetailDTO
     * @author assert
     * @date 2021/1/27
    */
    CombinationOrderDetailDTO getCombinationOrderDetailById(IdEnter enter);

    /**
     * 查询组装单产品部件列表
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO
     * @author assert
     * @date 2021/1/27
    */
    CombinationListDetailDTO getCombinationOrderPartsList(QueryCombinationPartsListParamDTO paramDTO);

    /**
     * 保存组装单产品部件扫码信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/27
    */
    GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO);

    /**
     * 完成组装
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO
     * @author assert
     * @date 2021/1/27
    */
    SaveScanCodeResultDTO completeCombination(QueryCombinationPartsListParamDTO paramDTO);

    /**
     * 组装单提交质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/27
    */
    GeneralResult submitQc(IdEnter enter);

    /**
     * 生成组装单清单信息
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/27
    */
    GeneralResult generateCombinationOrderList(IdEnter enter);

}
