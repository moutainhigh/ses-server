package com.redescooter.ses.web.ros.service.customer.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerAccountFlagEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.starter.common.config.OssConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.InquiryServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatTypeService;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
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
 * @Version：1.3
 * @create: 2020/03/06 10:27
 */
@Slf4j
@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private InquiryServiceMapper inquiryServiceMapper;

    @Autowired
    private OpeCustomerService opeCustomerService;

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
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAboutUs(SaveAboutUsEnter saveAboutUsEnter) {
        //入参去空格
        SaveAboutUsEnter enter = SesStringUtils.objStringTrim(saveAboutUsEnter);
        //邮箱解密
        //电话解密
        if (!StringUtils.isAllBlank(enter.getTelephone(), enter.getEmail())) {
            try {
                enter.setEmail(RsaUtils.decrypt(enter.getEmail(), privateKey));
                enter.setTelephone(RsaUtils.decrypt(enter.getTelephone(), privateKey));
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            if (enter.getEmail().length() < 2 || enter.getEmail().length() > 50) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
            if (enter.getTelephone().length() < 2 || enter.getTelephone().length() > 10) {
                throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
            }
            //电话
            enter.setTelephone(SesStringUtils.stringTrim(enter.getTelephone()));
            //邮箱 去空格
            enter.setEmail(SesStringUtils.stringTrim(enter.getEmail()));
        }

        // 查询已存在的email 暂时注释掉 邮箱过滤
        //List<String> emailList = inquiryServiceMapper.usingEmailList();

        //是否允许正式客户在联系我们中留言 默认为false  前端控制
        if (!enter.getWhetherConstantUs()) {
            //查询 该邮箱 是否为正式客户 是的话 直接返回
            OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().in(OpeCustomer::getStatus, CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue(),
                    CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue()).eq(OpeCustomer::getEmail,
                    enter.getEmail()));
            if (opeCustomer != null) {
                return new GeneralResult(enter.getRequestId());
            }
        }
        // 官网联系我们
        contactUsService.websiteContactUs(enter);

        //Monday 同步数据
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
     * @desc: 询价单列表
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:44
     * @Version: Ros 1.3
     */
    @Override
    public PageResult<InquiryResult> inquiryList(InquiryListEnter enter) {
        if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        int count = inquiryServiceMapper.inquiryListCount(enter);
        if (count == 0) {
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
     * @desc: 询价单详情
     * @param: enter
     * @retrn: InquiryResult
     * @auther: alex
     * @date: 2020/3/5 15:45
     * @Version: Ros 1.3
     */
    @Override
    public InquiryResult inquiryDetail(IdEnter enter) {
        InquiryResult inquiryResult = inquiryServiceMapper.inquiryDetail(enter);
        if (inquiryResult == null) {
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
            //验证是否可以再次发生邮件
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
     * 定金支付邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult depositPaymentEmail(IdEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getId, enter.getId())
                .eq(OpeCustomerInquiry::getSource, InquirySourceEnums.ORDER_FORM.getValue())
                .last("limit 1"));
        //询价单校验
        if (opeCustomerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.UNPAY_DEPOSIT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        // 创建邮件任务
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(opeCustomerInquiry.getFullName());
        baseMailTaskEnter.setToMail(opeCustomerInquiry.getEmail());
        baseMailTaskEnter.setToUserId(opeCustomerInquiry.getCustomerId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        //暂时为个人端预定
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.CUSTOMER_INQUIRY_PAY_DEPOSIT.getEvent());
        baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_APP.getValue());
        baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        mailMultiTaskService.addCustomerInquiryPayDepositTask(baseMailTaskEnter);

        //设置邮箱发送有效时间
        String key = new StringBuffer(opeCustomerInquiry.getId().toString()).append("send::").append(opeCustomerInquiry.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 尾款支付邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult lastParagraphEmail(IdEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getId, enter.getId())
                .eq(OpeCustomerInquiry::getSource, InquirySourceEnums.ORDER_FORM.getValue())
                .last("limit 1"));
        //询价单校验
        if (opeCustomerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.PAY_DEPOSIT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        // 创建邮件任务
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(opeCustomerInquiry.getFullName());
        baseMailTaskEnter.setToMail(opeCustomerInquiry.getEmail());
        baseMailTaskEnter.setToUserId(opeCustomerInquiry.getCustomerId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        //暂时为个人端预定
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.CUSTOMER_INQUIRY_LAST_PARAGRAPH.getEvent());
        baseMailTaskEnter.setMailAppId(AppIDEnums.SES_ROS.getValue());
        baseMailTaskEnter.setMailSystemId(AppIDEnums.SES_ROS.getSystemId());
        mailMultiTaskService.addCustomerInquiryPayLastParagraphTask(baseMailTaskEnter);

        //设置邮箱发送有效时间
        String key = new StringBuffer(opeCustomerInquiry.getId().toString()).append("send::").append(opeCustomerInquiry.getEmail()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 接受询价单 转化为潜在客户
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult acceptInquiry(IdEnter enter) {
        QueryWrapper<OpeCustomerInquiry> opeCustomerInquiryQueryWrapper = new QueryWrapper<>();
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_DR, 0);
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_ID, enter.getId());
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_SOURCE, InquirySourceEnums.INQUIRY.getValue());
        opeCustomerInquiryQueryWrapper.last("limit 1");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(opeCustomerInquiryQueryWrapper);
        if (opeCustomerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        //已经为潜在可乎 直接返回
        if (SesStringUtils.equals(opeCustomerInquiry.getStatus(), InquiryStatusEnums.PROCESSED.getValue())) {
            return new GeneralResult();
        }
        opeCustomerInquiry.setStatus(InquiryStatusEnums.PROCESSED.getValue());
        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        opeCustomerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.updateById(opeCustomerInquiry);

        // 保存客户信息
        OpeCustomer opeCustomer = buildOpeCustomerSingle(enter, opeCustomerInquiry);
        opeCustomerService.insertOrUpdateSelective(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 拒绝询价单
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult declineInquiry(IdEnter enter) {
        QueryWrapper<OpeCustomerInquiry> opeCustomerInquiryQueryWrapper = new QueryWrapper<>();
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_DR, 0);
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_ID, enter.getId());
        opeCustomerInquiryQueryWrapper.last("limit 1");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getOne(opeCustomerInquiryQueryWrapper);
        if (opeCustomerInquiry == null) {
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
     * 询价单数据导出
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult inquiryExport(InquiryListEnter enter) {
        String excelPath = "";
        List<InquiryExportResult> list = inquiryServiceMapper.exportInquiry(enter);
        log.info("总共的数据量：" + list.size());
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
                /**文件夹不存在，则进行创建**/
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
                log.info("这里出问题了！！！");
            }
        }
        return new GeneralResult(excelPath);
    }

    @Override
    public GeneralResult updateSaleOrder(UpdateInfoResult enter) {
        OpeSpecificatType opeSpecificatType = opeSpecificatTypeMapper.selectById(enter.getSpecificatTypeId());
        if(opeSpecificatType==null) throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_NOT_FOUND.getCode(), ExceptionCodeEnums.SPECIFICAT_NOT_FOUND.getMessage());
        OpeCustomerInquiryB opeCustomerInquiryB = opeCustomerInquiryBMapper.selectById(enter.getCustomerInquiryId());
        if (opeCustomerInquiryB==null) throw new SesWebRosException(ExceptionCodeEnums.CUSTOMERINQUIRY_NOT_FOUND.getCode(), ExceptionCodeEnums.CUSTOMERINQUIRY_NOT_FOUND.getMessage());
        OpeColor opeColor = opeColorMapper.selectById(enter.getColorId());
        if(opeColor==null) throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_FOUND.getCode(), ExceptionCodeEnums.COLOR_NOT_FOUND.getMessage());
        OpeColor opeColors = new OpeColor();
        opeColors.setId(enter.getColorId());
        opeColors.setColorName(enter.getColor());
        int colorResult = opeColorMapper.updateById(opeColors);
        if(colorResult>0){
            OpeSpecificatType opeSpecificatType1 = new OpeSpecificatType();
            opeSpecificatType1.setId(enter.getSpecificatTypeId());
            opeSpecificatType1.setSpecificatName(enter.getEnName());
            int specificatResult = opeSpecificatTypeMapper.updateById(opeSpecificatType1);
            if(specificatResult>0){
                OpeCustomerInquiryB opeCustomerInquiryB1 = new OpeCustomerInquiryB();
                opeCustomerInquiryB1.setId(enter.getCustomerInquiryId());
                opeCustomerInquiryB1.setProductQty(enter.getBatteryQty());
                opeCustomerInquiryBMapper.updateById(opeCustomerInquiryB1);
            }else{
                throw new SesWebRosException(ExceptionCodeEnums.SPECIFICATION_CHANGE_ERRO.getCode(), ExceptionCodeEnums.SPECIFICATION_CHANGE_ERRO.getMessage());
            }
        }else {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_CHANGE_ERRO.getCode(), ExceptionCodeEnums.COLOR_CHANGE_ERRO.getMessage());
        }
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
