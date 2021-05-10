package com.redescooter.ses.web.ros.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeWarehouseAccountMapper;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.app.WarehouseAccountService;
import com.redescooter.ses.web.ros.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.web.ros.vo.app.UpdatePasswordEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountListEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountSaveEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountUpdateEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:07
 */
@Service
@Slf4j
public class WarehouseAccountServiceImpl implements WarehouseAccountService {

    @Autowired
    private OpeWarehouseAccountService opeWarehouseAccountService;

    @Autowired
    private OpeWarehouseAccountMapper opeWarehouseAccountMapper;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 每个tab的count
     */
    @Override
    public Map<String, Integer> getTabCount(GeneralEnter enter) {
        Map<String, Integer> result = Maps.newHashMapWithExpectedSize(2);
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        List<OpeWarehouseAccount> list = opeWarehouseAccountService.list(qw);
        List<OpeWarehouseAccount> companyList = list.stream().filter(o -> o.getType() == 1).collect(Collectors.toList());
        List<OpeWarehouseAccount> partList = list.stream().filter(o -> o.getType() == 2).collect(Collectors.toList());
        result.put("1", companyList.size());
        result.put("2", partList.size());
        return result;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<OpeWarehouseAccount> getList(WarehouseAccountListEnter enter) {
        int count = opeWarehouseAccountMapper.getListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OpeWarehouseAccount> list = opeWarehouseAccountMapper.getList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 新增
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult add(WarehouseAccountSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        // 校验
        check(enter);

        int salt = RandomUtils.nextInt(10000, 99999);
        String pwd = DigestUtils.md5Hex(enter.getNewPassword() + salt);
        OpeWarehouseAccount account = new OpeWarehouseAccount();
        account.setId(idAppService.getId(SequenceName.OPE_WAREHOUSE_ACCOUNT));
        account.setDr(Constant.DR_FALSE);
        account.setType(enter.getType());
        account.setName(enter.getName());
        account.setEmail(enter.getEmail());
        account.setPosition(enter.getPosition());
        account.setSystem(enter.getSystem());
        account.setPassword(pwd);
        account.setSalt(String.valueOf(salt));
        account.setStatus(StatusEnum.DISABLE.getCode());
        account.setCreatedBy(enter.getUserId());
        account.setCreatedTime(new Date());
        opeWarehouseAccountService.save(account);
        return new GeneralResult(enter.getRequestId());
    }

    public void check(WarehouseAccountSaveEnter enter) {
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmNewPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getEmail, enter.getEmail());
        qw.eq(OpeWarehouseAccount::getSystem, enter.getSystem());
        int count = opeWarehouseAccountService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    /**
     * 详情
     */
    @Override
    public OpeWarehouseAccount getDetail(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return account;
    }

    /**
     * 编辑
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult edit(WarehouseAccountUpdateEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.ne(OpeWarehouseAccount::getId, enter.getId());
        qw.eq(OpeWarehouseAccount::getEmail, account.getEmail());
        qw.eq(OpeWarehouseAccount::getSystem, enter.getSystem());
        int count = opeWarehouseAccountService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        account.setName(enter.getName());
        account.setPosition(enter.getPosition());
        account.setSystem(enter.getSystem());
        account.setUpdatedBy(enter.getUserId());
        account.setUpdatedTime(new Date());
        opeWarehouseAccountService.updateById(account);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开启或关闭状态
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editStatus(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        // 关闭时判断,若账号下还有处理中的询价单,抛出异常



        Integer status = account.getStatus();
        account.setStatus(status == 1 ? 2 : 1);
        opeWarehouseAccountService.updateById(account);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 修改密码
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updatePassword(UpdatePasswordEnter enter) {
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmNewPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        String salt = account.getSalt();
        String pwd = DigestUtils.md5Hex(enter.getNewPassword() + salt);
        account.setEmail(enter.getEmail());
        account.setPassword(pwd);
        opeWarehouseAccountService.updateById(account);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        opeWarehouseAccountService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

}
