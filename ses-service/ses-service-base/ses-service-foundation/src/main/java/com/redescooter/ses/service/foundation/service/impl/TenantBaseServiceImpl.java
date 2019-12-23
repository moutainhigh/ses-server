package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.common.enums.tenant.TenanNodeEvent;
import com.redescooter.ses.api.common.enums.tenant.TenantStatus;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.SaveTenantConfigEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.constant.TenantDefaultValue;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantConfigMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantNodeMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName:TenantBaseServiceImpl
 * @description: TenantBaseServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 14:01
 */
@Service
public class TenantBaseServiceImpl implements TenantBaseService {

    @Reference
    private IdAppService idAppService;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Autowired
    private PlaTenantNodeMapper plaTenantNodeMapper;

    @Autowired
    private PlaTenantConfigMapper plaTenantConfigMapper;

    /**
     * 保存租户
     *
     * @param enter
     * @return
     */
    @Override
    public Long saveTenant(DateTimeParmEnter<BaseCustomerResult> enter) {
        // 保存租户
        PlaTenant plaTenant = buildTenantSingle(enter);
        plaTenantMapper.insert(plaTenant);

        // 租户节点
        enter.getT().setTenantId(plaTenant.getId());
        saveTenantNode(enter);
        // 租户配置
        SaveTenantConfigEnter saveTenantConfigEnter = SaveTenantConfigEnter.builder().inputTenantId(plaTenant.getId()).tenantDefaultConfig(Boolean.TRUE).build();
        saveTenantConfig(saveTenantConfigEnter);

        return plaTenant.getId();
    }

    /**
     * 保存租户节点
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveTenantNode(DateTimeParmEnter<BaseCustomerResult> enter) {
        PlaTenantNode plaTenantNode = buildPlaTenantNodeSingle(enter);
        plaTenantNodeMapper.insert(plaTenantNode);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 保存租户默认配置
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveTenantConfig(SaveTenantConfigEnter enter) {
        PlaTenantConfig save = new PlaTenantConfig();
        //生成默认配置
        if (enter.getTenantDefaultConfig()) {
            buildTenantConfigSingle(save, enter.getInputTenantId(), enter);
        } else {
            // 参数校验
//            if (enter.getTimeoutExpectde() == null || enter.getTimeoutExpectde() == 0) {
//                throw new FoundationException(ExceptionCodeEnums.TIMEOUTEXPECTDE_IS_EMPTY.getCode(), ExceptionCodeEnums.TIMEOUTEXPECTDE_IS_EMPTY.getMessage());
//            }
//            if (enter.getDistributionRange() == null || enter.getDistributionRange() == 0) {
//                throw new FoundationException(ExceptionCodeEnums.DISTRIBUTIONRANGE_IS_EMPTY.getCode(), ExceptionCodeEnums.DISTRIBUTIONRANGE_IS_EMPTY.getMessage());
//            }
//            if (enter.getEstimatedDuration() == null || enter.getEstimatedDuration() == 0) {
//                throw new FoundationException(ExceptionCodeEnums.ESTIMATEDDURATION_IS_EMPTY.getCode(), ExceptionCodeEnums.ESTIMATEDDURATION_IS_EMPTY.getMessage());
//            }

            //根据租户id查看租户配置是否存在
            BeanUtils.copyProperties(enter, save);
            // 保存
            if ((enter.getTenantConfigId() == null) || (enter.getTenantConfigId() == 0)) {
                save.setId((idAppService.getId(SequenceName.PLA_TENANT_CONFIG)));
                save.setCreatedBy(enter.getUserId());
                save.setCreatedTime(new Date());
            } else {
                // 修改
                save.setId(enter.getTenantConfigId());
            }
            save.setUpdatedBy(enter.getUserId());
            save.setUpdatedTime(new Date());
        }
        plaTenantConfigMapper.insertOrUpdateSelective(save);

        return new GeneralResult(enter.getRequestId());
    }

    private PlaTenantConfig buildTenantConfigSingle(PlaTenantConfig tenantConfig, Long tennatId, GeneralEnter enter) {
        PlaTenant tenant = plaTenantMapper.selectById(tennatId);
        if (tenant == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        tenantConfig.setId(idAppService.getId(SequenceName.PLA_TENANT_CONFIG));
        tenantConfig.setDr(0);
        tenantConfig.setAddress(tenant.getAddress());
        //配送范围10KM 配送时间30min 超时预警时间15min
        tenantConfig.setDistributionRange(TenantDefaultValue.DISTRIBUTION_RANGE);
        tenantConfig.setEstimatedDuration(TenantDefaultValue.ESTIMATED_DURATION);
        tenantConfig.setLanguage(enter.getLanguage());
        tenantConfig.setLatitude(tenant.getLatitude());
        tenantConfig.setLongitude(tenant.getLongitude());
        tenantConfig.setTenantId(tenant.getId());
        tenantConfig.setTimeoutExpectde(TenantDefaultValue.TIMEOUT_EXPECTDE);
        tenantConfig.setTimeZone(tenant.getTimeZone());
        tenantConfig.setCreatedBy(enter.getUserId());
        tenantConfig.setCreatedTime(new Date());
        tenantConfig.setUpdatedBy(enter.getUserId());
        tenantConfig.setUpdatedTime(new Date());
        return tenantConfig;
    }

    private PlaTenantNode buildPlaTenantNodeSingle(DateTimeParmEnter<BaseCustomerResult> enter) {
        PlaTenantNode plaTenantNode=new PlaTenantNode();
        plaTenantNode.setId(idAppService.getId(SequenceName.PLA_TENANT_NODE));
        plaTenantNode.setDr(0);
        plaTenantNode.setTenantId(enter.getT().getTenantId());
        plaTenantNode.setEvent(TenanNodeEvent.CREAT.getValue());
        plaTenantNode.setEventTime(new Date());
        plaTenantNode.setMemo(null);
        plaTenantNode.setCreateBy(enter.getUserId());
        plaTenantNode.setCreateTime(new Date());
        plaTenantNode.setUpdateBy(enter.getUserId());
        plaTenantNode.setUpdateTime(new Date());
        return plaTenantNode;
    }

    private PlaTenant buildTenantSingle(DateTimeParmEnter<BaseCustomerResult> enter) {
        PlaTenant plaTenant=new PlaTenant();
        plaTenant.setId(idAppService.getId(SequenceName.PLA_TENANT));
        plaTenant.setDr(0);
        plaTenant.setPId(0L);
        plaTenant.setTenantName(enter.getT().getCompanyName());
        plaTenant.setEmail(enter.getT().getEmail());
        plaTenant.setStatus(TenantStatus.INOPERATION.getCode());
        plaTenant.setCountryId(enter.getT().getCountry());
        plaTenant.setCityId(enter.getT().getCity());
        plaTenant.setDistrustId(enter.getT().getDistrust());
        plaTenant.setAddress(enter.getT().getAddress());
        plaTenant.setDriverCounts(0);
        plaTenant.setSalesId(enter.getT().getSalesId());
        //todo 负责人姓名 修改
        plaTenant.setTenantSource(enter.getT().getCustomerSource());
        plaTenant.setTenantType(enter.getT().getCustomerType());
        plaTenant.setTenantIndustry(enter.getT().getIndustryType());
        plaTenant.setContact(enter.getT().getContactFirstName());

        plaTenant.setLongitude(enter.getT().getLongitude());
        plaTenant.setLatitude(enter.getT().getLatitude());
        plaTenant.setTel1(enter.getT().getTelephone());
        plaTenant.setEmail(enter.getT().getEmail());
        plaTenant.setTimeZone(enter.getT().getTimeZone());
        plaTenant.setEffectiveTime(enter.getStartDateTime());
        plaTenant.setExpireTime(enter.getEndDateTime());
        plaTenant.setCreatedBy(enter.getUserId());
        plaTenant.setCreatedTime(new Date());
        plaTenant.setUpdatedBy(enter.getUserId());
        plaTenant.setUpdatedTime(new Date());
        return plaTenant;
    }

}
