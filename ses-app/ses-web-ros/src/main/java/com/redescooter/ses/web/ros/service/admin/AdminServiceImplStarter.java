package com.redescooter.ses.web.ros.service.admin;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.enums.employee.EmployeeStatusEnums;
import com.redescooter.ses.api.common.enums.website.AccessoryTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.admin.AdminServiceStarterMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName:AdminServiceImplStarter
 * @description: AdminServiceImplStarter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/01 11:23
 */
//不传 默认为最低优先级
@Order
@Log4j
@Service
public class AdminServiceImplStarter implements AdminServiceStarter {

    @Autowired
    private AdminServiceStarterMapper adminServiceStarterMapper;

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Reference
    private IdAppService idAppService;

    /**
     * 检查admin 是否存在
     *
     * @param enter
     * @return
     */
    @Override
    public Boolean checkAdmin(GeneralEnter enter) {
        return adminServiceStarterMapper.checkAdmin(Constant.ADMIN_USER_NAME) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 保存admin 用户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAdmin(GeneralEnter enter) {
        if (checkAdmin(enter)) {
            log.info("---------------------admin存在无需保存--------------------------");
            return new GeneralResult();
        }

        //查询顶级部门
        OpeSysDept dept = adminServiceStarterMapper.topDept(Constant.DEPT_TREE_ROOT_ID);
        if (dept == null) {
            //保存部门根节点
            dept = OpeSysDept.builder()
                    .id(idAppService.getId(SequenceName.OPE_SYS_DEPT))
                    .dr(0)
                    .pId(Constant.DEPT_TREE_ROOT_ID)
                    .tenantId(0L)
                    .principal(0L)
                    .level(Integer.valueOf(DeptLevelEnums.COMPANY.getValue()))
                    .name("ROOT")
                    .description(null)
                    .sort(1)
                    .createdBy(0L)
                    .createdTime(new Date())
                    .updatedBy(0L)
                    .updatedTime(new Date())
                    .build();
            opeSysDeptService.save(dept);
            log.info("---------------------ROOT部门不存在保存ROOT 部门--------------------------");
        }


        int salt = RandomUtils.nextInt(10000, 99999);
        //保存user
        OpeSysUser opeSysUser = OpeSysUser.builder()
                .id(idAppService.getId(SequenceName.OPE_SYS_USER))
                .dr(0)
                .deptId(dept.getId())
                .orgStaffId(0L)
                .appId(AccountTypeEnums.WEB_ROS.getAppId())
                .systemId(AccountTypeEnums.WEB_ROS.getSystemId())
                .password(DigestUtils.md5Hex(Constant.DEFAULT_PASSWORD + salt))
                .salt(String.valueOf(salt))
                .status(SysUserStatusEnum.NORMAL.getValue())
                .loginName(Constant.ADMIN_USER_NAME)
                .lastLoginTime(null)
                .lastLoginIp(null)
                .lastLoginToken(null)
                .activationTime(new Date())
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();

//        OpeSysStaff opeSysStaff = OpeSysStaff.builder()
//                .id(idAppService.getId(SequenceName.OPE_SYS_STAFF))
//                .dr(0)
//       .status(EmployeeStatusEnums.IN_SERVICE.getValue());
//        opeSysStaff.setSysUserId(userId);
//        opeSysStaff.setCreatedBy(enter.getUserId());
//        opeSysStaff.setCreatedTime(new Date());
//        opeSysStaff.setUpdatedBy(enter.getUserId());
//        opeSysStaff.setUpdatedTime(new Date());

        return null;
    }

    /**
     * 校验admin 的数据是否完整
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult checkAdminDate(GeneralEnter enter) {
        return null;
    }
}
