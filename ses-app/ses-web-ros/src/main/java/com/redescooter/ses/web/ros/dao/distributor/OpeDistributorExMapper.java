package com.redescooter.ses.web.ros.dao.distributor;

import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorListEnter;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorListResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorSaleProductResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/17 15:43
 */
@Mapper
public interface OpeDistributorExMapper {

    /**
     * 门店列表
     */
    List<DistributorListResult> getList(@Param("param") DistributorListEnter enter,@Param("deptIds") Set<Long> deptIds);

    /**
     * 门店列表条数
     */
    int getListCount(@Param("param") DistributorListEnter enter,@Param("deptIds") Set<Long> deptIds);

    /**
     * 门店类型选择销售,可售卖产品的数据来源
     */
    List<DistributorSaleProductResult> getSaleProduct();

}
