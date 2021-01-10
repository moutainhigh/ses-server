package com.redescooter.ses.web.website.service.impl;

import java.math.BigDecimal;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.enums.LnstallmentMonthEnums;
import com.redescooter.ses.web.website.enums.PaymentTypeEnums;
import com.redescooter.ses.web.website.service.ProductPriceService;
import com.redescooter.ses.web.website.vo.product.AddProductPriceEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class ProductPriceServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductPriceService productPriceService;

    @Test
    void addProductPrice() {

        AddProductPriceEnter e50Enter = new AddProductPriceEnter();
        e50Enter.setProductModelId(122680341434368l);
        e50Enter.setPriceType(String.valueOf(PaymentTypeEnums.BY_STAGES.getValue()));
        e50Enter.setInstallmentTime(String.valueOf(LnstallmentMonthEnums.DOWN_PAYMENTS.getValue()));
        e50Enter.setPrice(new BigDecimal("899"));
        e50Enter.setStartPrice(new BigDecimal(""));
        e50Enter.setPrepaidDeposit(new BigDecimal("690"));
        e50Enter.setAmountDiscount(new BigDecimal("0"));
        e50Enter.setUserId(0L);
        productPriceService.addProductPrice(e50Enter);


        AddProductPriceEnter E100Enter = new AddProductPriceEnter();
        E100Enter.setProductModelId(122680341434368l);
        E100Enter.setPriceType(String.valueOf(PaymentTypeEnums.BY_STAGES.getValue()));
        E100Enter.setInstallmentTime(String.valueOf(LnstallmentMonthEnums.DOWN_PAYMENTS.getValue()));
        E100Enter.setPrice(new BigDecimal("899"));
        E100Enter.setPrepaidDeposit(new BigDecimal("690"));
        E100Enter.setAmountDiscount(new BigDecimal("0"));
        E100Enter.setUserId(0L);
        productPriceService.addProductPrice(E100Enter);

        AddProductPriceEnter E125Enter = new AddProductPriceEnter();
        E125Enter.setProductModelId(122680341434368l);
        E125Enter.setPriceType(String.valueOf(PaymentTypeEnums.BY_STAGES.getValue()));
        E125Enter.setInstallmentTime(String.valueOf(LnstallmentMonthEnums.DOWN_PAYMENTS.getValue()));
        E125Enter.setPrice(new BigDecimal("899"));
        E125Enter.setPrepaidDeposit(new BigDecimal("690"));
        E125Enter.setAmountDiscount(new BigDecimal("0"));
        E125Enter.setUserId(0L);

        productPriceService.addProductPrice(E125Enter);
    }
}