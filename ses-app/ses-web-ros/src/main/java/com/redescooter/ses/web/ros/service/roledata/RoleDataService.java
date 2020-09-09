package com.redescooter.ses.web.ros.service.roledata;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataSaveEnter;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataShowResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;

/**
 * @ClassNameRoleDataService
 * @Description
 * @Author kyle
 * @Date2020/9/9 13:29
 * @Version V1.0
 **/
public interface RoleDataService {

    /**
     * @Author Aleks
     * @Description  展示角色数据权限的树结构
     * @Date  2020/9/9 13:53
     * @Param [enter]
     * @return
     **/
    RoleDataShowResult roleDataShow(RoleOpEnter enter);


    /**
     * @Author Aleks
     * @Description  保存角色的数据权限
     * @Date  2020/9/9 13:53
     * @Param [enter]
     * @return
     **/
    GeneralResult saveRoleData(RoleDataSaveEnter enter);

}
