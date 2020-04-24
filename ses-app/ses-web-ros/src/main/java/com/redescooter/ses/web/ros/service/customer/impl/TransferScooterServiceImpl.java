package com.redescooter.ses.web.ros.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterIdEnter;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

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
        opeStockProdProductQueryWrapper.eq(OpeStockProdProduct.COL_STATUS, 2);
        List<OpeStockProdProduct> opeStockProdProductList = opeStockProdProductService.list(opeStockProdProductQueryWrapper);

        //可分配的整车列表为空
        if (CollectionUtils.isEmpty(opeStockProdProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }

        //可分配的整车列表
        List<ChooseScooterResult> chooseScooterList = new ArrayList<>();
        opeStockProdProductList.forEach(opeStockProdProduct -> {
            ChooseScooterResult chooseScooterResult = null;
            chooseScooterList.add(
                    chooseScooterResult = ChooseScooterResult.builder()
                            .id(1L)
                            .batchNum(opeStockProdProduct.getLot())
                            .build());
        });
        return PageResult.create(enter, opeStockProdProductList.size(), chooseScooterList);
    }
}
