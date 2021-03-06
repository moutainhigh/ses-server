package com.redescooter.ses.web.ros.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.service.VersionBaseService;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.admin.DeleteRosLoginDateService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Service
public class DeleteRosLoginDateImpl implements DeleteRosLoginDateService {

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DubboReference
    private VersionBaseService versionBaseService;

    /**
     * 删除和当前 邮箱相关的所有数据 包含：sysUser、customer、inquiry
     *
     * @param enter
     */
    @Transient
    @Override
    public void deleteRosLoginDate(StringEnter enter) {

        // 校验是否为超管账户

        // 删除接口只能超级管理员操作
        OpeSysUser opeSysUser = opeSysUserService.getById(enter.getUserId());
        if (!StringUtils.equals(opeSysUser.getLoginName(), Constant.ADMIN_USER_NAME)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 不可删除超管数据
        if (StringUtils.equals(opeSysUser.getLoginName(), enter.getKeyword())) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        // 账户删除
        QueryWrapper<OpeSysUser> opeSysUserQueryWrapper = new QueryWrapper<>();
        opeSysUserQueryWrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getKeyword());
        List<OpeSysUser> opeSysUserList = opeSysUserService.list(opeSysUserQueryWrapper);
        if (!CollectionUtils.isEmpty(opeSysUserList)) {
            opeSysUserService.removeByIds(opeSysUserList.stream().map(OpeSysUser::getId).collect(Collectors.toList()));
        }

        // 查询客户
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getKeyword());
        List<OpeCustomer> opeCustomerList = opeCustomerService.list(opeCustomerQueryWrapper);
        if (!CollectionUtils.isEmpty(opeCustomerList)) {
            opeCustomerService
                    .removeByIds(opeCustomerList.stream().map(OpeCustomer::getId).collect(Collectors.toList()));
        }

        // 询价单删除
        QueryWrapper<OpeCustomerInquiry> opeCustomerInquiryQueryWrapper = new QueryWrapper<>();
        opeCustomerInquiryQueryWrapper.eq(OpeCustomerInquiry.COL_EMAIL, enter.getKeyword());
        List<OpeCustomerInquiry> opeCustomerInquiryList =
                opeCustomerInquiryService.list(opeCustomerInquiryQueryWrapper);
        if (!CollectionUtils.isEmpty(opeCustomerInquiryList)) {
            opeCustomerInquiryService.removeByIds(
                    opeCustomerInquiryList.stream().map(OpeCustomerInquiry::getId).collect(Collectors.toList()));
        }

    }

    /**
     * 测试分布式事务
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult testGlobalTransactional(GeneralEnter enter) {
        jdbcTemplate.update("update t_user set account = account - 1 where id = 1");
        // 跨库,调用foundation项目
        versionBaseService.testGlobalTransactional(enter);
        return new GeneralResult();
    }

}
