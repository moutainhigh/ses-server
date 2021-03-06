package com.redescooter.ses.web.ros.service.roledata.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDataService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.roledata.RoleDataService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataSaveEnter;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataShowResult;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataTree;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassNameRoleDataServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/9 13:41
 * @Version V1.0
 **/
@Service
public class RoleDataServiceImpl implements RoleDataService {

    @Autowired
    private OpeSysRoleDataService opeSysRoleDataService;

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private OpeSysUserRoleService opeSysUserRoleService;

    @Override
    public RoleDataShowResult roleDataShow(RoleOpEnter enter) {
        RoleDataShowResult result = new RoleDataShowResult();
        // ????????????
        List<OpeSysDept> deptList = opeSysDeptService.list();
        List<RoleDataTree> dataTrees = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deptList)) {
            for (OpeSysDept dept : deptList) {
                RoleDataTree roleDataTree = new RoleDataTree();
                roleDataTree.setDeptId(dept.getId());
                roleDataTree.setDeptCode(dept.getCode());
                roleDataTree.setDeptName(dept.getName());
                roleDataTree.setLevel(dept.getLevel());
                roleDataTree.setPId(dept.getPId());
                roleDataTree.setId(dept.getId());
                dataTrees.add(roleDataTree);
            }
            // ???????????????????????????
            QueryWrapper<OpeSysRoleData> qw = new QueryWrapper<>();
            qw.eq(OpeSysRoleData.COL_ROLE_ID, enter.getId());
            List<OpeSysRoleData> dataList = opeSysRoleDataService.list(qw);
            if (CollectionUtils.isNotEmpty(dataList) && dataList.size() > 1) {
                // ????????????????????? ??????????????????????????????
                List<Long> deptIds = dataList.stream().map(OpeSysRoleData::getDeptId).collect(Collectors.toList());
                for (RoleDataTree tree : dataTrees) {
                    if (deptIds.contains(tree.getDeptId())) {
                        tree.setChecked(true);
                    }
                }
            } else if (1 == dataList.size()) {
                // ?????????????????????????????????
                result.setDataType(dataList.get(0).getDataType());
            }
            result.setRoleDataTree(TreeUtil.build(dataTrees, Constant.DEPT_TREE_ROOT_ID));
        }
        return result;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveRoleData(RoleDataSaveEnter enter) {
        // ?????????????????????????????????
        QueryWrapper<OpeSysRoleData> qw = new QueryWrapper<>();
        qw.eq(OpeSysRoleData.COL_ROLE_ID, enter.getRoleId());
        opeSysRoleDataService.remove(qw);
        List<OpeSysRoleData> roleDataList = new ArrayList<>();
        // ???????????????  ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (StringManaConstant.entityIsNotNull(enter.getDataType())) {
            // ????????????????????????????????????????????????,????????????????????????
            OpeSysRoleData roleData = new OpeSysRoleData();
            roleData.setRoleId(enter.getRoleId());
            roleData.setDataType(enter.getDataType());
            roleDataList.add(roleData);
        } else if (!Strings.isNullOrEmpty(enter.getDeptId())) {
            // ??????????????????????????????????????????
            for (String deptId : enter.getDeptId().split(",")) {
                OpeSysRoleData roleData = new OpeSysRoleData();
                roleData.setRoleId(enter.getRoleId());
                roleData.setDeptId(Long.parseLong(deptId));
                roleDataList.add(roleData);
            }
        }
        if (CollectionUtils.isNotEmpty(roleDataList)) {
            opeSysRoleDataService.saveBatch(roleDataList);
        }
        try {
            QueryWrapper<OpeSysUserRole> queryWrapper = new QueryWrapper();
            List<OpeSysUserRole> list = opeSysUserRoleService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                List<Long> userIds = list.stream().map(OpeSysUserRole::getUserId).collect(Collectors.toList());
                for (Long userId : userIds) {
                    staffService.inintUserMsg(userId);
                }
            }
        } catch (Exception e) {
        }
        return new GeneralResult(enter.getRequestId());
    }


}
