package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.distributor.AddDealerEnter;
import com.redescooter.ses.web.website.vo.distributor.DealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.MapDealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDealerEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:27 上午
 * @Description 经销商服务
 **/
public interface DealerService {
    /**
     * 创建经销商
     *
     * @param enter
     * @return
     */
    GeneralResult addDistributor(AddDealerEnter enter);

    /**
     * 获取经销商详情
     *
     * @param enter
     */
    DealerDetailsResult getDistributorDetails(IdEnter enter);

    /**
     * 经销商列表
     *
     * @param enter
     * @return getDistributorList
     */
    List<MapDealerDetailsResult> getDistributorList(QueryDealerEnter enter);
}
