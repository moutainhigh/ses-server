package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dao.DistributorMapper;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.DealerService;
import com.redescooter.ses.web.website.service.base.SiteDistributorService;
import com.redescooter.ses.web.website.vo.distributor.AddDealerEnter;
import com.redescooter.ses.web.website.vo.distributor.DealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.MapDealerDetailsResult;
import com.redescooter.ses.web.website.vo.distributor.QueryDealerEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 12:56 下午
 * @Description 经销商服务实现
 **/
@Slf4j
@Service
public class DealerServiceImpl implements DealerService {

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private SiteDistributorService siteDistributorService;

    @Autowired
    private DistributorMapper distributorMapper;

    /**
     * 创建经销商
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addDistributor(AddDealerEnter enter) {
        SiteDistributor addDistributorVO = new SiteDistributor();
        addDistributorVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
        addDistributorVO.setDr(Constant.DR_FALSE);
        addDistributorVO.setStatus(CommonStatusEnums.NORMAL.getValue());
        addDistributorVO.setCode(new StringBuffer().append("DR_").append(MainCode.generateByShuffle()).toString());
        addDistributorVO.setName(enter.getName());
        addDistributorVO.setLogoUrl(enter.getLogoUrl());
        addDistributorVO.setCountryCode(enter.getCountryCode());
        addDistributorVO.setTel(enter.getTel());
        addDistributorVO.setEmail(enter.getEmail());
        addDistributorVO.setAddress(enter.getAddress());
        addDistributorVO.setLongitude(enter.getLongitude());
        addDistributorVO.setLatitude(enter.getLatitude());
        addDistributorVO.setCp(enter.getCp());
        addDistributorVO.setCity(enter.getCity());
        addDistributorVO.setArea(enter.getArea());
        if (StringUtils.isNotBlank(enter.getRemark())) {
            addDistributorVO.setRemark(enter.getRemark());
        }
        addDistributorVO.setType(enter.getType());
        addDistributorVO.setSynchronizeFlag(false);
        addDistributorVO.setRevision(0);
        addDistributorVO.setSynchronizeFlag(false);
        addDistributorVO.setCreatedBy(enter.getUserId());
        addDistributorVO.setCreatedTime(new Date());
        addDistributorVO.setUpdatedBy(enter.getUserId());
        addDistributorVO.setUpdatedTime(new Date());

        siteDistributorService.save(addDistributorVO);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取经销商详情
     *
     * @param enter
     */
    @Override
    public DealerDetailsResult getDistributorDetails(IdEnter enter) {
        SiteDistributor distributor = siteDistributorService.getById(enter.getId());
        DealerDetailsResult result = new DealerDetailsResult();

        if (distributor != null) {
            BeanUtils.copyProperties(distributor, result);
            result.setLongitude(distributor.getLongitude());
            result.setLatitude(distributor.getLatitude());
            result.setRequestId(enter.getRequestId());
        }
        return result;
    }

    /**
     * 经销商列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<MapDealerDetailsResult> getDistributorList(QueryDealerEnter enter) {
        return distributorMapper.getlistOrderByDistance(enter);
    }
}
