package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.distributor.AddDistributorEnter;
import com.redescooter.ses.web.website.vo.distributor.DistributorDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDistributorEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:27 上午
 * @Description 经销商服务
 **/
public interface DistributorService {
    /**
     * 创建经销商
     *
     * @param enter
     * @return
     */
    Boolean addDistributor(AddDistributorEnter enter);

    /**
     * 获取经销商详情
     *
     * @param enter
     */
    DistributorDetailsResult getDistributorDetails(IdEnter enter);

    /**
     * 经销商列表
     *
     * @param enter
     * @return getDistributorList
     */
    List<DistributorDetailsResult> getDistributorList(QueryDistributorEnter enter);
}
