package com.redescooter.ses.web.website.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.DistributorTypeEnums;
import com.redescooter.ses.web.website.service.DealerService;
import com.redescooter.ses.web.website.service.base.SiteDistributorService;
import com.redescooter.ses.web.website.vo.distributor.DealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.ExpressDealerExcleData;
import com.redescooter.ses.web.website.vo.distributor.MapDealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDealerEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class DealerServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private SiteDistributorService siteDistributorService;

    @Autowired
    private DealerService dealerService;

    @DubboReference
    private IdAppService idAppService;

    @Test
    void addDistributor() {

        String excelUrl = "https://rede.oss-cn-shanghai.aliyuncs.com/1612548293195.xlsx";

        ExcelImportResult<ExpressDealerExcleData> excelImportResult = importExcelService.importOssExcel(excelUrl, ExpressDealerExcleData.class, new ImportParams());

        List<SiteDistributor> saveList = new ArrayList<>();

        excelImportResult.getList().forEach(e -> {
            SiteDistributor distributor = new SiteDistributor();
            distributor.setId(idAppService.getId(SequenceName.SITE_PARTS));
            distributor.setDr(Constant.DR_FALSE);
            distributor.setStatus(CommonStatusEnums.NORMAL.getValue());
            distributor.setCode(new StringBuffer().append("DR_").append(MainCode.generateByShuffle()).toString());
            distributor.setName(e.getName());
            if (e.getTel() != null) {
                distributor.setTel(e.getTel().replace(" ", ""));
            }
            distributor.setAddress(e.getAdresse() == null ? "NO" : e.getAdresse());
            if (e.getLatitude() != null || e.getLongitude() != null) {
                distributor.setLongitude(setBigDecimal(e.getLongitude().replace(" ", ""),8));
                distributor.setLatitude(setBigDecimal(e.getLatitude().replace(" ", ""),8));
            }
            distributor.setCp(e.getCp());
            if (e.getType().equals("R")) {
                distributor.setType(DistributorTypeEnums.SALES_STORES.getValue());
            } else if (e.getType().equals("A")) {
                distributor.setType(DistributorTypeEnums.REPAIR_STORE.getValue());
            } else if (e.getType().equals("R/A")) {
                distributor.setType(DistributorTypeEnums.ALL.getValue());
            }
            distributor.setSynchronizeFlag(false);
            distributor.setRevision(0);
            distributor.setCreatedTime(new Date());
            distributor.setUpdatedBy(0l);
            distributor.setUpdatedTime(new Date());
            distributor.setSynchronizeFlag(false);
            distributor.setCreatedBy(0l);
            distributor.setCreatedTime(new Date());
            saveList.add(distributor);
        });

        siteDistributorService.saveBatch(saveList);
    }

    @Test
    void getDistributorDetails() {
    }

    @Test
    void getDistributorList() {
        QueryDealerEnter enter = new QueryDealerEnter();
        enter.setLongitude("2.30874130");
        enter.setLatitude("48.8695034");
        enter.setDistanceRange("100");

        List<MapDealerDetailsResult> distributorList = dealerService.getDistributorList(enter);

        System.out.println(distributorList.toString());
    }

    /**
     * 设置 BigDecimal的位数
     *
     * @param bdstr 数字串
     * @param num   需要的位数
     */
    private BigDecimal setBigDecimal(String bdstr, int num) {

        BigDecimal bdv = new BigDecimal(bdstr);//字符串转成bigdecimal
        bdv = bdv.setScale(num, BigDecimal.ROUND_HALF_UP);
        System.out.println(bdv);
        return bdv;
    }

}