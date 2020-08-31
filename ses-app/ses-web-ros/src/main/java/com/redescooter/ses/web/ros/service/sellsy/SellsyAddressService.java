package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.address.SellsyAddressResult;

import java.util.List;

public interface SellsyAddressService {

    /**
     * 地址列表
     */
    public List<SellsyAddressResult> queryAddressList();

    /**
     * 地址详情
     * @param enter
     */
    public SellsyAddressResult queryAddressOne(SellsyIdEnter enter);

}