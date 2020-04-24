package com.redescooter.ses.web.ros.service.customer.impl;

import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.exception.ExceptionCode;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @ClassName:TransferScooterServiceImpl
 * @description: TransferScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:43
 */
@Service
public class TransferScooterServiceImpl implements TransferScooterService {

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    /**
     * 车辆分配
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult transferScooter(TransferScooterEnter enter) {
        //验证客户 状态
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(), ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        //验证库存信息
        Collection<OpeStockProdProduct> opeStockProdProductList = opeStockProdProductService.listByIds(enter.getStockItemId());
        if (CollectionUtils.isEmpty(opeStockProdProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        if (opeStockProdProductList.size() != enter.getStockItemId().size()) {

        }


        return null;
    }
}
