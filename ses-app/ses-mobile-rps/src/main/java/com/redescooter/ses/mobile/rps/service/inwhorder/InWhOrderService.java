package com.redescooter.ses.mobile.rps.service.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.*;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 * 入库单相关业务接口
 * @author assert
 * @date 2021/1/15 18:09
 */
public interface InWhOrderService {

    /**
     * 入库单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/15
    */
    Map<Integer, Integer> getInWarehouseOrderTypeCount(GeneralEnter enter);

    /**
     * 入库类型数量统计
     * @param paramDTO
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/15
    */
    Map<Integer, Integer> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 入库单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO>
     * @author assert
     * @date 2021/1/15
    */
    PageResult<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO);

    /**
     * 根据id查询入库单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO
     * @author assert
     * @date 2021/1/15
    */
    InWhOrderDetailDTO getInWarehouseOrderDetailById(IdEnter enter);

    /**
     * 根据productId查询入库单产品详情
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO
     * @author assert
     * @date 2021/1/19
    */
    InWhOrderProductDetailDTO getProductDetailByProductId(QueryProductDetailParamDTO paramDTO);

    /**
     * 确认入库
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/19
    */
    GeneralResult confirmStorage(IdEnter enter);
    
    /**
     * 保存扫码结果
     * @param paramDTO
     * @return com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO
     * @author assert
     * @date 2021/1/21
    */
    SaveScanCodeResultDTO saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO);

}
