package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.distributor.SavaOrUpdateDistributorEnter;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/25 17:52
 */
public interface WebDistributorService {

    /**
     * ROS经销商数据同步到官网
     * @param enter
     */
   void saveOrUpdateDistribut (SavaOrUpdateDistributorEnter enter);

}
