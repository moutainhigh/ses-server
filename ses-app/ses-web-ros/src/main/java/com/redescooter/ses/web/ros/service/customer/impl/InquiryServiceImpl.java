package com.redescooter.ses.web.ros.service.customer.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerAccountFlagEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.UpdateInfoResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.starter.common.config.OssConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.InquiryServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.web.ros.dao.base.OpePartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSalePartsMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSalePriceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeSaleParts;
import com.redescooter.ses.web.ros.dm.OpeSalePrice;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryExportResult;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayGeneralEnter;
import com.redescooter.ses.web.ros.vo.website.SaveAboutUsEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:InquiryServiceImpl
 * @description: InquiryServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/03/06 10:27
 */
@Slf4j
@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeSalePriceMapper opeSalePriceMapper;

    @Autowired
    private OpeProductionPartsMapper opeProductionPartsMapper;

    @Autowired
    private OpeSalePartsMapper opeSalePartsMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private InquiryServiceMapper inquiryServiceMapper;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private CityBaseService cityBaseService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeSpecificatTypeMapper opeSpecificatTypeMapper;

    @Autowired
    private OpeColorMapper opeColorMapper;

    @Autowired
    private OpeCustomerInquiryBMapper opeCustomerInquiryBMapper;

    @Value("${Request.privateKey}")
    private String privateKey;

    @Value("${excel.folder}")
    private String excelFolder;

    @Autowired
    private MondayService mondayService;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private ContactUsService contactUsService;

    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> statusResultList = inquiryServiceMapper.countStatus();
        Map<String, Integer> map = new HashMap<String, Integer>();
        statusResultList.forEach(item -> {
            map.put(item.getStatus(), item.getTotalCount());
        });

        for (InquiryStatusEnums item : InquiryStatusEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
//        map.remove(InquiryStatusEnums.PROCESSED.getValue());
//        map.remove(InquiryStatusEnums.PAY_LAST_PARAGRAPH.getValue());
        return map;
    }

    /**
     * @param saveAboutUsEnter
     * @desc: ???????????????
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAboutUs(SaveAboutUsEnter saveAboutUsEnter) {
        //???????????????
        SaveAboutUsEnter enter = SesStringUtils.objStringTrim(saveAboutUsEnter);
        //????????????
        //????????????
        if (!StringUtils.isAllBlank(enter.getTelephone(), enter.getEmail())) {
            try {
                enter.setEmail(RsaUtils.decrypt(enter.getEmail(), privateKey));
                enter.setTelephone(RsaUtils.decrypt(enter.getTelephone(), privateKey));
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            if (NumberUtil.ltTwoOrGtFifty(enter.getEmail().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
            if (NumberUtil.ltTwoOrGtTen(enter.getTelephone().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
            //??????
            enter.setTelephone(SesStringUtils.stringTrim(enter.getTelephone()));
            //?????? ?????????
            enter.setEmail(SesStringUtils.stringTrim(enter.getEmail()));
        }

        // ??????????????????email ??????????????? ????????????
        //List<String> emailList = inquiryServiceMapper.usingEmailList();

        //???????????????????????????????????????????????? ?????????false  ????????????
        if (!enter.getWhetherConstantUs()) {
            //?????? ????????? ????????????????????? ????????? ????????????
            OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().in(OpeCustomer::getStatus, CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue(),
                    CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue()).eq(OpeCustomer::getEmail,
                    enter.getEmail()));
            if (StringManaConstant.entityIsNotNull(opeCustomer)) {
                return new GeneralResult(enter.getRequestId());
            }
        }
        // ??????????????????
        contactUsService.websiteContactUs(enter);

        //Monday ????????????
        MondayGeneralEnter mondayGeneralEnter = new MondayGeneralEnter();
        mondayGeneralEnter.setFirstName(enter.getFirstName());
        mondayGeneralEnter.setLastName(enter.getLastName());
        mondayGeneralEnter.setTelephone(enter.getTelephone());
        mondayGeneralEnter.setCreatedTime(new Date());
        mondayGeneralEnter.setUpdatedTime(new Date());
        mondayGeneralEnter.setEmail(enter.getEmail());
        mondayGeneralEnter.setCity(enter.getCity());
        mondayGeneralEnter.setDistant(enter.getDistrust());
        mondayGeneralEnter.setRemarks(enter.getRemark());
        mondayGeneralEnter.setAddress(enter.getAddress());
        mondayService.websiteContantUs(mondayGeneralEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: ???????????????
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:44
     * @Version: Ros 1.3
     */
    @Override
    public PageResult<InquiryResult> inquiryList(InquiryListEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        int count = inquiryServiceMapper.inquiryListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryResult> inquiryResultList = inquiryServiceMapper.inquiryList(enter);
//        inquiryResultList.forEach(item -> {
//            String city = null;
//            String distrust = null;
//            if (item.getCityId() != null && item.getCityId() != 0) {
//                city = cityBaseService.queryCityDeatliById(new IdEnter(item.getCityId())).getName();
//            }
//            if (item.getDistrustId() != null && item.getDistrustId() != 0) {
//                distrust = cityBaseService.queryCityDeatliById(new IdEnter(item.getDistrustId())).getName();
//            }
//            item.setCityName(city);
//            item.setDistrustName(distrust);
//        });
        return PageResult.create(enter, count, inquiryResultList);
    }

    /**
     * @param enter
     * @desc: ???????????????
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:45
     * @Version: Ros 1.3
     */
    @Override
    public InquiryResult inquiryDetail(IdEnter enter) {
        InquiryResult inquiryResult = inquiryServiceMapper.inquiryDetail(enter);
        if (StringManaConstant.entityIsNull(inquiryResult)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }

//        String city = null;
//        String distrust = null;
//        if (inquiryResult.getCityId() != null && inquiryResult.getCityId() != 0) {
//            city = cityBaseService.queryCityDeatliById(new IdEnter(inquiryResult.getCityId())).getName();
//        }
//        if (inquiryResult.getDistrustId() != null && inquiryResult.getDistrustId() != 0) {
//            distrust = cityBaseService.queryCityDeatliById(new IdEnter(inquiryResult.getDistrustId())).getName();
//        }
//        inquiryResult.setCityName(city);
//        inquiryResult.setDistrustName(distrust);

        if (StringUtils.equals(inquiryResult.getStatus(), InquiryStatusEnums.UNPAY_DEPOSIT.getValue()) || StringUtils.equals(inquiryResult.getStatus(), InquiryStatusEnums.PAY_DEPOSIT.getValue())) {
            //????????????????????????????????????
            Boolean exists = jedisCluster.exists(new StringBuffer(inquiryResult.getId().toString()).append("send::").append(inquiryResult.getEmail()).toString());
            if (exists) {
                Long ttl = jedisCluster.ttl(new StringBuffer(inquiryResult.getId().toString()).append("send::").append(inquiryResult.getEmail()).toString());
                inquiryResult.setTtl(ttl);
            } else {
                inquiryResult.setTtl(new Long(0));
            }
        }
        return inquiryResult;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult depositPaymentEmail(IdEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getId, enter.getId())
                .last("limit 1"));
        //???????????????
        if (StringManaConstant.entityIsNull(opeCustomerInquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.UNPAY_DEPOSIT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        // ??????????????????
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(opeCustomerInquiry.getFullName());
        baseMailTaskEnter.setToMail(opeCustomerInquiry.getEmail());
        baseMailTaskEnter.setToUserId(opeCustomerInquiry.getCustomerId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        //????????????????????????
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.CUSTOMER_INQUIRY_PAY_DEPOSIT.getEvent());
        baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_APP.getValue());
        baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        mailMultiTaskService.addCustomerInquiryPayDepositTask(baseMailTaskEnter);

        //??????????????????????????????
        String key = new StringBuffer(opeCustomerInquiry.getId().toString()).append("send::").append(opeCustomerInquiry.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult lastParagraphEmail(IdEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getId, enter.getId())
                .last("limit 1"));
        //???????????????
        if (StringManaConstant.entityIsNull(opeCustomerInquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.PAY_DEPOSIT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        // ??????????????????
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(opeCustomerInquiry.getFullName());
        baseMailTaskEnter.setToMail(opeCustomerInquiry.getEmail());
        baseMailTaskEnter.setToUserId(opeCustomerInquiry.getCustomerId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        //????????????????????????
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.CUSTOMER_INQUIRY_LAST_PARAGRAPH.getEvent());
        baseMailTaskEnter.setMailAppId(AppIDEnums.SES_ROS.getValue());
        baseMailTaskEnter.setMailSystemId(AppIDEnums.SES_ROS.getSystemId());
        mailMultiTaskService.addCustomerInquiryPayLastParagraphTask(baseMailTaskEnter);

        //??????????????????????????????
        String key = new StringBuffer(opeCustomerInquiry.getId().toString()).append("send::").append(opeCustomerInquiry.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????? ?????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult acceptInquiry(IdEnter enter) {
        QueryWrapper<OpeCustomerInquiry> opeCustomerInquiryQueryWrapper = new QueryWrapper<>();
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_DR, Constant.DR_FALSE);
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_ID, enter.getId());
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_SOURCE, InquirySourceEnums.INQUIRY.getValue());
        opeCustomerInquiryQueryWrapper.last("limit 1");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(opeCustomerInquiryQueryWrapper);
        if (StringManaConstant.entityIsNull(opeCustomerInquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        //????????????????????? ????????????
        if (SesStringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.PROCESSED.getValue())) {
            return new GeneralResult();
        }
        opeCustomerInquiry.setStatus(InquiryStatusEnums.PROCESSED.getValue());
        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        opeCustomerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.updateById(opeCustomerInquiry);

        // ??????????????????
        OpeCustomer opeCustomer = buildOpeCustomerSingle(enter, opeCustomerInquiry);
        opeCustomerService.insertOrUpdateSelective(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult declineInquiry(IdEnter enter) {
        QueryWrapper<OpeCustomerInquiry> opeCustomerInquiryQueryWrapper = new QueryWrapper<>();
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_DR, Constant.DR_FALSE);
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_ID, enter.getId());
        opeCustomerInquiryQueryWrapper.last("limit 1");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(opeCustomerInquiryQueryWrapper);
        if (StringManaConstant.entityIsNull(opeCustomerInquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!SesStringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.UNPROCESSED.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        opeCustomerInquiry.setStatus(InquiryStatusEnums.DECLINE.getValue());
        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        opeCustomerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.updateById(opeCustomerInquiry);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult inquiryExport(InquiryListEnter enter) {
        String excelPath = "";
        List<InquiryExportResult> list = inquiryServiceMapper.exportInquiry(enter);
        log.info("?????????????????????" + list.size());
        List<Map<String, Object>> dataMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Integer i = 1;
            for (InquiryExportResult inquiry : list) {
                inquiry.setCreatedTime(DateUtil.dateAddHour(inquiry.getCreatedTime(), 8));
                dataMap.add(toMap(inquiry, i));
                i++;
            }
            String sheetName = "Inquiry";
            String[] headers = {"ID", "fullName", "email", "bankCardname", "district", "address", "productName", "color", "batteryQty", "amountObligation", "amountPaid", "totalPrice", "time"};
            String exportExcelName = String.valueOf(System.currentTimeMillis());
            try {
                String path = ExcelUtil.exportExcel(sheetName, dataMap, headers, exportExcelName, excelFolder);
                /**????????????????????????????????????**/
                if (!new File(excelFolder).exists()) {
                    new File(excelFolder).mkdir();
                }
                File file = new File(path);
                FileInputStream inputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                        ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

                ClientConfiguration conf = new ClientConfiguration();
                conf.setProtocol(ProtocolEnums.getProtocol(ossConfig.getProtocol()));
                OSSClient ossClient = null;
                ossClient = new OSSClient(ossConfig.getInternalEndpoint(), ossConfig.getAccessKeyId(),
                        ossConfig.getSecretAccesskey(), conf);
                String fileName = System.currentTimeMillis() + ".xlsx";
                ossClient.putObject(ossConfig.getDefaultBucketName(), fileName,
                        multipartFile.getInputStream());
                String bucket = ossConfig.getDefaultBucketName();
                excelPath = "https://" + bucket + "." + ossConfig.getPublicEndpointDomain() + "/" + fileName;
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                log.info("???????????????????????????");
            }
        }
        return new GeneralResult(excelPath);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateSaleOrder(UpdateInfoResult enter) {
        // ???????????????????????????????????? E50:1~4 E100:2~4 E125:4
        OpeSpecificatType type = opeSpecificatTypeMapper.selectById(enter.getSpecificatTypeId());
        Integer qty = enter.getProductQty();
        if (StringManaConstant.entityIsNotNull(type)) {
            String specificatName = type.getSpecificatName();
            if ("E50".equals(specificatName)) {
                if (4 < qty) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }
            } else if ("E100".equals(specificatName)) {
                if (2 > qty) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }
            } else if ("E125".equals(specificatName)) {
                if (4 > qty) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }
            }
        }

        QueryWrapper<OpeSaleScooter> wrapper = new QueryWrapper<OpeSaleScooter>()
                .eq(OpeSaleScooter.COL_COLOR_ID, enter.getColorId())
                .eq(OpeSaleScooter.COL_SPECIFICAT_ID, enter.getSpecificatTypeId())
                .eq(OpeSaleScooter.COL_SALE_STUTAS, 1)
                .last("limit 1");
        OpeSaleScooter scooter = opeSaleScooterMapper.selectOne(wrapper);
        Long productId = scooter.getId();

        OpeCustomerInquiry inquiry = opeCustomerInquiryService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
        }
        LambdaQueryWrapper<OpeSalePrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeSalePrice::getDr, Constant.DR_FALSE);
        queryWrapper.eq(OpeSalePrice::getType, 2);
        queryWrapper.like(OpeSalePrice::getScooterBattery, scooter.getProductCode());
        queryWrapper.last("limit 1");
        OpeSalePrice opeSalePrice = opeSalePriceMapper.selectOne(queryWrapper);
        if (null == opeSalePrice) {
            throw new SesWebRosException(ExceptionCodeEnums.SALE_PRICE_NOT_FOUND.getCode(), ExceptionCodeEnums.SALE_PRICE_NOT_FOUND.getMessage());
        }
        LambdaQueryWrapper<OpeProductionParts> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(OpeProductionParts::getDr, Constant.DR_FALSE);
        wrapper1.eq(OpeProductionParts::getPartsType, 3);
        wrapper1.last("limit 1");
        OpeProductionParts opeProductionParts = opeProductionPartsMapper.selectOne(wrapper1);
        if (null == opeProductionParts) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        LambdaQueryWrapper<OpeSaleParts> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(OpeSaleParts::getDr, Constant.DR_FALSE);
        wrapper2.eq(OpeSaleParts::getPartsId, opeProductionParts.getId());
        wrapper2.last("limit 1");
        OpeSaleParts opeSaleParts = opeSalePartsMapper.selectOne(wrapper2);
        if (null == opeSaleParts){
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        int battery = enter.getProductQty() - scooter.getMinBatteryNum();

        BigDecimal amount = opeSalePrice.getDeposit().add(opeSalePrice.getBalance()).add(new BigDecimal(battery).multiply(opeSaleParts.getPrice()));

        inquiry.setTotalPrice(amount);
        inquiry.setProductPrice(amount);
        inquiry.setAmountObligation(amount);
        inquiry.setProductId(productId);
        opeCustomerInquiryService.updateById(inquiry);


        List<OpeCustomerInquiryB> inquiryBList = opeCustomerInquiryBMapper.selectList(new QueryWrapper<OpeCustomerInquiryB>().eq("inquiry_id", inquiry.getId()));
        if (CollectionUtils.isEmpty(inquiryBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        for (OpeCustomerInquiryB ope : inquiryBList) {
            ope.setProductQty(enter.getProductQty());
            ope.setProductPrice(amount);
        }

        opeCustomerInquiryBMapper.updateBatch(inquiryBList);
        return new GeneralResult(enter.getRequestId());
    }


    private Map<String, Object> toMap(InquiryExportResult opeCustomerInquiry, Integer i) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ID", i);
        map.put("fullName", opeCustomerInquiry.getCustomerFullName() == null ? "--" : opeCustomerInquiry.getCustomerFullName());
        map.put("email", opeCustomerInquiry.getEmail() == null ? "--" : opeCustomerInquiry.getEmail());
        map.put("bankCardname", opeCustomerInquiry.getBankCardName() == null ? "--" : opeCustomerInquiry.getBankCardName());
        map.put("district", opeCustomerInquiry.getPostcode() == null ? "--" : opeCustomerInquiry.getPostcode());
        map.put("address", opeCustomerInquiry.getAddress() == null ? "--" : opeCustomerInquiry.getAddress());
        map.put("productName", opeCustomerInquiry.getProductName() == null ? "--" : opeCustomerInquiry.getProductName());
        map.put("color", opeCustomerInquiry.getColorName() == null ? "--" : opeCustomerInquiry.getColorName());
        map.put("batteryQty", opeCustomerInquiry.getBatteryQty() == null ? 0 : opeCustomerInquiry.getBatteryQty());
        map.put("amountObligation", opeCustomerInquiry.getBalance() == null ? 0.00 : opeCustomerInquiry.getBalance());
        map.put("amountPaid", opeCustomerInquiry.getAmountPaid() == null ? 0.00 : opeCustomerInquiry.getAmountPaid());
        map.put("totalPrice", opeCustomerInquiry.getTotalPrice() == null ? 0.00 : opeCustomerInquiry.getTotalPrice());
        map.put("time", opeCustomerInquiry.getCreatedTime() == null ? "--" : DateUtil.format(opeCustomerInquiry.getCreatedTime(), ""));
        return map;
    }


    private OpeCustomer buildOpeCustomerSingle(IdEnter enter, OpeCustomerInquiry opeCustomerInquiry) {
        OpeCustomer opeCustomer = new OpeCustomer();
        opeCustomer.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        opeCustomer.setDr(0);
        opeCustomer.setTenantId(0L);
        opeCustomer.setTimeZone(enter.getTimeZone());
        opeCustomer.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        opeCustomer.setSalesId(0L);
        opeCustomer.setCustomerCode("0");
        opeCustomer.setIndustryType(opeCustomerInquiry.getIndustry());
        opeCustomer.setCustomerType(opeCustomerInquiry.getCustomerType());
        opeCustomer.setCustomerFirstName(opeCustomerInquiry.getFirstName());
        opeCustomer.setCustomerLastName(opeCustomerInquiry.getLastName());
        opeCustomer.setCustomerFullName(new StringBuilder(opeCustomerInquiry.getFirstName()).append(" ").append(opeCustomerInquiry.getLastName()).toString());
        opeCustomer.setScooterQuantity(opeCustomerInquiry.getScooterQuantity());
        opeCustomer.setAccountFlag(CustomerAccountFlagEnum.NORMAL.getValue());
        opeCustomer.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        if (StringUtils.isNotEmpty(opeCustomerInquiry.getContactFirst())) {
            opeCustomer.setContactFirstName(opeCustomerInquiry.getContactFirst());
        }
        if (StringUtils.isNotEmpty(opeCustomerInquiry.getContactLast())) {
            opeCustomer.setContactLastName(opeCustomerInquiry.getContactLast());
        }
        if (!StringUtils.isAllBlank(opeCustomerInquiry.getContactFirst(), opeCustomerInquiry.getContactLast())) {
            opeCustomer.setContactFullName(new StringBuilder(opeCustomerInquiry.getContactFirst()).append(" ").append(opeCustomerInquiry.getContactLast()).toString());
        }
        if (StringUtils.isNotEmpty(opeCustomerInquiry.getTelephone())) {
            opeCustomer.setTelephone(opeCustomerInquiry.getTelephone());
        }
        if (StringUtils.isNotEmpty(opeCustomerInquiry.getRemarks())) {
            opeCustomer.setMemo(opeCustomerInquiry.getRemarks());
        }
        if (StringUtils.isNotEmpty(opeCustomerInquiry.getAddress())) {
            opeCustomer.setAddress(opeCustomerInquiry.getAddress());
        }
        opeCustomer.setEmail(opeCustomerInquiry.getEmail());
        opeCustomer.setCreatedBy(enter.getUserId());
        opeCustomer.setCreatedTime(new Date());
        opeCustomer.setUpdatedBy(enter.getUserId());
        opeCustomer.setUpdatedTime(new Date());
        return opeCustomer;
    }

}
