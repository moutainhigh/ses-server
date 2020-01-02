package com.redescooter.ses.service.hub.source.consumer.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.customer.ConsumerUserProfileService;
import com.redescooter.ses.api.hub.vo.QueryUserProfileResult;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.consumer.dao.ConUserProfileMapper;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserProfile;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CorporateAccountProServiceImpl
 * @description: CorporateAccountProServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:31
 */
@DS("consumer")
@Service
public class ConsumerUserProfileServiceImpl implements ConsumerUserProfileService {


    @Autowired
    private ConUserProfileMapper conUserProfileMapper;

    @Reference
    private UserProfileProService userProfileProService;

    /**
     * 查询个人信息
     *
     * @param enter id为 userProfile Id
     * @return
     */
    @Override
    public QueryUserProfileResult queryUserProfile(IdEnter enter) {

        QueryWrapper<ConUserProfile> conUserProfileQueryWrapper = new QueryWrapper<>();
        if (enter.getUserId() != null || enter.getUserId() != 0) {
            conUserProfileQueryWrapper.eq(ConUserProfile.COL_USER_ID, enter.getUserId());
        }
        if (enter.getId() != null || enter.getId() != 0) {
            conUserProfileQueryWrapper.eq("ID", enter.getId());
        }
        ConUserProfile conUserProfile = conUserProfileMapper.selectOne(conUserProfileQueryWrapper);
        if (conUserProfile == null) {
            throw new SeSHubException(ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_PROFILE_IS_NOT_EXIST.getMessage());
        }
        QueryUserProfileResult queryUserProfileResult = new QueryUserProfileResult();
        BeanUtils.copyProperties(conUserProfile, queryUserProfileResult);
        return queryUserProfileResult;
    }
}
