package com.redescooter.ses.web.ros.dao.delete;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 13:57
 */
@Mapper
public interface DeleteMapper {
    int deleteCustomer(Long id);

    int deleteCustomerContact(Long id);

    int deleteCustomerInquiry(Long id);

    int deleteCustomerInquiryB(Long id);

    int deleteCarDistribute(Long id);

    int deleteCarDistributeNode(Long id);
}
