package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeDetailResult;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.foundation.vo.user.SaveAccountNodeEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.UserTokenMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaUserNodeService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private PlaUserNodeService plaUserNodeService;

    @Autowired
    private UserTokenMapper userTokenMapper;

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

    /**
     * 保存账户节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAccountNode(SaveAccountNodeEnter enter) {
        PlaUserNode plaUserNode = new PlaUserNode();
        plaUserNode.setId(idAppService.getId(SequenceName.PLA_USER_NODE));
        plaUserNode.setDr(0);
        plaUserNode.setUserId(enter.getInputUserId());
        plaUserNode.setTenantId(enter.getTenantId());
        plaUserNode.setEvent(enter.getEvent());
        plaUserNode.setMemo(enter.getMemo());
        plaUserNode.setEventTime(new Date());
        plaUserNode.setCreateTime(new Date());
        plaUserNode.setCreateBy(enter.getUserId());
        plaUserNode.setUpdateBy(enter.getUserId());
        plaUserNode.setUpdateTime(new Date());
        plaUserNodeService.save(plaUserNode);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 批量保存账户节点
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveAccountNodeList(List<SaveAccountNodeEnter> enter) {
        List<PlaUserNode> saveList = new ArrayList<>();
        enter.forEach(item -> {
            PlaUserNode plaUserNode = new PlaUserNode();
            plaUserNode.setId(idAppService.getId(SequenceName.PLA_USER_NODE));
            plaUserNode.setDr(0);
            plaUserNode.setUserId(item.getInputUserId());
            plaUserNode.setTenantId(item.getTenantId());
            plaUserNode.setEvent(item.getEvent());
            plaUserNode.setMemo(item.getMemo());
            plaUserNode.setEventTime(new Date());
            plaUserNode.setCreateTime(new Date());
            plaUserNode.setCreateBy(item.getUserId());
            plaUserNode.setUpdateBy(item.getUserId());
            plaUserNode.setUpdateTime(new Date());

            saveList.add(plaUserNode);
        });
        plaUserNodeService.saveBatch(saveList);
        return null;
    }

    /**
     * 账户节点详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryAccountNodeDetailResult> accountNodeDetail(QueryAccountNodeEnter enter) {
        return userTokenMapper.queryAccountNodeDetail(enter, customerTypeList());
    }

    /**
     * ros客户的账户信息 限制范围在 saasweb、personal内
     *
     * @return
     */
    private List<Integer> customerTypeList() {
        List<Integer> accountType = new ArrayList<>();
        accountType.add(AccountTypeEnums.WEB_RESTAURANT.getAccountType());
        accountType.add(AccountTypeEnums.WEB_EXPRESS.getAccountType());
        accountType.add(AccountTypeEnums.APP_PERSONAL.getAccountType());
        return accountType;
    }
}
