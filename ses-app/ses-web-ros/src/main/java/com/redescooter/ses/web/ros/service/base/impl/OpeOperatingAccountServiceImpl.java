package com.redescooter.ses.web.ros.service.base.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dao.base.OpeOperatingAccountMapper;
import com.redescooter.ses.web.ros.dm.OpeOperatingAccount;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeOperatingAccountService;
import com.redescooter.ses.web.ros.vo.account.OperatingAccountListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Courtney
 * @since 2021-04-30
 */
@Service
@Slf4j
public class OpeOperatingAccountServiceImpl extends ServiceImpl<OpeOperatingAccountMapper, OpeOperatingAccount> implements OpeOperatingAccountService {

    @Resource
    private OpeOperatingAccountMapper opeOperatingAccountMapper;

    @Autowired
    private OpeOperatingAccountService opeOperatingAccountService;


    @Override
    public int saveOpeOperatingAccount(OpeOperatingAccount opeOperatingAccount) {
        QueryWrapper<OpeOperatingAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("operating_email",opeOperatingAccount.getOperatingEmail());
        OpeOperatingAccount opeOperatingAccounts = opeOperatingAccountMapper.selectOne(wrapper);
        if(opeOperatingAccounts!=null){
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        opeOperatingAccount.setCreateTime(new Date());
        return opeOperatingAccountMapper.insert(opeOperatingAccount);
    }

    @Override
    public int updateByPk(OpeOperatingAccount opeOperatingAccount) {
        return opeOperatingAccountMapper.updateById(opeOperatingAccount);
    }

    @Override
    public boolean deleteByPk(IdEnter enter) {
        OpeOperatingAccount opeOperatingAccount = opeOperatingAccountMapper.selectById(enter.getId());
        if(opeOperatingAccount==null){
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return opeOperatingAccountService.removeById(enter.getId());
    }

    @Override
    public PageResult<OperatingAccountListResult> list(OperatingEnter enter) {
        int num = opeOperatingAccountMapper.listNum();
        if (num == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OperatingAccountListResult> list = opeOperatingAccountMapper.accountList(enter);
        return PageResult.create(enter, num, list);
    }

    @Override
    public OpeOperatingAccount accountDeatil(IdEnter enter) {
        return opeOperatingAccountMapper.selectById(enter.getId());
    }

    @Override
    public GeneralResult updateStatus(IdEnter idEnter) {
        OpeOperatingAccount opeOperatingAccounts = opeOperatingAccountMapper.selectById(idEnter.getId());
        if(opeOperatingAccounts==null){
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        Integer status = opeOperatingAccounts.getStatus();
        opeOperatingAccounts.setStatus(status == 0 ? 1 : 0);
        int result = opeOperatingAccountMapper.updateById(opeOperatingAccounts);
        return new GeneralResult(idEnter.getRequestId());
    }

}
