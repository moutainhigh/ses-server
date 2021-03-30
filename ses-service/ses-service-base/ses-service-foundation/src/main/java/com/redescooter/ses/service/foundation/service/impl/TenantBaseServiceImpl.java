package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.tenant.TenanNodeEventEnum;
import com.redescooter.ses.api.common.enums.tenant.TenantBussinessStatus;
import com.redescooter.ses.api.common.enums.tenant.TenantBussinessWeek;
import com.redescooter.ses.api.common.enums.tenant.TenantStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.QueryTenantNodeResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.foundation.vo.tenant.SaveTenantConfigEnter;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.constant.TenantDefaultValue;
import com.redescooter.ses.service.foundation.dao.AccountBaseServiceMapper;
import com.redescooter.ses.service.foundation.dao.TenantBaseServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantConfigMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantNodeMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:TenantBaseServiceImpl
 * @description: TenantBaseServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 14:01
 */
@DubboService
public class TenantBaseServiceImpl implements TenantBaseService {

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Autowired
    private PlaTenantNodeMapper plaTenantNodeMapper;

    @Autowired
    private PlaTenantConfigMapper plaTenantConfigMapper;

    @Autowired
    private AccountBaseServiceMapper accountBaseServiceMapper;

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private TenantBaseServiceMapper tenantBaseServiceMapper;

    /**
     * 保存租户
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public Long saveTenant(DateTimeParmEnter<BaseCustomerResult> enter) {
        // 保存租户
        PlaTenant plaTenant = buildTenantSingle(enter);
        plaTenantMapper.insert(plaTenant);

        // 租户节点
        enter.getT().setTenantId(plaTenant.getId());
        saveTenantNode(enter, TenanNodeEventEnum.CREAT.getValue());
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveTenantNode(DateTimeParmEnter<BaseCustomerResult> enter, String event) {
        PlaTenantNode plaTenantNode = buildPlaTenantNodeSingle(enter, event);
        plaTenantNodeMapper.insert(plaTenantNode);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 查询租户节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryTenantNodeResult> queryTenantNdoe(IdEnter enter) {
        QueryWrapper<PlaTenantNode> plaTenantNodeQueryWrapper = new QueryWrapper<>();
        plaTenantNodeQueryWrapper.eq(PlaTenantNode.COL_TENANT_ID, enter.getId());
        plaTenantNodeQueryWrapper.orderByAsc(PlaTenantNode.COL_CREATE_TIME);
        plaTenantNodeQueryWrapper.eq(PlaTenantNode.COL_DR, 0);
        List<PlaTenantNode> plaTenantNodeList = plaTenantNodeMapper.selectList(plaTenantNodeQueryWrapper);

        List<QueryTenantNodeResult> resultList = new ArrayList<>();
        plaTenantNodeList.forEach(item -> {
            QueryTenantNodeResult queryTenantNodeResult = QueryTenantNodeResult.builder()
                    .id(item.getId())
                    .event(item.getEvent())
                    .eventTime(item.getEventTime())
                    .memo(item.getMemo())
                    .tenantId(item.getTenantId())
                    .createBy(item.getCreateBy())
                    .build();
            resultList.add(queryTenantNodeResult);
        });
        return resultList;
    }

    /**
     * 保存租户默认配置
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveTenantConfig(SaveTenantConfigEnter enter) {

        PlaTenantConfig plaTenantConfig = buildTenantConfigSingle(enter.getInputTenantId(), enter);
        // 保存
        if ((enter.getTenantConfigId() == null) || (enter.getTenantConfigId() == 0)) {
            plaTenantConfig.setId((idAppService.getId(SequenceName.PLA_TENANT_CONFIG)));
            plaTenantConfig.setCreatedBy(enter.getUserId());
            plaTenantConfig.setCreatedTime(new Date());
        } else {
            // 修改
            plaTenantConfig.setId(enter.getTenantConfigId());
        }
        plaTenantConfigMapper.insertOrUpdateSelective(plaTenantConfig);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public TenantConfigInfoResult tenantConfigInfo(GeneralEnter enter) {
        QueryWrapper<PlaTenantConfig> plaTenantConfigQueryWrapper = new QueryWrapper<>();
        plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_TENANT_ID, enter.getTenantId());
        plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_DR, 0);
        PlaTenantConfig plaTenantConfig = plaTenantConfigMapper.selectOne(plaTenantConfigQueryWrapper);
        if (plaTenantConfig == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        TenantConfigInfoResult tenantConfigInfoResult = new TenantConfigInfoResult();
        BeanUtils.copyProperties(plaTenantConfig, tenantConfigInfoResult);
        return tenantConfigInfoResult;
    }

    /**
     * 查询租户信息
     *
     * @param enter
     * @return
     */
    @Override
    public QueryTenantResult queryTenantById(IdEnter enter) {
        PlaTenant plaTenant = plaTenantMapper.selectById(enter.getId());
        if (plaTenant == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        QueryTenantResult result = new QueryTenantResult();
        BeanUtils.copyProperties(plaTenant, result);
        return result;
    }

    /**
     * 查询租户状态
     *
     * @return
     */
    //todo要删除
    @Override
    public Map<String, Integer> accountCountStatus() {
        List<CountByStatusResult> countByStatusResult = accountBaseServiceMapper.accountCountStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResult) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TenantStatusEnum status : TenantStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 创建司机账户对租户中司机的个数进行累计
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateDriverCount(GeneralEnter enter) {
        tenantBaseServiceMapper.updateDriverCount(enter);
        return new GeneralResult(enter.getRequestId());
    }

    private PlaTenantConfig buildTenantConfigSingle(Long tennatId, SaveTenantConfigEnter enter) {

        PlaTenantConfig tenantConfig = null;
        PlaTenant tenant = null;
        QueryUserResult queryUserResult = null;
        if (!enter.getTenantDefaultConfig()) {
            queryUserResult = userBaseService.queryUserById(enter);
            // 餐厅 配送范围 配送时间 参数过滤 修改
            if (AccountTypeEnums.WEB_RESTAURANT.getAccountType().equals(queryUserResult.getUserType())) {
                // 参数校验
                if (enter.getDistributionRange() == null || enter.getDistributionRange() == 0) {
                    throw new FoundationException(ExceptionCodeEnums.DISTRIBUTIONRANGE_IS_EMPTY.getCode(), ExceptionCodeEnums.DISTRIBUTIONRANGE_IS_EMPTY.getMessage());
                }
                if (enter.getEstimatedDuration() == null || enter.getEstimatedDuration() == 0) {
                    throw new FoundationException(ExceptionCodeEnums.ESTIMATEDDURATION_IS_EMPTY.getCode(), ExceptionCodeEnums.ESTIMATEDDURATION_IS_EMPTY.getMessage());
                }
            }
        }

        //配送范围10KM 配送时间30min 超时预警时间15min
        if (enter.getTenantDefaultConfig()) {
            tenantConfig = new PlaTenantConfig();

            tenant = plaTenantMapper.selectById(tennatId);
            if (tenant == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            tenantConfig.setStartWeek(TenantBussinessWeek.MON.getValue());
            tenantConfig.setEndWeek(TenantBussinessWeek.SUN.getValue());
            tenantConfig.setBeginTime(DateUtil.parse(TenantDefaultValue.BEGIN_TIME, DateConstant.DEFAULT_DATETIME_FORMAT));
            tenantConfig.setEndTime(DateUtil.parse(TenantDefaultValue.END_TIME, DateConstant.DEFAULT_DATETIME_FORMAT));
            tenantConfig.setDistributionRange(TenantDefaultValue.DISTRIBUTION_RANGE);
            tenantConfig.setEstimatedDuration(TenantDefaultValue.ESTIMATED_DURATION);
            // 租户信息初始化
            tenantConfig.setDr(0);
            tenantConfig.setAddress(tenant.getAddress());
            tenantConfig.setStatus(TenantBussinessStatus.OPEN.getValue());
            tenantConfig.setLanguage(enter.getLanguage());
            tenantConfig.setLatitude(tenant.getLatitude());
            tenantConfig.setLongitude(tenant.getLongitude());
            tenantConfig.setTenantId(tenant.getId());
            tenantConfig.setTimeoutExpectde(TenantDefaultValue.TIMEOUT_EXPECTDE);
            tenantConfig.setTimeZone(tenant.getTimeZone());
            tenantConfig.setUpdatedBy(enter.getUserId());
            tenantConfig.setUpdatedTime(new Date());
        } else {
            // 进行租户配置的查询
            PlaTenantConfig plaTenantConfig = plaTenantConfigMapper.selectById(enter.getTenantConfigId());
            if (plaTenantConfig == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_CONFIG_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_CONFIG_IS_NOT_EXIST.getMessage());
            }
            tenantConfig = plaTenantConfig;

            tenant = plaTenantMapper.selectById(enter.getTenantId());
            if (tenant == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            tenantConfig.setStartWeek(enter.getStartWeek());
            tenantConfig.setEndWeek(enter.getEndWeek());
            tenantConfig.setBeginTime(DateUtil.parse(enter.getBeginTime(), DateConstant.DEFAULT_DATETIME_FORMAT));
            tenantConfig.setEndTime(DateUtil.parse(enter.getEndTime(), DateConstant.DEFAULT_DATETIME_FORMAT));
            if (AccountTypeEnums.WEB_RESTAURANT.getAccountType().equals(queryUserResult.getUserType())) {
                tenantConfig.setDistributionRange(enter.getDistributionRange());
                tenantConfig.setEstimatedDuration(enter.getEstimatedDuration());
            }
        }
        return tenantConfig;
    }

    private PlaTenantNode buildPlaTenantNodeSingle(DateTimeParmEnter<BaseCustomerResult> enter, String event) {
        PlaTenantNode plaTenantNode = new PlaTenantNode();
        plaTenantNode.setId(idAppService.getId(SequenceName.PLA_TENANT_NODE));
        plaTenantNode.setDr(0);
        plaTenantNode.setTenantId(enter.getT().getTenantId());
        plaTenantNode.setEvent(event);
        plaTenantNode.setEventTime(new Date());
        plaTenantNode.setMemo(null);
        plaTenantNode.setCreateBy(enter.getUserId());
        plaTenantNode.setCreateTime(new Date());
        plaTenantNode.setUpdateBy(enter.getUserId());
        plaTenantNode.setUpdateTime(new Date());
        return plaTenantNode;
    }

    private PlaTenant buildTenantSingle(DateTimeParmEnter<BaseCustomerResult> enter) {
        PlaTenant plaTenant = new PlaTenant();
        plaTenant.setId(idAppService.getId(SequenceName.PLA_TENANT));
        plaTenant.setDr(0);
        plaTenant.setPId(0L);
        plaTenant.setTenantName(enter.getT().getCompanyName());
        plaTenant.setEmail(enter.getT().getEmail());
        plaTenant.setStatus(TenantStatusEnum.INOPERATION.getValue());
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
