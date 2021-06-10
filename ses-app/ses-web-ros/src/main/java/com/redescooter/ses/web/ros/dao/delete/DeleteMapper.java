package com.redescooter.ses.web.ros.dao.delete;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
