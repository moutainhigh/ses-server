package com.redescooter.ses.mobile.rps.service.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 * 出库单相关业务接口
 * @author assert
 * @date 2021/1/4 16:21
 */
public interface OutWarehouseOrderService {

    /**
     * 出库单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/4
    */
    Map<Integer, Integer> getOutWarehouseOrderTypeCount(GeneralEnter enter);

    /**
     * 出库类型数量统计
     * @param paramDTO
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/4
    */
    Map<Integer, Integer> getOutWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 出库单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.outwhorder.QueryOutWarehouseOrderResultDTO>
     * @author assert
     * @date 2021/1/4
    */
    PageResult<QueryOutWarehouseOrderResultDTO> getOutWarehouseOrderList(QueryOutWarehouseOrderParamDTO paramDTO);

    /**
     * 出库单提交质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/4
    */
    GeneralResult submitQc(IdEnter enter);

    /**
     * 根据id查询出库单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderDetailDTO
     * @author assert
     * @date 2021/1/4
    */
    OutWarehouseOrderDetailDTO getOutWarehouseOrderDetailById(IdEnter enter);

    /**
     * 根据id查询出库单产品详情
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/5
    */
    OutWhOrderProductDetailDTO getOutWhOrderProductDetailByProductId(QueryProductDetailParamDTO paramDTO);

    /**
     * 保存出库单产品扫码结果
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/22
    */
    GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO);

    /**
     * 提交出库
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021-01-10
     */
    GeneralResult outWarehouse(IdEnter enter);

}
