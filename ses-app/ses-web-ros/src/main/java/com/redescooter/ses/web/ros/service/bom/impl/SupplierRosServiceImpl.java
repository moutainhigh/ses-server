package com.redescooter.ses.web.ros.service.bom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
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
import com.redescooter.ses.web.ros.dao.base.OpeSupplierMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierTraceService;
import com.redescooter.ses.web.ros.service.bom.SupplierRosService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.supplier.SupplierEditEnter;
import com.redescooter.ses.web.ros.vo.supplier.SupplierPage;
import com.redescooter.ses.web.ros.vo.supplier.SupplierResult;
import com.redescooter.ses.web.ros.vo.supplier.SupplierSaveEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierRosServiceImpl implements SupplierRosService {

    @Autowired
    private OpeSupplierService supplierService;

    @Autowired
    private OpeSupplierMapper opeSupplierMapper;

    @Autowired
    private OpeSupplierTraceService supplierTraceService;

    @Autowired
    private SupplierServiceMapper supplierServiceMapper;

    @DubboReference
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

    public Boolean checkMail(String mail,String idStr) {
        QueryWrapper<OpeSupplier> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSupplier.COL_CONTACT_EMAIL, mail);
        wrapper.eq(OpeSupplier.COL_DR, 0);
        if(!Strings.isNullOrEmpty(idStr)){
            // id?????????????????????????????????????????????????????????
            wrapper.ne(OpeSupplier.COL_ID, Long.parseLong(idStr));
        }
        Boolean mailBoolean = opeSupplierMapper.selectCount(wrapper) > 0 ? Boolean.FALSE  : Boolean.TRUE;
        return mailBoolean;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SupplierSaveEnter supplierSaveEnter) {
        //supplierSaveEnter??????????????????
        SupplierSaveEnter enter = SesStringUtils.objStringTrim(supplierSaveEnter);
        checkSaveSupplierParameter(enter);
        Boolean mailBoolean = checkMail(enter.getContactEmail(),"");
        if (!mailBoolean){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        //????????????????????????
        String factoryName = SesStringUtils.upperCaseString(enter.getSupplierName());
        if (StringUtils.isNotEmpty(enter.getContactFirstName())) {
            String firstName = SesStringUtils.upperCaseString(enter.getContactFirstName());
            enter.setContactFirstName(firstName);
        }
        if (StringUtils.isNotEmpty(enter.getContactLastName())) {
            String lastName = SesStringUtils.upperCaseString(enter.getContactLastName());
            enter.setContactLastName(lastName);
        }
        enter.setSupplierName(factoryName);
        //???????????????
        enter.setContactEmail(SesStringUtils.stringTrim(enter.getContactEmail()));

        OpeSupplier supplierSave = new OpeSupplier();

        BeanUtils.copyProperties(enter, supplierSave);
        supplierSave.setId(idAppService.getId(SequenceName.OPE_SUPPLIER));
        supplierSave.setDr(0);
        supplierSave.setStatus(SupplierStatusEnum.NORMAL.getValue());
        if (SesStringUtils.isNoneBlank(enter.getSupplierLatitude(), enter.getSupplierLongitude())) {
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

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult edit(SupplierEditEnter supplierSaveEnter) {
      //supplierSaveEnter??????????????????
      SupplierSaveEnter enter = SesStringUtils.objStringTrim(supplierSaveEnter);
      checkSaveSupplierParameter(enter);

        Boolean mailBoolean = checkMail(enter.getContactEmail(),supplierSaveEnter.getId().toString());
        if (!mailBoolean){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
      //????????????????????????
      String factoryName = SesStringUtils.upperCaseString(enter.getSupplierName());

      enter.setSupplierName(factoryName);
        //???????????????
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
        if (NumberUtil.notNullAndGtFifty(page.getKeyword())) {
            return PageResult.createZeroRowResult(page);
        }
        int count = supplierServiceMapper.listCount(page);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(page);
        }
        List<SupplierResult> list = supplierServiceMapper.list(page);
        return PageResult.create(page, count, list);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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

    private void checkSaveSupplierParameter(SupplierSaveEnter enter) {
        if (NumberUtil.ltTwoOrGtForty(enter.getSupplierName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_NAME_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtForty(enter.getSupplierAddress().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getContactFullName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtFifty(enter.getContactEmail().length()) || !enter.getContactEmail().contains("@")) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getContactPhone().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getSupplierTag().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.SUPPLIER_TAG_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.SUPPLIER_TAG_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getBusinessNumber().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getCode(), ExceptionCodeEnums.ILLEGAL_BUSINESS_LICENSE_NUMBER.getMessage());
        }
    }
}
