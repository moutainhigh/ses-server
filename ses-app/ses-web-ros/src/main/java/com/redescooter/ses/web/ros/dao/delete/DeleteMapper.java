package com.redescooter.ses.web.ros.dao.delete;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 13:57
 */
@Mapper
public interface DeleteMapper {

    /**
     * 删除车辆bom
     */
    int deleteScooterBom(@Param("id") Long id);

    int deleteCustomer(Long id);

    int deleteCustomerContact(Long id);

    /**
     * 删除组装件bom
     */
    int deleteCombinBom(@Param("id") Long id);

    int deleteCustomerInquiry(Long id);

    /**
     * 删除码库relation
     */
    int deleteCodebaseRelation(@Param("id") Long id);

    int deleteCustomerInquiryB(Long id);

    int deleteCarDistribute(Long id);

    int deleteCarDistributeNode(Long id);

    int deleteOrder(Long id);

    int deleteOrderB(Long id);

    /**
     * 删除库存产品序列号
     */
    int deleteWmsStockSerialNumber(@Param("id") Long id);

    /**
     * 删除部件
     */
    int deletePart(@Param("id") Long id);

    /**
     * 删除ope_product_price
     */
    int deletePrice(@Param("id") Long id);

    /**
     * 删除ope_product_price_history
     */
    int deletePriceHistory(@Param("id") Long id);

    int deleteDeposit();

    /**
     * 删除ope_in_whouse_scooter_b
     */
    int deleteInWhouseScooterB(@Param("id") Long id);

    /**
     * 删除ope_in_whouse_order
     */
    int deleteInWhouse(@Param("id") Long id);

}
