package com.redescooter.ses.web.ros.service.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.vo.app.UpdatePasswordEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountListEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountSaveEnter;
import com.redescooter.ses.web.ros.vo.app.WarehouseAccountUpdateEnter;

import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:07
 */
public interface WarehouseAccountService {

    /**
     * 每个tab的count
     */
    Map<String, Integer> getTabCount(GeneralEnter enter);

    /**
     * 列表
     */
    PageResult<OpeWarehouseAccount> getList(WarehouseAccountListEnter enter);

    /**
     * 新增
     */
    GeneralResult add(WarehouseAccountSaveEnter enter);

    /**
     * 详情
     */
    OpeWarehouseAccount getDetail(IdEnter enter);

    /**
     * 编辑
     */
    GeneralResult edit(WarehouseAccountUpdateEnter enter);

    /**
     * 开启或关闭状态
     */
    GeneralResult editStatus(IdEnter enter);

    /**
     * 修改密码
     */
    GeneralResult updatePassword(UpdatePasswordEnter enter);

    /**
     * 删除
     */
    GeneralResult delete(IdEnter enter);

}
