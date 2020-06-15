package com.redescooter.ses.web.ros.service.factory.impl;

import com.redescooter.ses.api.common.enums.employee.AddressBureauEnums;
import com.redescooter.ses.api.common.enums.factory.FactoryEventEnum;
import com.redescooter.ses.api.common.enums.factory.FactoryStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.FactoryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.factory.FactoryRosService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryTraceService;
import com.redescooter.ses.web.ros.vo.factory.FactoryEditEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryPage;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.SaveEmployeeEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FactoryRosServiceImpl implements FactoryRosService {

    @Autowired
    private OpeFactoryService factoryService;

    @Autowired
    private FactoryServiceMapper factoryServiceMapper;

    @Autowired
    private OpeFactoryTraceService factoryTraceService;

    @Reference
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

        //员工名称首位大写
        String factoryName = SesStringUtils.upperCaseString(enter.getFactoryName());
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
        return new GeneralResult(enter.getRequestId());
    }

    @Transactional
    @Override
    public GeneralResult edit(FactoryEditEnter factorySaveEnter) {
      //employeeListEnter参数值去空格
      FactorySaveEnter enter = SesStringUtils.objStringTrim(factorySaveEnter);
      checkSaveFactoryParameter(enter);

      //员工名称首位大写
      String factoryName = SesStringUtils.upperCaseString(enter.getFactoryName());
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
      if (page.getKeyword()!=null && page.getKeyword().length()>50){
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
      if (enter.getContactEmail().length() < 2 || enter.getContactEmail().length() > 30 || !enter.getContactEmail().contains("@")){
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
