package com.redescooter.ses.web.ros.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.wh.StockItemStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
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
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterIdEnter;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private OpeStockProdProductService opeStockProdProductService;

    @Autowired
    private OpeCustomerService opeCustomerService;


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //查询分配整车列表
     * @Date 2020/4/24 17:01
     * @Param [enter]
     */
    @Override
    public PageResult<ChooseScooterResult> chooseScooterList(ChooseScooterIdEnter enter) {
        //查询可分配的整车列表
        QueryWrapper<OpeStockProdProduct> opeStockProdProductQueryWrapper = new QueryWrapper<>();
        opeStockProdProductQueryWrapper.eq(OpeStockProdProduct.COL_DR, 0);
        opeStockProdProductQueryWrapper.eq(OpeStockProdProduct.COL_STATUS, StockItemStatusEnums.AVAILABLE.getValue());
        List<OpeStockProdProduct> opeStockProdProductList = opeStockProdProductService.list(opeStockProdProductQueryWrapper);

        //可分配的整车列表为空
        if (CollectionUtils.isEmpty(opeStockProdProductList)) {
            //throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }

        //可分配的整车列表
        List<ChooseScooterResult> chooseScooterList = new ArrayList<>();
        opeStockProdProductList.forEach(opeStockProdProduct -> {
            ChooseScooterResult chooseScooterResult = null;
            chooseScooterList.add(
                    chooseScooterResult = ChooseScooterResult.builder()
                            .id(opeStockProdProduct.getId())
                            .batchNum(opeStockProdProduct.getLot())
                            .build());
        });
        return PageResult.create(enter, opeStockProdProductList.size(), chooseScooterList);
    }


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
