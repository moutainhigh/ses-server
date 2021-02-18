package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.account.LoginPushStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.JpushUserService;
import com.redescooter.ses.api.foundation.vo.message.JpushUserEnter;
import com.redescooter.ses.api.foundation.vo.message.JpushUserResult;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.JpushUserServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaJpushUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dm.JpushUserData;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description: JpushUserServiceImpl
 * author: jerry.li
 * create: 2019-05-22 11:40
 */
@Slf4j
@DubboService
public class JpushUserServiceImpl implements JpushUserService {

    @Autowired
    private PlaJpushUserMapper jpushUserMapper;

    @Autowired
    private JpushUserServiceMapper jpushUserServiceMapper;

    @DubboReference
    private IdAppService idSerService;

    @Autowired
    private PlaUserMapper userMapper;

    //登入 ---0
    private static String LOGIN = "LOGIN";
    //登出 ---1
    private static String LOGOUT = "LOGOUT";

    /**
     * 插入或者更新极光用户关系
     * TODO （调用此方法，根据业务主键不可以进行传递过来，故不做主键维度判断）
     * 1.根据极光注册id判断该设备是否存在
     * 2.如果存在做更新
     * 3.不存在做插入
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(JpushUserEnter enter) {

        if (StringUtils.isBlank(enter.getRegistrationId())) {
            return new GeneralResult(enter.getRequestId());
        }

        PlaJpushUser jpushUser = null;
        JpushUserData jpushUserData = jpushUserServiceMapper.queryJpushUserByRegistrationId(enter.getRegistrationId(), enter.getUserId());


        // 如果发现当前用户绑定有其他设备 直接删除设备绑定的令牌
        List<PlaJpushUser> plaJpushUserList = jpushUserMapper
                .selectList(new QueryWrapper<PlaJpushUser>().eq(PlaJpushUser.COL_USER_ID, enter.getUserId()));
        if (CollectionUtils.isNotEmpty(plaJpushUserList)) {
            List<Long> ids = plaJpushUserList.stream().map(PlaJpushUser::getId).collect(Collectors.toList());
            jpushUserMapper
                    .delete(new LambdaQueryWrapper<PlaJpushUser>()
                            .in(PlaJpushUser::getId, ids));
        }

        //用户首次登录极光
        if (jpushUserData == null) {
            jpushUser = new PlaJpushUser();
            BeanUtils.copyProperties(enter, jpushUser);
            jpushUser.setId(idSerService.getId(SequenceName.PLA_JPUSHUSER));
            jpushUser.setStatus(enter.getStatus());
            jpushUser.setUserId(enter.getStatus().equals(LoginPushStatusEnums.LOGIN_IN.getValue()) ? enter.getUserId() : null);
            jpushUser.setStatusCode(enter.getStatus().equals(LoginPushStatusEnums.LOGIN_IN.getValue()) ? LOGIN : LOGOUT);
            jpushUser.setPlatformType(enter.getPlatformType());
            jpushUser.setAudienceType(enter.getAudienceType());
            jpushUser.setCreateBy(enter.getUserId());
            jpushUser.setCreateTime(new Date());
            jpushUser.setUpdateBy(enter.getUserId());
            jpushUser.setUpdateTime(new Date());
            jpushUserMapper.insert(jpushUser);
            return new GeneralResult(enter.getRequestId());
        }

        //登录绑定
        if (enter.getStatus() == 0) {
            jpushUser = new PlaJpushUser();
            //BeanUtils.copyProperties(jpushUserData, jpushUser);
            //变更绑定用户ID,别名，标签，推送平台，推送类型，设备状态，设备状态值
            jpushUser.setId(jpushUserData.getId());
            jpushUser.setDr(0);
            jpushUser.setUserId(enter.getUserId());
            jpushUser.setAlias(enter.getAlias());
            jpushUser.setTag(enter.getTag());
            jpushUser.setPlatformType(enter.getPlatformType());
            jpushUser.setAudienceType(enter.getAudienceType());
            jpushUser.setStatus(0);
            jpushUser.setStatusCode(LOGIN);
            jpushUser.setUpdateBy(enter.getUserId());
            jpushUser.setUpdateTime(new Date());
            jpushUserMapper.updateById(jpushUser);

            return new GeneralResult(enter.getRequestId());
        }
        //注销解绑
        if (enter.getStatus() == 1) {

            jpushUser = new PlaJpushUser();
            jpushUser.setId(jpushUserData.getId());
            jpushUser.setUserId(new Long(0));
            jpushUser.setStatus(enter.getStatus());
            jpushUser.setStatusCode(LOGOUT);
            jpushUser.setPlatformType(enter.getPlatformType());
            jpushUser.setAudienceType(enter.getAudienceType());
            jpushUser.setUpdateTime(new Date());
            jpushUserMapper.updateById(jpushUser);

            return new GeneralResult(enter.getRequestId());
        }

        return new GeneralResult(enter.getRequestId());

    }

    /**
     * 根据用户主键获取极光注册ID
     *
     * @param userId
     * @return
     */
    @Override
    public List<JpushUserResult> queryJGUserInfo(String[] userId) {

        List<String> userList = Arrays.asList(userId);

        return jpushUserServiceMapper.queryRegistids(userList);
    }
}
