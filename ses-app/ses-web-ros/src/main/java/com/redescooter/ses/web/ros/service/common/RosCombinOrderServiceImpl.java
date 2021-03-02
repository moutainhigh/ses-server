package com.redescooter.ses.web.ros.service.common;

import com.redescooter.ses.api.common.service.RosCombinOrderService;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2021/1/26 14:54
 */
@Slf4j
@DubboService
public class RosCombinOrderServiceImpl implements RosCombinOrderService {

    @Resource
    private ProductionPurchasService productionPurchasService;

    @Override
    public GeneralResult generatorQcOrderByCombin(IdEnter enter) {
        /**
         * 调用Chris生成组装质检单方法
         */
        return productionPurchasService.generatorQcOrderByCombin(enter);
    }

}
