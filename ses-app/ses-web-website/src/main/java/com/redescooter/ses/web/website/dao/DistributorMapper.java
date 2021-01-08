package com.redescooter.ses.web.website.dao;

import com.redescooter.ses.web.website.vo.distributor.DistributorDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDistributorEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 3:11 下午
 * @Description 经销商数据层
 **/
public interface DistributorMapper {

    /**
     * 根据经纬度获取指定范围内的列表
     *
     * @param enter
     * @return
     */
    List<DistributorDetailsResult> getlistOrderByDistance(QueryDistributorEnter enter);
}
