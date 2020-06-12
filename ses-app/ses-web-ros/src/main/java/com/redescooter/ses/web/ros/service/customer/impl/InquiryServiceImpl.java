package com.redescooter.ses.web.ros.service.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerAccountFlagEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerIndustryEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
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
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryListEnter;
import com.redescooter.ses.web.ros.vo.inquiry.InquiryResult;
import com.redescooter.ses.web.ros.vo.inquiry.NewSaveInquiryEnter;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.HashMap;
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

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private CityBaseService cityBaseService;

    @Reference
    private IdAppService idAppService;

    @Value("${Request.privateKey}")
    private String privateKey;


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
     * @param enter
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    @Transactional
    @Override
    public GeneralResult saveInquiry(SaveInquiryEnter enter) {

        //邮箱 去空格
        enter.setEmail(SesStringUtils.stringTrim(enter.getEmail()));

        // 查询已存在的email
        List<String> emailList = inquiryServiceMapper.usingEmailList();
        if (emailList.contains(enter.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        OpeCustomerInquiry opeCustomerInquiry = buildOpeCustomerInquirySingle(enter);
        opeCustomerInquiryService.save(opeCustomerInquiry);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 保存询价单
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/3/5 15:03
     * @Version: Ros 1.3
     */
    @Override
    public GeneralResult newSaveInquiry(NewSaveInquiryEnter enter) {

        //邮箱 去空格
        enter.setEmail(SesStringUtils.stringTrim(enter.getEmail()));
        //邮箱解密
        //电话解密
        if (!StringUtils.isAllBlank(enter.getTelephone(), enter.getEmail())) {
            try {
                enter.setEmail(RsaUtils.decrypt(enter.getEmail(), privateKey));
                enter.setTelephone(RsaUtils.decrypt(enter.getTelephone(), privateKey));
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            if (enter.getEmail().length() < 2 || enter.getEmail().length() > 20) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_ILLEGAL.getCode(),ExceptionCodeEnums.EMAIL_IS_ILLEGAL.getMessage());
            }
        }


        // 查询已存在的email 暂时注释掉 邮箱过滤
//        List<String> emailList = inquiryServiceMapper.usingEmailList();
//        if (emailList.contains(enter.getEmail())) {
//            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
//        }
        CityResult cityResult = cityBaseService.queryCityDetailByName(enter.getDistrust());
        if (cityResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DISTRUST_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DISTRUST_IS_NOT_EXIST.getMessage());
        }

        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setCustomerId(0L);
        opeCustomerInquiry.setCountry(null);
        opeCustomerInquiry.setCity(null);
        opeCustomerInquiry.setDistrict(cityResult.getId());
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


        opeCustomerInquiryService.save(opeCustomerInquiry);
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
        int count = inquiryServiceMapper.inquiryListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryResult> inquiryResultList = inquiryServiceMapper.inquiryList(enter);
        inquiryResultList.forEach(item -> {
            String city = null;
            String distrust = null;
            if (item.getCityId() != null && item.getCityId() != 0) {
                city = cityBaseService.queryCityDeatliById(new IdEnter(item.getCityId())).getName();
            }
            if (item.getDistrustId() != null && item.getDistrustId() != 0) {
                distrust = cityBaseService.queryCityDeatliById(new IdEnter(item.getDistrustId())).getName();
            }
            item.setCityName(city);
            item.setDistrustName(distrust);
        });
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

        String city = null;
        String distrust = null;
        if (inquiryResult.getCityId() != null && inquiryResult.getCityId() != 0) {
            city = cityBaseService.queryCityDeatliById(new IdEnter(inquiryResult.getCityId())).getName();
        }
        if (inquiryResult.getDistrustId() != null && inquiryResult.getDistrustId() != 0) {
            distrust = cityBaseService.queryCityDeatliById(new IdEnter(inquiryResult.getDistrustId())).getName();
        }
        inquiryResult.setCityName(city);
        inquiryResult.setDistrustName(distrust);

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

    private OpeCustomerInquiry buildOpeCustomerInquirySingle(SaveInquiryEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setCustomerId(0L);
        opeCustomerInquiry.setCountry(enter.getCountryId());
        opeCustomerInquiry.setCity(enter.getCityId());
        opeCustomerInquiry.setDistrict(enter.getDistrustId());
        opeCustomerInquiry.setCustomerSource("");
        opeCustomerInquiry.setSalesId(0L);
        opeCustomerInquiry.setSource(InquirySourceEnums.INQUIRY.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPROCESSED.getValue());
        if (CustomerIndustryEnums.getIndustryEnumByValue(enter.getIndustryType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        opeCustomerInquiry.setIndustry(enter.getIndustryType());
        if (CustomerTypeEnum.getEnumByValue(enter.getCustomerType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        opeCustomerInquiry.setCustomerType(CustomerTypeEnum.PERSONAL.getValue());

        if (SesStringUtils.equals(enter.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            if (SesStringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
            opeCustomerInquiry.setCompanyName(enter.getCompanyName());
            opeCustomerInquiry.setScooterQuantity(enter.getScooterQuantity());
            opeCustomerInquiry.setContactFirst(enter.getContactFirstName());
            opeCustomerInquiry.setContactLast(enter.getContactLastName());
            opeCustomerInquiry.setContantFullName(new StringBuilder(enter.getContactFirstName()).append(" ").append(enter.getContactLastName()).toString());
        }
        if (SesStringUtils.equals(enter.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            if (SesStringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (SesStringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
            opeCustomerInquiry.setFirstName(enter.getCustomerFirstName());
            opeCustomerInquiry.setLastName(enter.getCustomerLastName());
            opeCustomerInquiry.setFullName(new StringBuilder(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
            opeCustomerInquiry.setScooterQuantity(1);
            opeCustomerInquiry.setContactFirst(null);
            opeCustomerInquiry.setContactLast(null);
            opeCustomerInquiry.setContantFullName(null);
        }
        opeCustomerInquiry.setCountryCode(enter.getCountryCode());
        opeCustomerInquiry.setTelephone(enter.getTelephone());
        opeCustomerInquiry.setEmail(enter.getEmail());
        opeCustomerInquiry.setAddress("");
        opeCustomerInquiry.setRemarks(enter.getRemark());
        if (enter.getUserId() == null) {
            opeCustomerInquiry.setCreatedBy(0L);
            opeCustomerInquiry.setUpdatedBy(0L);
        } else {
            opeCustomerInquiry.setCreatedBy(enter.getUserId());
            opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        }
        opeCustomerInquiry.setCreatedTime(new Date());
        opeCustomerInquiry.setUpdatedTime(new Date());
        return opeCustomerInquiry;
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
        if (StringUtils.equals(opeCustomerInquiry.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            opeCustomer.setCompanyName(opeCustomerInquiry.getCompanyName());
            opeCustomer.setContactFirstName(opeCustomerInquiry.getContactFirst());
            opeCustomer.setContactLastName(opeCustomerInquiry.getContactLast());
        }
        if (StringUtils.equals(opeCustomerInquiry.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            opeCustomer.setCustomerFirstName(opeCustomerInquiry.getFirstName());
            opeCustomer.setCustomerLastName(opeCustomerInquiry.getLastName());
            opeCustomer.setCustomerFullName(new StringBuilder(opeCustomerInquiry.getFirstName()).append(" ").append(opeCustomerInquiry.getLastName()).toString());
        }
        opeCustomer.setCertificateType("0");
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
