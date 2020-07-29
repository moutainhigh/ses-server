package com.redescooter.ses.web.ros.service.customer.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
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
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.InquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private CityBaseService cityBaseService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private ExcelService excelService;

    @Value("${Request.privateKey}")
    private String privateKey;

    @Value("${excel.folder}")
    private String excelFolder;

    @Autowired
    private MondayService mondayService;

    @Autowired
    private OssConfig ossConfig;

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
        map.remove(InquiryStatusEnums.PROCESSED.getValue());
        map.remove(InquiryStatusEnums.PAY_LAST_PARAGRAPH.getValue());
        return map;
    }

    /**
     * @param saveInquiryEnter
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    @Override
    public GeneralResult saveInquiry(SaveInquiryEnter saveInquiryEnter) {
        //入参去空格
        SaveInquiryEnter enter = SesStringUtils.objStringTrim(saveInquiryEnter);
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

        //查询 该邮箱 是否为正式客户 是的话 直接返回
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().in(OpeCustomer::getStatus, CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue(),
                CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue()).eq(OpeCustomer::getEmail,
                enter.getEmail()));
        if (opeCustomer != null) {
            return new GeneralResult(enter.getRequestId());
        }

        //查询客户是否有询价单 存在的话数量累计
        List<OpeCustomerInquiry> customerInquiryList =
                opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>().eq(OpeCustomerInquiry::getEmail, enter.getEmail()).ne(OpeCustomerInquiry::getStatus,
                        InquiryStatusEnums.DECLINE.getValue()));

        OpeCustomerInquiry opeCustomerInquiry = null;
        if (CollectionUtils.isEmpty(customerInquiryList)) {
            opeCustomerInquiry = buildOpeCustomerInquiry(enter);
        } else {
            opeCustomerInquiry = customerInquiryList.get(0);
            opeCustomerInquiry.setScooterQuantity(opeCustomerInquiry.getScooterQuantity() + 1);
        }

       /* CityResult cityResult = cityBaseService.queryCityDetailByName(enter.getDistrust());
        if (cityResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DISTRUST_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DISTRUST_IS_NOT_EXIST.getMessage());
        }*/
        opeCustomerInquiry.setSource("1");
        opeCustomerInquiryService.saveOrUpdate(opeCustomerInquiry);

        //Monday 同步数据
        mondayService.websiteContantUs(opeCustomerInquiry);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeCustomerInquiry buildOpeCustomerInquiry(SaveInquiryEnter enter) {

        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setOrderNo(RandomUtil.simpleUUID());
        opeCustomerInquiry.setCustomerId(0L);
        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        opeCustomerInquiry.setCountry(null);
        opeCustomerInquiry.setCity(null);
        opeCustomerInquiry.setDistrict(null);
        opeCustomerInquiry.setCustomerSource("");
        opeCustomerInquiry.setSalesId(0L);
        opeCustomerInquiry.setSource(InquirySourceEnums.INQUIRY.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPROCESSED.getValue());

        //默认为个人餐厅
//        opeCustomerInquiry.setIndustry(CustomerIndustryEnums.RESTAURANT.getValue());
//        opeCustomerInquiry.setCustomerType(CustomerTypeEnum.PERSONAL.getValue());

        opeCustomerInquiry.setCompanyName(null);
        opeCustomerInquiry.setFirstName(SesStringUtils.upperCaseString(enter.getFirstName()));
        opeCustomerInquiry.setLastName(SesStringUtils.upperCaseString(enter.getLastName()));
        opeCustomerInquiry.setFullName(new StringBuilder(SesStringUtils.upperCaseString(enter.getFirstName())).append(" ").append(SesStringUtils.upperCaseString(enter.getLastName())).toString());
        opeCustomerInquiry.setScooterQuantity(1);
        opeCustomerInquiry.setContactFirst(null);
        opeCustomerInquiry.setContactLast(null);
        opeCustomerInquiry.setContantFullName(null);
        opeCustomerInquiry.setCountryCode(enter.getCountryCode());
        opeCustomerInquiry.setTelephone(enter.getTelephone());
        opeCustomerInquiry.setEmail(enter.getEmail());
        opeCustomerInquiry.setAddress("");
        opeCustomerInquiry.setRemarks(enter.getRemark());
        opeCustomerInquiry.setCreatedBy(0L);
        opeCustomerInquiry.setUpdatedBy(0L);
        opeCustomerInquiry.setCreatedTime(new Date());
        opeCustomerInquiry.setUpdatedTime(new Date());

        //def1 国家 def2 城市 distrust区域
        opeCustomerInquiry.setDef1(enter.getCustomerCountry());
        opeCustomerInquiry.setDistrict(Long.valueOf(enter.getDistrust()));
        opeCustomerInquiry.setDef2(enter.getCity());
        opeCustomerInquiry.setCountry(enter.getCountryId());
        return opeCustomerInquiry;
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
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.CUSTOMER_INQUIRY_PAY_DEPOSIT.getEvent());
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
    @Transactional
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
    @Transactional
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
    @Transactional
    @Override
    public GeneralResult inquiryExport(InquiryListEnter enter) {
        String excelPath = "";
        List<InquiryResult> list = inquiryServiceMapper.exportInquiry(enter);
        List<Map<String, Object>> dataMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (InquiryResult inquiry : list) {
                dataMap.add(toMap(inquiry));
            }
            String sheetName = "询价单";
            String[] headers = {"NAME","SURNAME","EMAIL","TELEPHONE","CODE POSTAL","VOTER MESSAGE","CITY NAME","CREATE TIME"};
            String exportExcelName = String.valueOf(System.currentTimeMillis());
            try {
                String path = ExcelUtil.exportExcel(sheetName, dataMap, headers, exportExcelName,excelFolder);
                log.info("路劲是这个！！！！！！！！！！！！！！！"+excelFolder);
                File file = new File(path);
                FileInputStream inputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                        ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

                ClientConfiguration conf = new ClientConfiguration();
                conf.setProtocol(ProtocolEnums.getProtocol(ossConfig.getProtocol()));
                OSSClient ossClient = null;
                ossClient = new OSSClient(ossConfig.getInternalEndpoint(), ossConfig.getAccessKeyId(),
                        ossConfig.getSecretAccesskey(), conf);
                String fileName = System.currentTimeMillis()+".xlsx";
                ossClient.putObject(ossConfig.getDefaultBucketName(), fileName,
                        multipartFile.getInputStream());
                String bucket = ossConfig.getDefaultBucketName();
                excelPath = "https://" + bucket + "." + ossConfig.getPublicEndpointDomain() + "/" + fileName;
                if(file.exists()){
                    file.delete();
                }
            }catch (Exception e){

            }
        }
        return new GeneralResult(excelPath);
    }


   private Map<String, Object> toMap(InquiryResult opeCustomerInquiry){
       Map<String, Object> map = new LinkedHashMap<>();
       map.put("NAME",Strings.isNullOrEmpty(opeCustomerInquiry.getCustomerFirstName())?"--":opeCustomerInquiry.getCustomerFirstName());
       map.put("SURNAME NAME",Strings.isNullOrEmpty(opeCustomerInquiry.getCustomerLastName())?"--":opeCustomerInquiry.getCustomerLastName());
       map.put("EMAIL",Strings.isNullOrEmpty(opeCustomerInquiry.getEmail())?"--":opeCustomerInquiry.getEmail());
       map.put("TELEPHONE",Strings.isNullOrEmpty(opeCustomerInquiry.getTelephone())?"--":"+33-"+opeCustomerInquiry.getTelephone());
       map.put("CODE POSTAL",Strings.isNullOrEmpty(opeCustomerInquiry.getDef2())?"--":opeCustomerInquiry.getDef2());
       map.put("VOTER MESSAGE",Strings.isNullOrEmpty(opeCustomerInquiry.getRemark())?"--":opeCustomerInquiry.getRemark());
       map.put("CITY NAME",Strings.isNullOrEmpty(opeCustomerInquiry.getDef3())?"--":opeCustomerInquiry.getDef3());
       map.put("CREATE TIME",opeCustomerInquiry.getAcceptanceTime()==null?"--":DateUtil.getTimeStrDefault(opeCustomerInquiry.getAcceptanceTime()));
       return map;
   }


    private OpeCustomer buildOpeCustomerSingle(IdEnter enter, OpeCustomerInquiry opeCustomerInquiry) {
        OpeCustomer opeCustomer = new OpeCustomer();
        opeCustomer.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        opeCustomer.setDr(0);
        opeCustomer.setTenantId(0L);
        opeCustomer.setTimeZone(enter.getTimeZone());
        opeCustomer.setCountry(opeCustomerInquiry.getCountry());
        opeCustomer.setCountryCode(opeCustomerInquiry.getCountryCode());
        opeCustomer.setCity(opeCustomerInquiry.getCity());
        opeCustomer.setDistrust(opeCustomerInquiry.getDistrict());
        opeCustomer.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
        opeCustomer.setSalesId(0L);
        opeCustomer.setCustomerCode("0");
        opeCustomer.setIndustryType(opeCustomerInquiry.getIndustry());
        opeCustomer.setCustomerType(opeCustomerInquiry.getCustomerType());
//        if (StringUtils.equals(opeCustomerInquiry.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
//            opeCustomer.setCompanyName(opeCustomerInquiry.getCompanyName());
//            opeCustomer.setContactFirstName(opeCustomerInquiry.getContactFirst());
//            opeCustomer.setContactLastName(opeCustomerInquiry.getContactLast());
//        }
//        if (StringUtils.equals(opeCustomerInquiry.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
//            opeCustomer.setCustomerFirstName(opeCustomerInquiry.getFirstName());
//            opeCustomer.setCustomerLastName(opeCustomerInquiry.getLastName());
//            opeCustomer.setCustomerFullName(new StringBuilder(opeCustomerInquiry.getFirstName()).append(" ").append(opeCustomerInquiry.getLastName()).toString());
//        }
        opeCustomer.setCustomerFirstName(opeCustomerInquiry.getFirstName());
        opeCustomer.setCustomerLastName(opeCustomerInquiry.getLastName());
        opeCustomer.setCustomerFullName(new StringBuilder(opeCustomerInquiry.getFirstName()).append(" ").append(opeCustomerInquiry.getLastName()).toString());
//        opeCustomer.setCertificateType("0");
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
