package com.redescooter.ses.web.ros.service.factory.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.factory.FactoryEventEnum;
import com.redescooter.ses.api.common.enums.factory.FactoryStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.FactoryServiceMapper;
import com.redescooter.ses.web.ros.dao.base.OpeFactoryMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import com.redescooter.ses.web.ros.dm.OpeSysRpsUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryTraceService;
import com.redescooter.ses.web.ros.service.base.OpeSysRpsUserService;
import com.redescooter.ses.web.ros.service.factory.FactoryRosService;
import com.redescooter.ses.web.ros.vo.factory.FactoryEditEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.functors.FalsePredicate;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FactoryRosServiceImpl implements FactoryRosService {

    @Autowired
    private OpeFactoryService factoryService;

    @Autowired
    private OpeFactoryMapper opeFactoryMapper;

    @Autowired
    private FactoryServiceMapper factoryServiceMapper;

    @Autowired
    private OpeFactoryTraceService factoryTraceService;

    @Autowired
    private OpeSysRpsUserService opeSysRpsUserService;

    @DubboReference
    private IdAppService idAppService;

    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {

        List<CountByStatusResult> statusResults = factoryServiceMapper.countStatus(enter);

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : statusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (FactoryStatusEnum status : FactoryStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    @Transactional
    @Override
    public GeneralResult save(FactorySaveEnter factorySaveEnter) {
      //employeeListEnter参数值去空格
      FactorySaveEnter enter = SesStringUtils.objStringTrim(factorySaveEnter);
        checkSaveFactoryParameter(enter);
        //邮箱校验
        Boolean booleanResult = checkMail(enter.getContactEmail(),"");
        if (!booleanResult){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
      //员工名称首位大写
      String factoryName = SesStringUtils.upperCaseString(enter.getFactoryName());
      if (StringUtils.isNotEmpty(enter.getContactFirstName())){
          String firstName = SesStringUtils.upperCaseString(enter.getContactFirstName());
          enter.setContactFirstName(firstName);
      }
        if (StringUtils.isNotEmpty(enter.getContactLastName())){
          String lastName = SesStringUtils.upperCaseString(enter.getContactLastName());
            enter.setContactLastName(lastName);
        }
        if (StringUtils.isNotEmpty(enter.getContactFullName())){
            String lastName = SesStringUtils.upperCaseString(enter.getContactFullName());
            enter.setContactFullName(lastName);
        }
        enter.setFactoryName(factoryName);
        OpeFactory factorySave = new OpeFactory();

        BeanUtils.copyProperties(enter, factorySave);

        factorySave.setId(idAppService.getId(SequenceName.OPE_FACTORY));
        factorySave.setDr(0);
        factorySave.setStatus(FactoryStatusEnum.NORMAL.getValue());
        if(SesStringUtils.isNoneBlank(enter.getFactoryLatitude(),enter.getFactoryLongitude())){
            factorySave.setFactoryLatitude(new BigDecimal(enter.getFactoryLatitude()));
            factorySave.setFactoryLongitude(new BigDecimal(enter.getFactoryLongitude()));
        }
        factorySave.setTenantId(enter.getTenantId());
        factorySave.setUserId(enter.getUserId());
        factorySave.setOverdueFlag(0);
        factorySave.setCreatedBy(enter.getUserId());
        factorySave.setCreatedTime(new Date());
        factorySave.setUpdatedBy(enter.getUserId());
        factorySave.setUpdatedTime(new Date());

        boolean save = factoryService.save(factorySave);
        if (save) {
            saveFactoryTrace(FactoryEventEnum.CREATE.getValue(), factorySave);
        }
        // 生成工厂的账号信息
        try {
            createFactoryUser(factorySave);
        }catch (Exception e){}
        return new GeneralResult(enter.getRequestId());
    }


    // 通过邮箱创建工厂的RPS登陆账号
    public void createFactoryUser(OpeFactory factory){
        OpeSysRpsUser user = new OpeSysRpsUser();
        int salt = RandomUtils.nextInt(10000, 99999);
        String decryptPassword = "RedEScooter2019";
        user.setPassword(DigestUtils.md5Hex(decryptPassword + salt));
        user.setSalt(String.valueOf(salt));
        user.setLoginName(factory.getContactEmail());
        user.setStatus(SysUserStatusEnum.NORMAL.getCode());
        user.setCreatedBy(factory.getCreatedBy());
        user.setCreatedTime(new Date());
        user.setUpdatedBy(factory.getUpdatedBy());
        user.setUpdatedTime(new Date());
        opeSysRpsUserService.saveOrUpdate(user);
    }


    public Boolean checkMail(String mail,String idStr) {

        QueryWrapper<OpeFactory> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeFactory.COL_CONTACT_EMAIL, mail);
        wrapper.eq(OpeFactory.COL_DR, 0);
        if(!Strings.isNullOrEmpty(idStr)){
            // 修改的时候,排除当前的这条数据
            wrapper.ne(OpeFactory.COL_ID, Long.parseLong(idStr));
        }
        Boolean mailBoolean = opeFactoryMapper.selectCount(wrapper) > 0 ? Boolean.FALSE  : Boolean.TRUE;
        return mailBoolean;
    }

    @Transactional
    @Override
    public GeneralResult edit(FactoryEditEnter factorySaveEnter) {
      //employeeListEnter参数值去空格
      FactorySaveEnter enter = SesStringUtils.objStringTrim(factorySaveEnter);
      checkSaveFactoryParameter(enter);
        Boolean mailBoolean = checkMail(enter.getContactEmail(),factorySaveEnter.getId().toString());
        if (!mailBoolean){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        //员工名称首位大写
      String factoryName = SesStringUtils.upperCaseString(enter.getFactoryName());
      if(StringUtils.isNotEmpty(enter.getContactFirstName())){
          enter.setContactFirstName(SesStringUtils.upperCaseString(enter.getContactFirstName()));
      }
      if (StringUtils.isNotEmpty(enter.getContactFirstName())){
          enter.setContactLastName(SesStringUtils.upperCaseString(enter.getContactFirstName()));
      }
      enter.setFactoryName(factoryName);
        OpeFactory factoryEdit = new OpeFactory();
        BeanUtils.copyProperties(enter, factoryEdit);
        factoryEdit.setUpdatedBy(enter.getUserId());
        factoryEdit.setUpdatedTime(new Date());

        boolean update = factoryService.updateById(factoryEdit);
        if (update) {
            saveFactoryTrace(FactoryEventEnum.MODIFY.getValue(), factoryEdit);
        }

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public FactoryResult details(IdEnter enter) {

        OpeFactory factory = factoryService.getById(enter.getId());

        FactoryResult factoryResult = new FactoryResult();
        Optional.ofNullable(factory).ifPresent(f -> {
            BeanUtils.copyProperties(f, factoryResult);
            factoryResult.setRequestId(enter.getRequestId());
        });

        return factoryResult;
    }

    @Override
    public PageResult<FactoryResult> list(FactoryPage page) {
        if (page.getKeyword() != null && page.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(page);
        }
        int count = factoryServiceMapper.listCount(page);
        if (count == 0) {
            return PageResult.createZeroRowResult(page);
        }

        List<FactoryResult> list = factoryServiceMapper.list(page);

        return PageResult.create(page, count, list);
    }

    @Transactional
    @Override
    public GeneralResult saveFactoryTrace(String event, OpeFactory factory) {
        OpeFactoryTrace trace = new OpeFactoryTrace();
        trace.setId(idAppService.getId(SequenceName.OPE_FACTORY_TRACE));
        trace.setDr(0);
        trace.setFactoryId(factory.getId());
        trace.setTenantId(factory.getTenantId());
        trace.setUserId(factory.getUserId());
        trace.setStatus(factory.getStatus());
        trace.setEvent(event);
        trace.setEventTime(new Date());
        trace.setReason(factory.getFactoryMemo());
        trace.setCreatedBy(factory.getUpdatedBy());
        trace.setCreatedTime(new Date());
        trace.setUpdatedBy(factory.getUpdatedBy());
        trace.setUpdatedTime(new Date());

        factoryTraceService.save(trace);

        return new GeneralResult();
    }

    private void   checkSaveFactoryParameter(FactorySaveEnter enter){
      if (enter.getFactoryName().length() < 2 || enter.getFactoryName().length() > 40){
        throw new SesWebRosException(ExceptionCodeEnums.FACTORY_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.FACTORY_NAME_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getFactoryAddress().length() < 2 || enter.getFactoryAddress().length() > 40){
        throw new SesWebRosException(ExceptionCodeEnums.FACTORY_ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.FACTORY_ADDRESS_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getContactFullName().length() < 2 || enter.getContactFullName().length() > 20){
        throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getContactEmail().length() < 2 || enter.getContactEmail().length() > 50 || !enter.getContactEmail().contains("@")){
        throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getContactPhone().length() < 2 || enter.getContactPhone().length() > 20){
        throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getFactoryTag().length() < 2 || enter.getFactoryTag().length() > 20){
        throw new SesWebRosException(ExceptionCodeEnums.FACTORY_TAG_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.FACTORY_TAG_IS_NOT_ILLEGAL.getMessage());
      }
      if (enter.getBusinessNumber().length() < 2 || enter.getBusinessNumber().length() > 20){
        throw new SesWebRosException(ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getCode(), ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getMessage());
      }
    }

}
