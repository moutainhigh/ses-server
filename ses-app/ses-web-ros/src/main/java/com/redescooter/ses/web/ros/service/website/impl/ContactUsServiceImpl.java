package com.redescooter.ses.web.ros.service.website.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.website.ContantUsMessageType;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.mail.MailContactUsMessageEnter;
import com.redescooter.ses.starter.common.config.OssConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.config.ConstantUsEmailConfig;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.ContactUsMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUs;
import com.redescooter.ses.web.ros.dm.OpeContactUsTrace;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeContactUsService;
import com.redescooter.ses.web.ros.service.base.OpeContactUsTraceService;
import com.redescooter.ses.web.ros.service.website.ContactUsService;
import com.redescooter.ses.web.ros.service.website.ContactUsTraceService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.customer.*;
import com.redescooter.ses.web.ros.vo.inquiry.SaveInquiryEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.entity.ContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @ClassNameContactUsServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/8/4 20:07
 * @Version V1.0
 **/
@Slf4j
@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    private OpeContactUsService opeContactUsService;

    @Autowired
    private OpeContactUsTraceService opeContactUsTraceService;

    @Autowired
    private ContactUsMapper contactUsMapper;

    @Reference
    private CityBaseService cityBaseService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private ContactUsTraceService contactUsTraceService;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Autowired
    private ConstantUsEmailConfig constantUsEmailConfig;

    @Value("${excel.folder}")
    private String excelFolder;

    @Autowired
    private OssConfig ossConfig;

    @Override
    public PageResult<ContactUsListResult> list(ContactUsListEnter enter) {
        ;
        if (enter.getKeyWord() != null && enter.getKeyWord().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        int totalRows = contactUsMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ContactUsListResult> list = contactUsMapper.list(enter);
        return PageResult.create(enter, totalRows, list);
    }

    @Override
    public List<ContactUsDetailResult> detail(ContactUsEnter enter) {
        return contactUsMapper.detailList(enter);
    }

    @Override
    public List<ContactUsHistoryResult> trace(ContactUsEnter enter) {
        List<OpeContactUsTrace> opeContactUsTraceList = opeContactUsTraceService.list(
                new QueryWrapper<OpeContactUsTrace>()
                        .eq(OpeContactUsTrace.COL_CONTACT_US_ID, enter.getId())
                        .orderByDesc(OpeContactUsTrace.COL_CREATED_TIME));

        Map<ContactUsHistoryResult, List<ContactUsHistoryReplyResult>> resultMap = new HashMap<>();

        opeContactUsTraceList.forEach(item -> {
            if (StringUtils.equals(item.getMessageType(), ContantUsMessageType.LEAVE_MESSAGE.getValue())) {
                ContactUsHistoryResult contactUsHistoryResult = new ContactUsHistoryResult();
                BeanUtils.copyProperties(item, contactUsHistoryResult);
                resultMap.put(contactUsHistoryResult, new ArrayList<>());
            }
            if (StringUtils.equals(item.getMessageType(), ContantUsMessageType.REPLY.getValue())) {
                resultMap.keySet().forEach(reply -> {
                    if (item.getLeaveWordId().equals(reply.getId()) && StringUtils.isNotEmpty(reply.getRemark())) {
                        List<ContactUsHistoryReplyResult> contactUsHistoryReplyResultList = resultMap.get(reply);

                        ContactUsHistoryReplyResult contactUsHistoryReplyResult = new ContactUsHistoryReplyResult();
                        contactUsHistoryReplyResult.setId(item.getId());
                        contactUsHistoryReplyResult.setReply(item.getRemark());
                        contactUsHistoryReplyResult.setReplyCreatedTime(item.getCreatedTime());
                        contactUsHistoryReplyResultList.add(contactUsHistoryReplyResult);
                        resultMap.put(reply, contactUsHistoryReplyResultList);
                    }
                });

            }
        });

        List<ContactUsHistoryResult> result = new ArrayList<>();

        resultMap.keySet().forEach(item->{
            item.setReplyList(resultMap.get(item));
            result.add(item);
        });


        return result;
    }

    @Override
    public GeneralResult message(ContactUsMessageEnter enter) {
        OpeContactUsTrace one = opeContactUsTraceService.getOne(new QueryWrapper<OpeContactUsTrace>().eq(OpeContactUsTrace.COL_ID, enter.getId()));
        if (one == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeContactUsTrace trace = new OpeContactUsTrace();
        BeanUtils.copyProperties(one, trace);
        trace.setId(idAppService.getId(SequenceName.OPE_CONTACT_US_TRACE));
        trace.setRemark(enter.getMessage());
        trace.setMessageType(ContantUsMessageType.REPLY.getValue());
        trace.setCreatedTime(new Date());
        trace.setUpdatedTime(new Date());
        trace.setLeaveWordId(enter.getId());
        opeContactUsTraceService.save(trace);
        //发送回复信息
        replyMessageEmail(trace, enter);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public void websiteContactUs(SaveInquiryEnter enter) {
        // 先看这个邮箱是否已存在
        QueryWrapper<OpeContactUs> qw = new QueryWrapper<>();
        qw.eq(OpeContactUs.COL_EMAIL, enter.getEmail());
        qw.last("limit 1");
        OpeContactUs contactUsEntity = opeContactUsService.getOne(qw);
        contactUsEntity = createContactUsEntity(enter, contactUsEntity);
        // 再处理联系我们的历史记录
        contactUsTraceService.createContactUsTrace(contactUsEntity);
    }


    public OpeContactUs createContactUsEntity(SaveInquiryEnter enter, OpeContactUs opeContactUs) {
        if (opeContactUs != null) {
            // 说明这个邮箱已经存在
            opeContactUs.setFrequency(opeContactUs.getFrequency() + 1);
        } else {
            // 说明这个邮箱是第一次联系我们
            opeContactUs = new OpeContactUs();
            opeContactUs.setId(idAppService.getId(SequenceName.OPE_CONTACT_US));
            opeContactUs.setEmail(enter.getEmail());
            opeContactUs.setFrequency(1);
            opeContactUs.setCreatedTime(new Date());
        }
        opeContactUs.setFirstName(enter.getFirstName());
        opeContactUs.setLastName(enter.getLastName());
        opeContactUs.setFullName(opeContactUs.getFirstName() + " " + opeContactUs.getLastName());
        opeContactUs.setTelephone(enter.getTelephone());
        opeContactUs.setCountry(enter.getCountryId());
        opeContactUs.setCountryName(enter.getCustomerCountry());
        opeContactUs.setCityName(enter.getCity());
        opeContactUs.setDistrictName(enter.getDistrust());
        opeContactUs.setDistrict(cityBaseService.getDistrictId(opeContactUs.getCityName(), opeContactUs.getDistrictName()));
        opeContactUs.setAddress(enter.getAddress());
        opeContactUs.setRemark(enter.getRemark());
        opeContactUs.setUpdatedTime(new Date());
        opeContactUsService.saveOrUpdate(opeContactUs);
        return opeContactUs;
    }


    @Override
    public GeneralResult export(ContactUsListEnter enter) {
        String excelPath = "";
        List<ContactUsListResult> list = contactUsMapper.export(enter);
        List<Map<String, Object>> dataMap = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (ContactUsListResult contactUsListResult : list) {
                dataMap.add(toMap(contactUsListResult));
            }
            String sheetName = "Contact Us";
            String[] headers = {"CUSTOMER NAME", "EMAIL", "PHONE", "COUNTRY", "AREA", "ADDRESS", "FREQUENCY", "CREATION TIME"};
            String exportExcelName = String.valueOf(System.currentTimeMillis());
            try {
                String path = ExcelUtil.exportExcel(sheetName, dataMap, headers, exportExcelName, excelFolder);
                log.info("路劲是这个！！！！！！！！！！！！！！！" + excelFolder);
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
            }catch (Exception e){
               log.info("出问题了！！！！！！！！");
            }
        }else {
            log.info("没有找到数据！！！！！！！！！！");
        }
        return new GeneralResult(excelPath);
    }


    private Map<String, Object> toMap(ContactUsListResult contactUsListResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("CUSTOMER NAME", Strings.isNullOrEmpty(contactUsListResult.getFullName()) ? "--" : contactUsListResult.getFullName());
        map.put("EMAIL", Strings.isNullOrEmpty(contactUsListResult.getEmail()) ? "--" : contactUsListResult.getEmail());
        map.put("PHONE", Strings.isNullOrEmpty(contactUsListResult.getTelephone()) ? "--" : contactUsListResult.getTelephone());
        map.put("COUNTRY", Strings.isNullOrEmpty(contactUsListResult.getCountryName()) ? "--" : contactUsListResult.getCountryName());
        String area = "";
        if (!Strings.isNullOrEmpty(contactUsListResult.getCityName()) && !Strings.isNullOrEmpty(contactUsListResult.getDistrictName())) {
            area = contactUsListResult.getCityName() + " " + contactUsListResult.getDistrictName();
        } else if (Strings.isNullOrEmpty(contactUsListResult.getCityName()) && Strings.isNullOrEmpty(contactUsListResult.getDistrictName())) {
            area = "--";
        } else if (!Strings.isNullOrEmpty(contactUsListResult.getCityName()) && Strings.isNullOrEmpty(contactUsListResult.getDistrictName())) {
            area = contactUsListResult.getCityName() + " --";
        } else if (Strings.isNullOrEmpty(contactUsListResult.getCityName()) && !Strings.isNullOrEmpty(contactUsListResult.getDistrictName())) {
            area = "-- " + contactUsListResult.getDistrictName();
        }
        map.put("AREA", area);
        map.put("ADDRESS", Strings.isNullOrEmpty(contactUsListResult.getAddress()) ? "--" : contactUsListResult.getAddress());
        map.put("FREQUENCY", contactUsListResult.getFrequency());
        map.put("CREATION TIME", contactUsListResult.getCreateTime() == null ? "--" : DateUtil.format(contactUsListResult.getCreateTime(),""));
        return map;
    }



    private Map<ContactUsHistoryResult, List<ContactUsHistoryReplyResult>> getTraceParameter(List<OpeContactUsTrace> opeContactUsTraceList) {
        Map<ContactUsHistoryResult, List<ContactUsHistoryReplyResult>> resultMap = new HashMap<>();

        opeContactUsTraceList.forEach(item -> {
            if (StringUtils.equals(item.getMessageType(), ContantUsMessageType.LEAVE_MESSAGE.getValue())) {
                ContactUsHistoryResult contactUsHistoryResult = new ContactUsHistoryResult();
                BeanUtils.copyProperties(item, contactUsHistoryResult);
                resultMap.put(contactUsHistoryResult, new ArrayList<>());
            }
            if (StringUtils.equals(item.getMessageType(), ContantUsMessageType.REPLY.getValue())) {
                resultMap.keySet().forEach(reply -> {
                    if (item.getLeaveWordId().equals(reply.getId()) && StringUtils.isNotEmpty(reply.getRemark())) {
                        List<ContactUsHistoryReplyResult> contactUsHistoryReplyResultList = resultMap.get(reply);

                        ContactUsHistoryReplyResult contactUsHistoryReplyResult = new ContactUsHistoryReplyResult();
                        contactUsHistoryReplyResult.setId(item.getId());
                        contactUsHistoryReplyResult.setReply(item.getRemark());
                        contactUsHistoryReplyResult.setReplyCreatedTime(item.getCreatedTime());
                        contactUsHistoryReplyResultList.add(contactUsHistoryReplyResult);
                        resultMap.put(reply, contactUsHistoryReplyResultList);
                    }
                });

            }
        });
        return resultMap;
    }

    private void replyMessageEmail(OpeContactUsTrace trace, ContactUsMessageEnter contactUsMessageEnter) {
        MailContactUsMessageEnter enter = new MailContactUsMessageEnter();
        enter.setMessage(trace.getRemark());
        enter.setName(trace.getFirstName() + " " + trace.getLastName());
        enter.setEvent(MailTemplateEventEnums.ROS_CONTACTUS_REPLY_MESSAGE.getEvent());
        enter.setMailSystemId(SystemIDEnums.REDE_SES.getSystemId());
        enter.setMailAppId(AppIDEnums.SES_ROS.getValue());
        enter.setToMail(trace.getEmail());
        enter.setUserRequestId(contactUsMessageEnter.getRequestId());
        enter.setToUserId(contactUsMessageEnter.getUserId());
        mailMultiTaskService.contactUsReplyMessageEmail(enter);
    }

}
