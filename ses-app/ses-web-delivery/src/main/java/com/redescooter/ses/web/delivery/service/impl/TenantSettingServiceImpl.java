package com.redescooter.ses.web.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.tenant.TenantBussinessWeek;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.foundation.vo.tenant.SaveTenantConfigEnter;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.delivery.dao.base.CorUserProfileMapper;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.TenantSettingService;
import com.redescooter.ses.web.delivery.vo.TenantInforResult;
import com.redescooter.ses.web.delivery.vo.UpdateCustomerInfoEnter;
import com.redescooter.ses.web.delivery.vo.UpdateTenantConfigEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:TenantService
 * @description: TenantService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 16:51
 */
@Service
public class TenantSettingServiceImpl implements TenantSettingService {

    @Autowired
    private CorUserProfileMapper corUserProfileMapper;

    @Reference
    private TenantBaseService tenantBaseService;

    @Reference
    private CustomerService customerService;

    @Reference
    private UserBaseService userBaseService;

    /**
     * 店铺详细信息
     *
     * @param enter
     * @return
     */
    @Override
    public TenantInforResult tenantInfor(GeneralEnter enter) {
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryTenantResult queryTenantResult = tenantBaseService.queryTenantById(new IdEnter(enter.getTenantId()));

        TenantConfigInfoResult tenantConfigInfoResult = tenantBaseService.tenantConfigInfo(enter);

        TenantInforResult tenantInforResult = new TenantInforResult();
        BeanUtils.copyProperties(queryTenantResult, tenantInforResult);
        tenantInforResult.setTenantConfigId(tenantConfigInfoResult.getId());
        tenantInforResult.setStartWeek(tenantConfigInfoResult.getStartWeek());
        tenantInforResult.setEndWeek(tenantConfigInfoResult.getEndWeek());
        tenantInforResult.setBeginTime(DateUtil.getTimeStr(tenantConfigInfoResult.getBeginTime(), DateConstant.DEFAULT_DATETIME_FORMAT));
        tenantInforResult.setEndTime(DateUtil.getTimeStr(tenantConfigInfoResult.getEndTime(), DateConstant.DEFAULT_DATETIME_FORMAT));

        if (null != tenantConfigInfoResult.getEstimatedDuration() && 0 != tenantConfigInfoResult.getEstimatedDuration()) {
            tenantInforResult.setDuration(tenantConfigInfoResult.getEstimatedDuration());
        }
        if (null != tenantConfigInfoResult.getDistributionRange() && 0 != tenantConfigInfoResult.getDistributionRange()) {
            tenantInforResult.setAround(tenantConfigInfoResult.getDistributionRange());
        }
        tenantInforResult.setEmail(queryTenantResult.getEmail());
        tenantInforResult.setAvatar(corUserProfile.getPicture());
        tenantInforResult.setPageBootTips(corUserProfile.getPageBootTips());
        tenantInforResult.setIndustry(queryTenantResult.getTenantIndustry());
        return tenantInforResult;
    }

    /**
     * 更新店铺信息
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult updateTenantConfig(UpdateTenantConfigEnter enter) {
        List<String> weekList = new ArrayList<>();
        for (TenantBussinessWeek item : TenantBussinessWeek.values()) {
            weekList.add(item.getValue());
        }
        if (!weekList.contains(enter.getStartWeek()) || !weekList.contains(enter.getEndWeek())) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.TENANT_BUSINESS_TIME_FORMAT_IS_WRONG.getCode(), ExceptionCodeEnums.TENANT_BUSINESS_TIME_FORMAT_IS_WRONG.getMessage());
        }

        SaveTenantConfigEnter saveTenantConfigEnter = new SaveTenantConfigEnter();
        BeanUtils.copyProperties(enter, saveTenantConfigEnter);
        return tenantBaseService.saveTenantConfig(saveTenantConfigEnter);
    }

    /**
     * 更新客户信息
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult updateCustomerInfo(UpdateCustomerInfoEnter enter) {
        // 更新客户信息
        BaseCustomerEnter baseCustomerEnter = new BaseCustomerEnter();
        baseCustomerEnter.setId(enter.getId());
        if (StringUtils.isNotBlank(enter.getCustomerFirstName()) && StringUtils.isNotBlank(enter.getCustomerLastName())) {
//            baseCustomerEnter.setCustomerFirstName(enter.getCustomerFirstName());
//            baseCustomerEnter.setCustomerLastName(enter.getCustomerLastName());
//            baseCustomerEnter.setCustomerFullName(new StringBuffer().append(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
            baseCustomerEnter.setContactFirstName(enter.getCustomerFirstName());
            baseCustomerEnter.setContactLastName(enter.getCustomerLastName());
            baseCustomerEnter.setContactFullName(new StringBuffer().append(enter.getCustomerFirstName()).append(" ").append(enter.getCustomerLastName()).toString());
        }
        if (StringUtils.isNotBlank(enter.getTelephone())) {
            baseCustomerEnter.setTelephone(enter.getTelephone());
        }
        if (StringUtils.isNotBlank(enter.getAvatar())) {
            baseCustomerEnter.setPicture(enter.getAvatar());
        }

        customerService.updateCustomerInfo(baseCustomerEnter);

        // 更新个人信息
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
//        if (SesStringUtils.isNotBlank(enter.getCustomerFirstName()) && SesStringUtils.isNotBlank(enter.getCustomerLastName())) {
//            corUserProfile.setFirstName(enter.getCustomerFirstName());
//            corUserProfile.setLastName(enter.getCustomerLastName());
//            corUserProfile.setFullName(new StringBuffer().append(enter.getCustomerLastName()).append(" ").append(enter.getCustomerLastName()).toString());
//        }
        if (StringUtils.isNotBlank(enter.getTelephone())) {
            corUserProfile.setTelNumber1(enter.getTelephone());
        }
        if (StringUtils.isNotBlank(enter.getAvatar())) {
            corUserProfile.setPicture(enter.getAvatar());
        }
        corUserProfile.setUpdatedBy(enter.getUserId());
        corUserProfile.setUpdatedTime(new Date());
        corUserProfileMapper.updateById(corUserProfile);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户信息
     *
     * @param enter
     * @return
     */
    @Override
    public BaseCustomerResult customerInfor(GeneralEnter enter) {
        return customerService.customerInfo(new IdEnter(enter.getTenantId()));
    }

    /**
     * 关闭引导页
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult closePageBootTip(GeneralEnter enter) {
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_USER_ID, enter.getUserId());
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_TENANT_ID, enter.getTenantId());
        CorUserProfile corUserProfile = corUserProfileMapper.selectOne(corUserProfileQueryWrapper);
        if (corUserProfile == null) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        corUserProfile.setPageBootTips(Boolean.FALSE);
        corUserProfile.setUpdatedBy(enter.getUserId());
        corUserProfile.setUpdatedTime(new Date());
        corUserProfileMapper.updateById(corUserProfile);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开启所有引导页
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult openPageBootTipAll(GeneralEnter enter) {
        QueryWrapper<CorUserProfile> corUserProfileQueryWrapper = new QueryWrapper<>();
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_DR, 0);
        corUserProfileQueryWrapper.eq(CorUserProfile.COL_PAGE_BOOT_TIPS, Boolean.FALSE);
        List<CorUserProfile> corUserProfileList = corUserProfileMapper.selectList(corUserProfileQueryWrapper);
        if (CollectionUtils.isNotEmpty(corUserProfileList)) {
            corUserProfileList.forEach(item -> {
                item.setPageBootTips(Boolean.TRUE);
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());
            });
        }
        corUserProfileMapper.updateBatch(corUserProfileList);
        return new GeneralResult(enter.getRequestId());
    }
}
