package com.redescooter.ses.web.ros;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.WebResetPasswordEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.web.ros.config.SellsyConfig;
import com.redescooter.ses.web.ros.dm.SellsyCustomer;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceTotal;
import com.redescooter.ses.web.ros.dm.SellsyProduct;
import com.redescooter.ses.web.ros.enums.sellsy.*;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.SellsyCustomerService;
import com.redescooter.ses.web.ros.service.base.SellsyInvoiceTotalService;
import com.redescooter.ses.web.ros.service.base.SellsyProductService;
import com.redescooter.ses.web.ros.service.sellsy.*;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase.SellsyBriefcasesUploadFileEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.client.*;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.briefcases.SellsyBriefcaseUploadFileResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.client.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.website.WebEditCustomerEnter;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class SesWebRosApplicationTests {

    @Before
    public void setUp() throws Exception {
        System.out.println("单元测试开始--------------------->");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("单元测试结束--------------------->");
    }

    @Autowired
    private JedisService jedisService;

    @Autowired
    private SellsyProductService sellsyProductService;

    @Autowired
    private WebSiteTokenService webSiteService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private SellsyInvoiceTotalService sellsyInvoiceTotalService;

    @Autowired
    private SellsyDocumentService sellsyDocumentService;

    @Autowired
    private SellsyAccountSettingService sellsyAccountSettingService;

    @Autowired
    private SellsyClientService sellsyClientService;

    @Autowired
    private SellsyCatalogueService sellsyCatalogueService;

    @Autowired
    private BriefcasesService briefcasesService;

    @Test
    public void stream() {
        String test = "RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test + "40382"));
    }


    @Test
    public void testSendMail() {
        BaseSendMailEnter enter = new BaseSendMailEnter();
        enter.setMail("aleks@redescooter.com");
        enter.setRequestId("123456789qazwsx");
        webSiteService.sendEmail(enter);
    }


    @Test
    public void forgetPassword() {
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("123");
        enter.setConfirmPassword("123");
        enter.setRequestId("123456789qazwsx");
        webSiteService.forgetPassword(enter);
    }


    @Test
    public void resetPassword() {
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("UoFdxiOnLrvPX2sHOe%2FEbd6nBxta5ym1lS1KDvfAiHZhLT%2BNT1dLSov2czROujJVj" +
                "%2FAD4vhHRYFLjSe6RvAl9n2KCdIlpBUjkcis1nRwTLnu2FG3wt8yGHyt43PxuUmU0Bqk8sq1q6X60f8sAYt2IAVjF0JNragbrhvNoDxZzAo3Eg8HpMgdzrDYCtSOuq7sLWPtkJ0KPwLthfp6nUJi8UeYw6hB8MklSZ8qb4BkbnQqA07VL1IZHeoJml0ziSRwbq656druCpMcGaZ9hQjTWugvlPFfBv79JjWdo4XlLk29VRmUmhy%2BG%2FHVVDGdoPo7ip4qIs5nDAFGRRRPMZ7ayw%3D%3D");
        enter.setConfirmPassword("Hbr%2F803ozulFPdiLAuuDJuvrleiBlyiXjdNdA7q0B6yrrPDK%2FKw3KtKTwymLHNq5SUItmW6oJ1sySL6Knnr8rxQguvM%2B4rQMHssA8Bha8UbRg%2BkHbMi" +
                "%2FvVUETKa5hRgq1na5OsMTby4o4OtJuxAtQbAhis2jt3lxDrYybjjSsLstzG7%2BRgLiIksQuIPdlB" +
                "%2BDathoOuH2OOo2uFbIiyLtHt2Fglm9E5z5rTlSu1jBo0wE08Mvy5qFXdI47AVkt9y6DM5QUdDk2Edpg5CUBpBiPvpCTMi3ZNqWZvC%2BtJ%2BNlrnD66dJJbwzQoFbVErZBlbRXtsarNtNl0ysu9oOEnDrXQ%3D%3D");
        enter.setOldPassword("fmWv3FpZb4uZj1l8DwBr6qlMBmr942Ye3RVvY%2FnBxQFdtml0645dgMp3E8T0uXAevVbmHwxHKKhVV9N%2BMrM7yhLS40SG5MBMl6Oz3qHasAkGfCb4g4gbX6%2FnhRebOGb%2BZ%2FDduxYtbjdP%2BVaSKGj%2Frcwr" +
                "%2FPQX%2B%2Fji7PWCWj4YAMIT%2B%2BPw2kA2hwfGOQFLLrWHUKKWbWa3UiHD" +
                "%2Bm1XBD3veXxwvnDhRo2OVravaxP4EP6wdcZqNxtqIZJptgA13AH2BWWamHZWdA9jrg57PK0mOltp5X75M03xM5eIHGRuUL1ZyunizTSW76LcB366sYD9MAmWSQez4LxXQZFz%2BSGfBQ%3D%3D");
        webSiteService.resetPassword(enter);
    }

    @Test
    public void editCustomer() {
        WebEditCustomerEnter enter = new WebEditCustomerEnter();
        enter.setAddress("");
        enter.setFirstName("");
        enter.setLastName("");
        webSiteService.editCustomer(enter);
    }

    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
        System.setProperty("jasypt.encryptor.password", "RedE");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//        System.out.println("加密后的登录名： " + stringEncryptor.encrypt("root"));
//        System.out.println("加密后的密码： " + stringEncryptor.encrypt("1qaz2wsx"));
        System.out.println("consumer： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/consumer_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("scooter： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/scooter_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("platform： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/platform_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("corporate： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/corporate_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("operation： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/operation_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        ////
//
//        String url="+chN0fRMdoeLUWSKhrK2wbRtCS9HtHdgHojKHlPjwRbbf/NC9cLGUJGNbS8KTloYlKi43+n79TiDla0aWszCT
//        +pDuCXOcFQy3ycbBJXN4wqwDF5HVL1PObebjlCR29Q7bUW10W7cFdrrjhZN90u0zEtEYh1qzD7rW1rw8uUnFSuF69w9M3et6tHJYU2MpOS/jl/1u6e
//        /d3uhRRp225KLSZMS6PrR3EnsNfW6HQYByeVM3eZLZlBDUkebkhxKcU8tV1Qz4xSJbl9rNA1jqwFAIxhsFFI+uuLfiEe4u0/5DDewWR1NtvzOTEKKKlM1f9VyMDwwECar1IbYORA72Yik0Q==";
////
//        String userName="vWqurNhz5ZyGRrJm4tjVnA==";
////
//        String password="Q/B0gpzvU8K2HB+l+DVlQn1L/n3F2RZ8gSpIZT4CkvqqOg9GfxXs+Ouj4IJSNXrf";
////
//        System.out.println("解密的登录名： " + stringEncryptor.decrypt(userName));
//        System.out.println("解密的密码： " + stringEncryptor.decrypt(password));
//        System.out.println("url： " + stringEncryptor.decrypt(url));
    }

    @Test
    public void os() {
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        if (osName.startsWith("Mac OS")) {
            // 苹果
            System.out.println("This is Mac");
        } else if (osName.startsWith("Windows")) {
            // windows
            System.out.println("This is windows");
        } else {
            // unix or linux
            System.out.println("This is unix or linux");
        }
    }

    @Test
    public void stest() {
        String str = "sdasda";
//        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        System.out.println(m.matches());
//
////        Pattern p1 = Pattern.compile(RegexpConstant.specialCharacters);
////        Matcher m2 = p.matcher(str);
//        System.out.println(Pattern.matches(RegexpConstant.specialCharacters, str));

        String regEx = "^((?![`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]).)*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        System.out.println(m.matches());
    }

    @Test
    public void createDocument() {
        //查询发票排版
        List<SellsyLayoutResult> sellsyLayoutResults = sellsyAccountSettingService.queryDocLayoutList();
        if (CollectionUtils.isEmpty(sellsyLayoutResults)) {
            log.info("----------------布局出错------------");
            throw new RuntimeException();
        }
        List<SellsyTranslationLanguageResult> sellsyTranslationLanguageResults = sellsyAccountSettingService.queryTranslationLanguages();
        if (CollectionUtils.isEmpty(sellsyTranslationLanguageResults)) {
            log.info("----------------语言出错------------");
            throw new RuntimeException();
        }

        List<SellsyCurrencyResult> sellsyCurrencyResults = sellsyAccountSettingService.queryCurrencyList();
        if (CollectionUtils.isEmpty(sellsyCurrencyResults)) {
            log.info("----------------货币单位出错------------");
            throw new RuntimeException();
        }

        List<SellsyClientResult> sellsyClientResults = sellsyClientService.queryClientList(new SellsyClientListEnter());
        if (CollectionUtils.isEmpty(sellsyClientResults)) {
            log.info("----------------客户出错------------");
            throw new RuntimeException();
        }

        //查询个人信息
        SellsyCorpInfoResult sellsyCorpInfoResult = sellsyAccountSettingService.queryCorpInfos();
        if (sellsyCorpInfoResult == null) {
            log.info("----------------公司出错------------");
            throw new RuntimeException();
        }

        //查询税率
        List<SellsyRateCategoryResult> sellsyRateCategoryResults = sellsyAccountSettingService.queryRateCategoryList();
        if (CollectionUtils.isEmpty(sellsyRateCategoryResults)) {
            log.info("----------------税局出错------------");
            throw new RuntimeException();
        }
        //查询 税率
        List<SellsyTaxeResult> sellsyTaxeResults = sellsyAccountSettingService.queryTaxeList();
        if (CollectionUtils.isEmpty(sellsyTaxeResults)) {
            log.info("----------------税率出错------------");
            throw new RuntimeException();
        }

        List<SellsyCatalogueResult> sellsyCatalogueResultList = sellsyCatalogueService.queryCatalogueList(new SellsyCatalogueListEnter(SellsyCatalogueTypeEnums.item, null, null));
        if (CollectionUtils.isEmpty(sellsyCatalogueResultList)) {
            log.info("----------------产品出错------------");
            throw new RuntimeException();
        }

        SellsyRowEnter sellsyRowEnter = SellsyRowEnter.builder()
                .row_type(SellsyDocumentRosTypeEnums.ITEM.getValue())
                .row_name(sellsyCatalogueResultList.get(0).getName())
                .row_taxid(Integer.valueOf(sellsyTaxeResults.get(0).getId()))
                .row_tax2id(null)
                .row_qt(10)
                .row_isOption(SellsyBooleanEnums.N)
                .row_unitAmount(sellsyCatalogueResultList.get(0).getUnitAmount())
                .row_discount(null)
                .row_discountUnit(SellsyGlobalDiscountUnitEnums.percent)
                .row_linkedid(Integer.valueOf(sellsyCatalogueResultList.get(0).getId()))
                .row_declid(Integer.valueOf(sellsyCatalogueResultList.get(0).getDeclid()))
                .row_notes(null)
                .row_whid(null)
                .row_purchaseAmount(String.valueOf(Double.valueOf(sellsyCatalogueResultList.get(0).getUnitAmount()) * 10))
                .row_serial(null)
                .row_barcode(null)
                .row_title(null)
                .row_comment(null)
                .row_unit(null)
                .build();


        SellsyClientServiceCreateDocumentEnter sellsyClientServiceCreateDocumentEnter = new SellsyClientServiceCreateDocumentEnter();
        sellsyClientServiceCreateDocumentEnter.setDoctype(SellsyDocmentTypeEnums.invoice);
        sellsyClientServiceCreateDocumentEnter.setThirdid(Integer.valueOf(sellsyClientResults.get(1).getId()));
        sellsyClientServiceCreateDocumentEnter.setIdent("BRO00001831");
        sellsyClientServiceCreateDocumentEnter.setSubject("RedE Group Scooter");
        sellsyClientServiceCreateDocumentEnter.setNotes(null);
        sellsyClientServiceCreateDocumentEnter.setDisplayShipAddress(SellsyBooleanEnums.Y);
        sellsyClientServiceCreateDocumentEnter.setRateCategory(Integer.valueOf(sellsyRateCategoryResults.get(0).getId()));
        sellsyClientServiceCreateDocumentEnter.setGlobalDiscount(0);
        sellsyClientServiceCreateDocumentEnter.setGlobalDiscountUnit(SellsyGlobalDiscountUnitEnums.percent);
        sellsyClientServiceCreateDocumentEnter.setCurrency(1);
        sellsyClientServiceCreateDocumentEnter.setDoclayout(Integer.valueOf(sellsyLayoutResults.get(1).getId()));
        sellsyClientServiceCreateDocumentEnter.setDoclang(Integer.valueOf(sellsyTranslationLanguageResults.get(0).getId()));
        sellsyClientServiceCreateDocumentEnter.setShowContactOnPdf(SellsyBooleanEnums.Y);
        sellsyClientServiceCreateDocumentEnter.setCorpAddressId(Integer.parseInt(sellsyCorpInfoResult.getMainaddressid()));
        sellsyClientServiceCreateDocumentEnter.setThirdaddress(new SellsyIdEnter(Integer.valueOf(sellsyCorpInfoResult.getMainaddressid())));
        //sellsyClientServiceCreateDocumentEnter.setSellsellEnter(sellsyRowEnter);
        SellsyIdResult document = sellsyDocumentService.createDocument(sellsyClientServiceCreateDocumentEnter);

        log.info("------------创建发票的返回值 {}--------", document.toString());
    }

    @Test
    public void updateDocumentStatus() {
        SellsyUpdateDocumentStatusEnter build = SellsyUpdateDocumentStatusEnter
                .builder()
                .docid(19841560)
                .document(new SellsyUpdateDocumentInvoidSatusEnter(SellsyDocmentTypeEnums.invoice, SellsyDocumentInvoiceStatusEnums.cancelled))
                .build();
        sellsyDocumentService.upateDocumentStatus(build);

    }

    @Test
    public void createDocumentList() {
        sellsyDocumentService.createDcumentList();
    }

    @Autowired
    private SellsyConfig sellsyConfig;

    @Test
    public void  createCatalogue(){

        //List<SellsyProduct> sellsyProductList = sellsyProductService.list(new LambdaQueryWrapper<SellsyProduct>().in(SellsyProduct::getStatus,"1","3"));


        //查询计量单位
        List<SellsyUnitResult> sellsyUnitResultList = sellsyAccountSettingService.queryUnitList();
        if (CollectionUtils.isEmpty(sellsyUnitResultList)){
            throw new SesWebRosException();
        }
        SellsyUnitResult sellsyUnitResult = sellsyUnitResultList.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(sellsyConfig.getUnit()))).findFirst().orElse(null);
        if (sellsyUnitResult==null){
            throw new SesWebRosException();
        }
        //查询税率
        List<SellsyTaxeResult> sellsyTaxeResults = sellsyAccountSettingService.queryTaxeList();
        if (CollectionUtils.isEmpty(sellsyTaxeResults)) {
            throw new SesWebRosException();
        }

        SellsyTaxeResult sellsyTaxeResult = sellsyTaxeResults.stream().filter(item -> StringUtils.equals(item.getId(), String.valueOf(sellsyConfig.getTaxId()))).findFirst().orElse(null);
        if (sellsyTaxeResult == null) {
            throw new SesWebRosException();
        }
        //数据封装
        SellsyCreateCatalogueEnter sellsyCreateCatalogueEnter = new SellsyCreateCatalogueEnter();
        SellsyCreateCatalogueTypeEnter catalogueTypeEnter = new SellsyCreateCatalogueTypeEnter();
        catalogueTypeEnter.setName("RED1000001");
        catalogueTypeEnter.setTradename("Electric motorcycles and accessories");
        catalogueTypeEnter.setTradenametonote(SellsyBooleanEnums.Y);
        catalogueTypeEnter.setNotes("自定义添加可删除");
        catalogueTypeEnter.setTags(null);
        catalogueTypeEnter.setUnitAmount(0);
        catalogueTypeEnter.setUnit(sellsyUnitResult.getValue());
        catalogueTypeEnter.setQty(1);
        catalogueTypeEnter.setUnitAmountIsTaxesFree(SellsyBooleanEnums.Y);
        catalogueTypeEnter.setTaxid(Integer.valueOf(sellsyTaxeResult.getId()));
        catalogueTypeEnter.setTaxrate(Float.valueOf(sellsyTaxeResult.getValue()));
        sellsyCreateCatalogueEnter.setType(SellsyCatalogueTypeEnums.item);
        sellsyCreateCatalogueEnter.setItem(catalogueTypeEnter);
        SellsyIdResult catalogue = sellsyCatalogueService.createCatalogue(sellsyCreateCatalogueEnter);

        SellsyProduct sellsyProduct = SellsyProduct
                .builder()
                .id(idAppService.getId("SellsyProduct"))
                .dr(0)
                .productName(catalogueTypeEnter.getTradename())
                .productCode(catalogueTypeEnter.getName())
                .status("4")
                .productPrice("0")
                .remark(catalogueTypeEnter.getNotes())
                .def1(String.valueOf(catalogue.getId()))
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();

        sellsyProductService.save(sellsyProduct);
    }


    @Test
    public void deleteCatalogue(){
        List<SellsyProduct> sellsyProductList = sellsyProductService.list(new LambdaQueryWrapper<SellsyProduct>().orderByDesc(SellsyProduct::getId));
        //List<SellsyProduct> sellsyProductList = sellsyProductService.list(new LambdaQueryWrapper<SellsyProduct>().eq(SellsyProduct::getId,1000021));

        SellsyCatalogueListEnter sellsyCatalogueListEnter = new SellsyCatalogueListEnter();
        SellsyCatalogueListSearchEnter sellsyCatalogueListSearchEnter =
                new SellsyCatalogueListSearchEnter();
        sellsyCatalogueListEnter.setType(SellsyCatalogueTypeEnums.item);
        for (SellsyProduct item : sellsyProductList) {
            if (StringUtils.isEmpty(item.getReplaceProductCode())){
                sellsyCatalogueListSearchEnter.setName(item.getProductCode());
            }else {
                sellsyCatalogueListSearchEnter.setName(item.getReplaceProductCode());
            }
            sellsyCatalogueListEnter.setSearch(sellsyCatalogueListSearchEnter);

            List<SellsyCatalogueResult> sellsyCatalogueResults =
                    sellsyCatalogueService.queryCatalogueList(sellsyCatalogueListEnter);
            if (CollectionUtils.isEmpty(sellsyCatalogueResults)) {
                continue;
            }
            SellsyDeleteCatalogueEnter sellsyDeleteCatalogueEnter = new SellsyDeleteCatalogueEnter();

            //过滤
            for (SellsyCatalogueResult catalogue : sellsyCatalogueResults) {
                if (StringUtils.equals(catalogue.getName(), sellsyCatalogueListSearchEnter.getName().trim())) {
                    sellsyDeleteCatalogueEnter.setId(Integer.valueOf(catalogue.getId()));
                    break;
                }
            }
            if (sellsyDeleteCatalogueEnter.getId()==null){
                continue;
            }
            sellsyDeleteCatalogueEnter.setType(SellsyCatalogueTypeEnums.item);
            sellsyCatalogueService.deleteCatalogue(sellsyDeleteCatalogueEnter);
            log.info("----------执行了--------");
        }
    }

    @Test
    public  void btachDeleteProduct(){
        for (int i = 0; i < 10; i++) {
            deleteCatalogue();
        }
    }

    @Test
    public void  createclient(){
        SellsyCreateClientThirdEnter third = new SellsyCreateClientThirdEnter();
        third.setName("alex");
        third.setType(SellsyClientTypeEnums.corporation.getValue());
        third.setEmail("alex@redescooter.com");


        SellsyCreateClientAddressEnter address = SellsyCreateClientAddressEnter
                .builder()
                .name("中国上海")
                .part1("中国上海")
                .build();

        SellsyCreateClientEnter sellsyCreateClientEnter = SellsyCreateClientEnter.builder()
                .third(third)
                .address(address)
                .contact(null)
                .build();
        SellsyIdResult client = sellsyClientService.createClient(sellsyCreateClientEnter);
        log.info("-----------取到返回值{}---------", client.getId());
    }

    @Autowired
    private SellsyCustomerService sellsyCustomerService;

    @Test
    public void deleteclient() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(SellsyCustomer.COL_DEF2, "1");
        List<SellsyCustomer> list = sellsyCustomerService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(item -> {
            SellsyDeleteClientEnter sellsyDeleteClientEnter = SellsyDeleteClientEnter.builder()
                    .clientid(item.getDef2())
                    .build();
            sellsyClientService.deleteClient(sellsyDeleteClientEnter);
        });

        //SellsyGeneralResult(error=null, status=null, result={"client_id":25918711}, sellsyResponseInfo=null)
    }

    /**
     * 发票批量创建
     */
    @Test
    public void DocumentCreateTotal() {
        for (int i = 0; i < 10; i++) {
            try {
                List<SellsyIdResult> dcumentTotalList =
                        sellsyDocumentService.createDcumentTotalList(null);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("--------------出错了第{}重试----------", i);
            }
        }


        log.info("--------------执行成功---------");
    }

    /**
     * 批量上传发票
     */
    @Test
    public void docuemntUploadFile() {
        QueryWrapper sellsyInvoiceWrapper = new QueryWrapper();
        sellsyInvoiceWrapper.eq(SellsyInvoiceTotal.COL_DEF1, SellsyBooleanEnums.Y);
        sellsyInvoiceWrapper.isNull(SellsyInvoiceTotal.COL_DEF3);
        List<SellsyInvoiceTotal> sellsyInvoiceTotalList = sellsyInvoiceTotalService.list();

        if (CollectionUtils.isEmpty(sellsyInvoiceTotalList)) {
            return;
        }

        List<SellsyInvoiceTotal> updateSellsyTotalList = new ArrayList<>();

        for (SellsyInvoiceTotal item : sellsyInvoiceTotalList) {
            if (StringUtils.isEmpty(item.getDef5())) {
                continue;
            }
            SellsyBriefcasesUploadFileEnter enter =
                    SellsyBriefcasesUploadFileEnter
                            .builder()
                            .linkedtype(SellsyBriefcaseTypeEnums.invoice)
                            .linkedid(Integer.valueOf(item.getDef2()))
                            .fileUrl(item.getDef5())
                            .build();
            SellsyBriefcaseUploadFileResult sellsyBriefcaseUploadFileResult = briefcasesService.briefcasesUploadFile(enter, null);
            item.setDef3(String.valueOf(SellsyBooleanEnums.Y));
            item.setDef6(Double.valueOf(sellsyBriefcaseUploadFileResult.getFile_id()));
            //pdateSellsyTotalList.add(item);
            sellsyInvoiceTotalService.updateById(item);
        }
        if (CollectionUtils.isNotEmpty(updateSellsyTotalList)) {
            sellsyInvoiceTotalService.updateBatch(updateSellsyTotalList);
        }
    }

    @Test
    public void updaloadPciture() {
        SellsyBriefcasesUploadFileEnter enter =
                SellsyBriefcasesUploadFileEnter
                        .builder()
                        .linkedtype(SellsyBriefcaseTypeEnums.invoice)
                        .linkedid(Integer.valueOf(20111882))
                        .fileUrl("https://rede.oss-cn-shanghai.aliyuncs.com/invoice/16004095725194269.pdf")
                        .build();
        SellsyBriefcaseUploadFileResult sellsyBriefcaseUploadFileResult = briefcasesService.briefcasesUploadFile(enter, null);
        log.info(sellsyBriefcaseUploadFileResult);
    }

    @Test
    public void createPayment() {
        SellsyDocumentPaymentEnter documentPaymentEnter = SellsyDocumentPaymentEnter
                .builder()
                .date(new Timestamp(new Date().getTime() / 1000))
                .amount("372")
                .medium(3497466)
                .ident("FAC00001444")
                .notes("测试")
                .doctype(SellsyDocmentTypeEnums.invoice.getCode())
                .docid("20109028")
                .build();
        sellsyDocumentService.createDocumentPayment(new SellsyCreateDocumentPaymentEnter(documentPaymentEnter));
    }

    @Test
    public void deletePayment() {
        SellsyDeletePaymentEnter deletePaymentEnter = SellsyDeletePaymentEnter
                .builder()
                .docid(20109028)
                .doctype(SellsyDocmentTypeEnums.invoice.getCode())
                .payid(10173692)
                .build();
        sellsyDocumentService.deleteDocumentPayment(new SellsyDeleteDocumentPaymentEnter(deletePaymentEnter));
    }

    @Test
    public void queryPayment() {
        SellsyQueryDocumentPaymentEnter deletePaymentEnter = SellsyQueryDocumentPaymentEnter
                .builder()
                .docid(20109028)
                .doctype(SellsyDocmentTypeEnums.invoice.getCode())
                .payid(10173692)
                .build();
        sellsyDocumentService.queryDocumentPaymentOne(deletePaymentEnter);
    }
}

