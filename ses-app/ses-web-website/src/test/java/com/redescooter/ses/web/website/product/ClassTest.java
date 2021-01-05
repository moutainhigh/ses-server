package com.redescooter.ses.web.website.product;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.SesWebWebsiteApplication;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.base.SiteProductClassService;
import lombok.extern.log4j.Log4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author jerry
 * @Date 2021/1/4 11:33 上午
 * @Description TODO:产品大类测试类
 **/

public class ClassTest extends SesWebWebsiteApplication {

    @Autowired(required = true)
    private SiteProductClassService productClassService;

    @Reference
    private IdAppService idAppService;

    @Test
    public void save() {

        List<SiteProductClass> list = new ArrayList<>();

        SiteProductClass highClass = new SiteProductClass();
        highClass.setId(idAppService.getId(SequenceName.SITE_PRODUCT_CLASS));
        highClass.setDr(Constant.DR_FALSE);
        highClass.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        highClass.setProductClassName("high speed");
        highClass.setProductClassCode(MainCode.generateByShuffle());
        highClass.setCnName("高速");
        highClass.setFrName("HAUTE VITESSE");
        highClass.setEnName("HIGH SPEED");
        highClass.setSynchronizeFlag(Boolean.FALSE);
        highClass.setRevision(0);
        highClass.setCreatedBy(0L);
        highClass.setCreatedTime(new Date());
        highClass.setUpdatedBy(0L);
        highClass.setUpdatedTime(new Date());

        SiteProductClass lowClass = new SiteProductClass();
        lowClass.setId(idAppService.getId(SequenceName.SITE_PRODUCT_CLASS));
        lowClass.setDr(Constant.DR_FALSE);
        lowClass.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        lowClass.setProductClassName("high speed");
        lowClass.setProductClassCode(MainCode.generateByShuffle());
        lowClass.setCnName("高速");
        lowClass.setFrName("HAUTE VITESSE");
        lowClass.setEnName("HIGH SPEED");
        lowClass.setSynchronizeFlag(Boolean.FALSE);
        lowClass.setRevision(0);
        lowClass.setCreatedBy(0L);
        lowClass.setCreatedTime(new Date());
        lowClass.setUpdatedBy(0L);
        lowClass.setUpdatedTime(new Date());

        list.add(highClass);
        list.add(lowClass);

        productClassService.batchInsert(list);
    }
}
