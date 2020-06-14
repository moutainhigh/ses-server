package com.redescooter.ses.web.ros.service.bom.impl;

import com.redescooter.ses.api.common.enums.supplier.SupplierEventEnum;
import com.redescooter.ses.api.common.enums.supplier.SupplierStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.SupplierServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.bom.SupplierRosService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierTraceService;
import com.redescooter.ses.web.ros.vo.factory.FactorySaveEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierEditEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierPage;
import com.redescooter.ses.web.ros.vo.supplier.SupplierResult;
import com.redescooter.ses.web.ros.vo.supplier.SupplierSaveEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.*;


@Service
public class SupplierRosServiceImpl implements SupplierRosService {

    @Autowired
    private OpeSupplierService supplierService;
    @Autowired
    private OpeSupplierTraceService supplierTraceService;
    @Autowired
    private SupplierServiceMapper supplierServiceMapper;
    @Reference
    private IdAppService idAppService;


    @Override
    public Map<String, Integer> countStatus(GeneralEnter enter) {
        List<CountByStatusResult> statusResults = supplierServiceMapper.countStatus(enter);
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : statusResults) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (SupplierStatusEnum status : SupplierStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    @Transactional
    @Override
    public GeneralResult save(SupplierSaveEnter supplierSaveEnter) {
      //supplierSaveEnter参数值去空格
      SupplierSaveEnter enter = SesStringUtils.objStringTrim(supplierSaveEnter);
      checkSaveSupplierParameter(enter);
      //员工名称首位大写
      String SupplierName = SesStringUtils.upperCaseString(enter.getSupplierName());
      enter.setSupplierName(SupplierName);
        //邮箱去空格
        enter.setContactEmail(SesStringUtils.stringTrim(enter.getContactEmail()));

        OpeSupplier supplierSave = new OpeSupplier();

        BeanUtils.copyProperties(enter, supplierSave);
        supplierSave.setId(idAppService.getId(SequenceName.OPE_SUPPLIER));
        supplierSave.setDr(0);
        supplierSave.setStatus(SupplierStatusEnum.NORMAL.getValue());
        if(SesStringUtils.isNoneBlank(enter.getSupplierLatitude(),enter.getSupplierLongitude())){
            supplierSave.setSupplierLatitude(new BigDecimal(enter.getSupplierLatitude()));
            supplierSave.setSupplierLongitude(new BigDecimal(enter.getSupplierLongitude()));
        }
        supplierSave.setTenantId(enter.getTenantId());
        supplierSave.setUserId(enter.getUserId());
        supplierSave.setOverdueFlag(0);
        supplierSave.setCreatedBy(enter.getUserId());
        supplierSave.setCreatedTime(new Date());
        supplierSave.setUpdatedBy(enter.getUserId());
        supplierSave.setUpdatedTime(new Date());

        boolean save = supplierService.save(supplierSave);
        if (save) {
            saveSupplierTrace(SupplierEventEnum.CREATE.getValue(), supplierSave);
        }

        return new GeneralResult(enter.getRequestId());
    }

    @Transactional
    @Override
    public GeneralResult edit(SupplierEditEnter supplierSaveEnter) {
      //supplierSaveEnter参数值去空格
      SupplierSaveEnter enter = SesStringUtils.objStringTrim(supplierSaveEnter);
      checkSaveSupplierParameter(enter);
      //员工名称首位大写
      String factoryName = SesStringUtils.upperCaseString(enter.getSupplierName());

      enter.setSupplierName(factoryName);
        //邮箱去空格
        enter.setContactEmail(SesStringUtils.stringTrim(enter.getContactEmail()));

        OpeSupplier supplierEdit = new OpeSupplier();
        BeanUtils.copyProperties(enter, supplierEdit);
        supplierEdit.setUpdatedBy(enter.getUserId());
        supplierEdit.setUpdatedTime(new Date());

        boolean update = supplierService.updateById(supplierEdit);
        if (update) {
            saveSupplierTrace(SupplierEventEnum.MODIFY.getValue(), supplierEdit);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public SupplierResult details(IdEnter enter) {
        OpeSupplier supplier = supplierService.getById(enter.getId());
        SupplierResult supplierResult = new SupplierResult();
        Optional.ofNullable(supplier).ifPresent(s -> {
            BeanUtils.copyProperties(s, supplierResult);
            supplierResult.setRequestId(enter.getRequestId());
        });

        return supplierResult;
    }

    @Override
    public PageResult<SupplierResult> list(SupplierPage page) {
      if (page.getKeyword()!=null && page.getKeyword().length()>50){
        return PageResult.createZeroRowResult(page);
      }
        int count = supplierServiceMapper.listCount(page);
        if (count == 0) {
            return PageResult.createZeroRowResult(page);
        }
        List<SupplierResult> list = supplierServiceMapper.list(page);
        return PageResult.create(page, count, list);
    }

    @Override
    public GeneralResult saveSupplierTrace(String event, OpeSupplier supplier) {

        OpeSupplierTrace trace = new OpeSupplierTrace();
        trace.setId(idAppService.getId(SequenceName.OPE_SUPPLIER_TRACE));
        trace.setDr(0);
        trace.setSupplierId(supplier.getId());
        trace.setTenantId(supplier.getTenantId());
        trace.setUserId(supplier.getUserId());
        trace.setStatus(supplier.getStatus());
        trace.setEvent(event);
        trace.setEventTime(new Date());
        trace.setReason(supplier.getSupplierMemo());
        trace.setCreatedBy(supplier.getUpdatedBy());
        trace.setCreatedTime(new Date());
        trace.setUpdatedBy(supplier.getUpdatedBy());
        trace.setUpdatedTime(new Date());

        supplierTraceService.save(trace);
        return new GeneralResult();
    }
  private void   checkSaveSupplierParameter(SupplierSaveEnter enter){
    if (enter.getSupplierName().length() < 2 || enter.getSupplierName().length() > 40){
      throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_NAME_IS_NOT_ILLEGAL.getMessage());
    }
    if (enter.getSupplierAddress().length() < 2 || enter.getSupplierAddress().length() > 40){
      throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_ADDRESS_IS_NOT_ILLEGAL.getMessage());
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
    if (enter.getSupplierTag().length() < 2 || enter.getSupplierTag().length() > 20){
      throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_TAG_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_TAG_IS_NOT_ILLEGAL.getMessage());
    }
    if (enter.getBusinessNumber().length() < 2 || enter.getBusinessNumber().length() > 20){
      throw new SesWebRosException(ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getCode(), ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getMessage());
    }
  }
}
