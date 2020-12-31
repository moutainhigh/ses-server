package com.redescooter.ses.mobile.rps.dao.product;

import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteResultResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品质检模板子信息相关 Mapper接口
 * @author assert
 * @date 2020/12/31 16:03
 */
public interface ProductionQualityTemplateBMapper {

    /**
     * 根据productionTemplateId集合查询产品质检模板子信息
     * @param templateIds
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteResultResult>
     * @author assert
     * @date 2020/12/31
    */
    List<ProductQcTempleteResultResult> getProductionQualityTemplateBByTemplateId(@Param("templateIds") List<Long> templateIds);

}
