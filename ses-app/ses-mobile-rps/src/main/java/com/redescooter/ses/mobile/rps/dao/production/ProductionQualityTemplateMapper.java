package com.redescooter.ses.mobile.rps.dao.production;

import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateDTO;
import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateResultDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品质检模板相关 Mapper接口
 * @author assert
 * @date 2020/12/31 16:03
 */
public interface ProductionQualityTemplateMapper {

    /**
     * 根据templateId集合查询产品质检结果信息
     * @param templateIds
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateResultDTO>
     * @author assert
     * @date 2021/1/6
    */
    List<ProductQcTemplateResultDTO> getProductQcTemplateResultByTemplateIds(@Param("templateIds") List<Long> templateIds);

    /**
     * 根据productionId、productionType查询产品质检模板信息
     * @param productionId
     * @param productionType
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateDTO>
     * @author assert
     * @date 2021/1/6
    */
    List<ProductQcTemplateDTO> getQcTemplateByProductIdAndType(@Param("productionId") Long productionId,
                                                               @Param("productionType") Integer productionType);

}
