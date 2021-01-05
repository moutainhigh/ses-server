package com.redescooter.ses.web.website.service;

import java.math.BigDecimal;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.enums.LnstallmentMonthEnums;
import com.redescooter.ses.web.website.enums.PaymentTypeEnums;
import com.redescooter.ses.web.website.vo.product.AddProductPriceEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class ProductPriceServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductPriceService productPriceService;

    @Test
    void addProductPrice() {

        AddProductPriceEnter enter = new AddProductPriceEnter();
        enter.setProductId(122680341434368l);
        enter.setPriceType(String.valueOf(PaymentTypeEnums.BY_STAGES.getValue()));
        enter.setInstallmentTime(String.valueOf(LnstallmentMonthEnums.DOWN_PAYMENTS.getValue()));
        enter.setPrice(new BigDecimal("899"));
        enter.setPrepaidDeposit(new BigDecimal("690"));
        enter.setAmountDiscount(new BigDecimal("0"));
        enter.setUserId(0L);

        productPriceService.addProductPrice(enter);
    }
}