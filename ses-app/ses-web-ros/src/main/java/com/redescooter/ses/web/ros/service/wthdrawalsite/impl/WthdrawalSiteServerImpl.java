package com.redescooter.ses.web.ros.service.wthdrawalsite.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeWithdrawalSite;
import com.redescooter.ses.web.ros.service.base.OpeWithdrawalSiteService;
import com.redescooter.ses.web.ros.service.wthdrawalsite.WthdrawalSiteServer;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteEditEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteResult;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteSaveEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.isSwitchEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class WthdrawalSiteServerImpl implements WthdrawalSiteServer {

    @Autowired
    private OpeWithdrawalSiteService withdrawalSiteService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 保存门店信息
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(WthdrawalSiteSaveEnter enter) {
        OpeWithdrawalSite saveVO = new OpeWithdrawalSite();
        saveVO.setId(idAppService.getId(SequenceName.OPE_WITHDRAWAL_SITE));
        saveVO.setDr(Constant.DR_FALSE);
        saveVO.setStatus(0);
        saveVO.setTenantId(new Long("0"));
        saveVO.setUserId(new Long("0"));
        saveVO.setBusinessStatus(0);
        saveVO.setType(enter.getType());
        saveVO.setStreetNumber(enter.getStreetNumber());
        saveVO.setStreetName(enter.getStreetName());
        saveVO.setContactFirst(enter.getContactFirst());
        saveVO.setContactLast(enter.getContactLast());
        saveVO.setContantFullName(enter.getContantFullName());
        saveVO.setEmail(enter.getEmail());
        saveVO.setPhoneNumber(enter.getPhoneNumber());
        saveVO.setOpenTime(enter.getOpenTime());
        saveVO.setCloseTime(enter.getCloseTime());
        saveVO.setOtherParams(enter.getOtherParams());
        saveVO.setStoreCode(SesStringUtils.getlinkNo());
        saveVO.setStoreName(enter.getStoreName());
        saveVO.setCountry(enter.getCountry());
        saveVO.setCity(enter.getCity());
        saveVO.setArea(enter.getArea());
        saveVO.setCodePostal(enter.getCodePostal());
        saveVO.setAddress(enter.getAddress());
        saveVO.setRemarks(enter.getRemarks());
        saveVO.setCreatedBy(enter.getUserId());
        saveVO.setCreatedTime(new Date());
        saveVO.setUpdatedBy(enter.getUserId());
        saveVO.setUpdatedTime(new Date());
        saveVO.setDef1(enter.getDef1());
        withdrawalSiteService.save(saveVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 门店编辑
     *
     * @param enter
     * @param id
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult edit(WthdrawalSiteEditEnter enter) {
        OpeWithdrawalSite editVO = new OpeWithdrawalSite();
        editVO.setId(enter.getId());
        editVO.setBusinessStatus(enter.getBusinessStatus());
        editVO.setType(enter.getType());
        editVO.setStreetNumber(enter.getStreetNumber());
        editVO.setStreetName(enter.getStreetName());
        editVO.setContactFirst(enter.getContactFirst());
        editVO.setContactLast(enter.getContactLast());
        editVO.setContantFullName(enter.getContantFullName());
        editVO.setEmail(enter.getEmail());
        editVO.setPhoneNumber(enter.getPhoneNumber());
        editVO.setOpenTime(enter.getOpenTime());
        editVO.setCloseTime(enter.getCloseTime());
        editVO.setOtherParams(enter.getOtherParams());
        editVO.setStoreCode(SesStringUtils.getlinkNo());
        editVO.setStoreName(enter.getStoreName());
        editVO.setCountry(enter.getCountry());
        editVO.setCity(enter.getCity());
        editVO.setArea(enter.getArea());
        editVO.setCodePostal(enter.getCodePostal());
        editVO.setAddress(enter.getAddress());
        editVO.setRemarks(enter.getRemarks());
        editVO.setUpdatedBy(enter.getUserId());
        editVO.setUpdatedTime(new Date());
        editVO.setDef1(enter.getDef1());
        withdrawalSiteService.updateById(editVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 门店详情
     *
     * @param enter
     * @param id
     * @return
     */
    @Override
    public WthdrawalSiteResult details(IdEnter enter) {
        WthdrawalSiteResult result = new WthdrawalSiteResult();
        OpeWithdrawalSite withdrawalSite = withdrawalSiteService.getOne(new LambdaQueryWrapper<OpeWithdrawalSite>()
                .eq(OpeWithdrawalSite::getDr, Constant.DR_FALSE)
                .eq(OpeWithdrawalSite::getId, enter.getId())
                .last("limit 1"));

        if (StringManaConstant.entityIsNotNull(withdrawalSite)) {
            BeanUtils.copyProperties(withdrawalSite, result);
        }

        return result;
    }

    /**
     * 门店列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<WthdrawalSiteResult> list(GeneralEnter enter) {
        List<WthdrawalSiteResult> results = new ArrayList<>();
        WthdrawalSiteResult withdrawalSite = null;

        List<OpeWithdrawalSite> lists = withdrawalSiteService.list(new LambdaQueryWrapper<OpeWithdrawalSite>()
                .eq(OpeWithdrawalSite::getDr, Constant.DR_FALSE));

        if (!CollectionUtils.isEmpty(lists)) {

            for (OpeWithdrawalSite site : lists) {
                withdrawalSite = new WthdrawalSiteResult();
                BeanUtils.copyProperties(site, withdrawalSite);
                results.add(withdrawalSite);
            }
        }
        return results;
    }

    /**
     * 营业开关
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult isSwitch(isSwitchEnter enter) {
        OpeWithdrawalSite isSwitchVO = new OpeWithdrawalSite();
        isSwitchVO.setId(enter.getId());
        isSwitchVO.setBusinessStatus(enter.getBusinessStatus());
        withdrawalSiteService.updateById(isSwitchVO);
        return new GeneralResult(enter.getRequestId());
    }
}
