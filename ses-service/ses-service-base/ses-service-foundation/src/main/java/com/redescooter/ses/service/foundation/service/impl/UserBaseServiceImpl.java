package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 8:41 下午
 * @ClassName: UserBaseServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class UserBaseServiceImpl implements UserBaseService {

    @Autowired
    private PlaUserMapper userMapper;

    /**
     * 根据邮箱获取用户信息
     *
     * @param enter
     * @return
     */
    @Override
    public List<BaseUserResult> queryEmailInfo(StringEnter enter) {

        List<BaseUserResult> resultList = new ArrayList<>();

        if (StringUtils.isBlank(enter.getSt())) {
            return resultList;
        }

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getSt());
        queryWrapper.eq(PlaUser.COL_DR, 0);
        List<PlaUser> plaUserList = userMapper.selectList(queryWrapper);

        if (plaUserList == null) {
            return resultList;
        }
        plaUserList.forEach(user -> {
            BaseUserResult result = new BaseUserResult();
            BeanUtils.copyProperties(user, result);
            resultList.add(result);
        });

        return resultList;
    }

    /**
     * 查询user
     *
     * @param enter
     * @return
     */
    @Override
    public QueryUserResult queryUserById(GeneralEnter enter) {

        PlaUser plaUser = userMapper.selectById(enter.getUserId());
        if (plaUser == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        QueryUserResult queryUserResult = new QueryUserResult();
        BeanUtils.copyProperties(plaUser, queryUserResult);

        return queryUserResult;
    }
}
