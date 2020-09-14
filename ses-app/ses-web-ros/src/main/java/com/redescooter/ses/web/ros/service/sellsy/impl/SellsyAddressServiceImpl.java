package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAddressService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.address.SellsyAddressResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SellsyAddressServiceImpl implements SellsyAddressService {

    @Autowired
    private SellsyService sellsyService;

    /**
     * 地址列表
     */
    @Override
    public List<SellsyAddressResult> queryAddressList() {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Addresses_GetList).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormattingByPage(sellsyGeneralResult, new SellsyAddressResult());
    }

    /**
     * 地址详情
     * @param enter
     */
    @Override
    public SellsyAddressResult queryAddressOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Addresses_GetOne).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyAddressResult());
    }
}
