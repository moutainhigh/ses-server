package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.SynchTenantEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeDetailResult;
import com.redescooter.ses.api.foundation.vo.user.QueryAccountNodeEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.foundation.vo.user.SaveAccountNodeEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.AccountBaseServiceMapper;
import com.redescooter.ses.service.foundation.dao.UserTokenMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserNodeMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPermissionMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaUserNodeService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 8:41 ??????
 * @ClassName: UserBaseServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class UserBaseServiceImpl implements UserBaseService {

    @Autowired
    private PlaUserMapper userMapper;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private PlaUserNodeService plaUserNodeService;

    @Autowired
    private PlaUserMapper plaUserMapper;

    @Autowired
    private PlaUserNodeMapper plaUserNodeMapper;

    @Autowired
    private PlaUserPermissionMapper plaUserPermissionMapper;

    @Autowired
    private PlaUserPasswordMapper plaUserPasswordMapper;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private PlaTenantMapper tenantMapper;

    @Autowired
    private AccountBaseServiceMapper accountBaseServiceMapper;

    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<BaseUserResult> queryEmailInfo(StringEnter enter) {

        List<BaseUserResult> resultList = new ArrayList<>();

        if (StringUtils.isBlank(enter.getKeyword())) {
            return resultList;
        }

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getKeyword());
        queryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
        List<PlaUser> plaUserList = userMapper.selectList(queryWrapper);

        if (null == plaUserList) {
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
     * ??????user
     *
     * @param enter
     * @return
     */
    @Override
    public QueryUserResult queryUserById(GeneralEnter enter) {

        PlaUser plaUser = userMapper.selectById(enter.getUserId());
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        QueryUserResult queryUserResult = new QueryUserResult();
        BeanUtils.copyProperties(plaUser, queryUserResult);
        return queryUserResult;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAccountNode(SaveAccountNodeEnter enter) {
        PlaUserNode plaUserNode = new PlaUserNode();
        plaUserNode.setId(idAppService.getId(SequenceName.PLA_USER_NODE));
        plaUserNode.setDr(Constant.DR_FALSE);
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
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveAccountNodeList(List<SaveAccountNodeEnter> enter) {
        List<PlaUserNode> saveList = new ArrayList<>();
        enter.forEach(item -> {
            PlaUserNode plaUserNode = new PlaUserNode();
            plaUserNode.setId(idAppService.getId(SequenceName.PLA_USER_NODE));
            plaUserNode.setDr(Constant.DR_FALSE);
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
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryAccountNodeDetailResult> accountNodeDetail(QueryAccountNodeEnter enter) {
        return userTokenMapper.queryAccountNodeDetail(enter, customerTypeList());
    }

    /**
     * ros????????????????????? ??????????????? saasweb???personal???
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


    /**
     * @return
     * @Author Aleks
     * @Description ???Jerry?????????????????????????????????????????????????????????????????????????????????
     * @Date 2020/8/25 15:38
     * @Param [email]
     **/
    @Override
    public boolean checkActivat(String email) {
        boolean flag = false;
        QueryWrapper<PlaUser> qw = new QueryWrapper<>();
        qw.eq(PlaUser.COL_LOGIN_NAME, email);
        qw.isNotNull(PlaUser.COL_LAST_LOGIN_TIME);
        qw.last(" limit 1");
        PlaUser user = userMapper.selectOne(qw);
        if (null != user) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Long getUserId(String email, List<Integer> types) {
        Long userId = 0L;
        QueryWrapper<PlaUser> qw = new QueryWrapper<>();
        qw.eq(PlaUser.COL_LOGIN_NAME, email);
        qw.in(PlaUser.COL_USER_TYPE, types);
        qw.last("limit 1");
        PlaUser user = userMapper.selectOne(qw);
        if (null != user) {
            userId = user.getId();
        }
        return userId;
    }

    //???????????????????????????
    @Async
    @Override
    public void custDataSynchTenant(SynchTenantEnter synchTenantEnter) {
        // ???????????????????????????????????????????????????????????????????????????
        QueryWrapper<PlaTenant> qw = new QueryWrapper<>();
        qw.eq(PlaTenant.COL_EMAIL, synchTenantEnter.getEmail());
        qw.last("limit 1");
        PlaTenant tenant = tenantMapper.selectOne(qw);
        if (null != tenant) {
            tenant.setAddress(synchTenantEnter.getAddress());
            tenant.setTenantName(synchTenantEnter.getCompanyName());
            tenantMapper.updateById(tenant);
        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deletePlaUser(String email) {
        LambdaQueryWrapper<PlaUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlaUser::getLoginName, email);
        wrapper.last("limit 1");
        PlaUser plaUser = plaUserMapper.selectOne(wrapper);
        if (null != plaUser) {
            // ??????pla_user
            accountBaseServiceMapper.deletePlaUser(email);

            LambdaQueryWrapper<PlaUserNode> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PlaUserNode::getUserId, plaUser.getId());
            lqw.last("limit 1");
            PlaUserNode node = plaUserNodeMapper.selectOne(lqw);
            if (null != node) {
                // ??????pla_user_node
                accountBaseServiceMapper.deletePlaUserNode(plaUser.getId());

                // ??????pla_user_permission
                LambdaQueryWrapper<PlaUserPermission> qw = new LambdaQueryWrapper<>();
                qw.eq(PlaUserPermission::getUserId, plaUser.getId());
                List<PlaUserPermission> list = plaUserPermissionMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(list)) {
                    for (PlaUserPermission permission : list) {
                        accountBaseServiceMapper.deletePlaUserPermission(permission.getUserId());
                    }
                }
            }
        }

        // ??????pla_user_password
        LambdaQueryWrapper<PlaUserPassword> pwdWrapper = new LambdaQueryWrapper<>();
        pwdWrapper.eq(PlaUserPassword::getLoginName, email);
        pwdWrapper.last("limit 1");
        PlaUserPassword password = plaUserPasswordMapper.selectOne(pwdWrapper);
        if (null != password) {
            accountBaseServiceMapper.deletePlaUserPassword(email);
        }
    }

}
