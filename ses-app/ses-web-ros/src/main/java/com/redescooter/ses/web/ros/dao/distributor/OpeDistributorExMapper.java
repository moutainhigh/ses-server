package com.redescooter.ses.web.ros.dao.distributor;

import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorListEnter;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<DistributorListResult> getList(@Param("param") DistributorListEnter enter);

    /**
     * 门店列表条数
     */
    int getListCount(@Param("param") DistributorListEnter enter);

}
