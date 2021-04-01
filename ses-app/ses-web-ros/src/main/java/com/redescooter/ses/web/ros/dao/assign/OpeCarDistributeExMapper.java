package com.redescooter.ses.web.ros.dao.assign;

import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/28 14:36
 */
@Mapper
public interface OpeCarDistributeExMapper {

    /**
     * 待分配列表条数
     */
    int getToBeAssignListCount(@Param("param") ToBeAssignListEnter enter);

    /**
     * 待分配列表
     */
    List<ToBeAssignListResult> getToBeAssignList(@Param("param") ToBeAssignListEnter enter);

    /**
     * 已分配列表条数
     */
    int getAssignedListCount(@Param("param") AssignedListEnter enter);

    /**
     * 已分配列表
     */
    List<AssignedListResult> getAssignedList(@Param("param") AssignedListEnter enter);

    /**
     * 根据客户id查询客户信息
     */
    ToBeAssignDetailCustomerInfoResult getCustomerInfo(@Param("customerId") Long customerId);

}
