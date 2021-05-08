package com.redescooter.ses.api.hub.service.admin;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.OperatingEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.hub.vo.admin.AdmSysUser;
import com.redescooter.ses.api.hub.vo.admin.OperatingAccountListResult;


/**
 * <p>
 *  服务类
 * </p>
 * @author Courtney
 * @since 2021-04-30
 */
public interface AdmOperatingAccountService {


    /**
     * 添加
     */
    int saveAdmOperatingAccount(AdmSysUser admOperatingAccount);

    /**
     * 修改
     */
    int updateByPk(AdmSysUser admOperatingAccount);

    /**
     * 删除
     */
    Integer deleteByPk(IdEnter enter);


    /**
     * 分页查询
     */
    PageResult<OperatingAccountListResult> list(OperatingEnter enter);


    /**
     * 详情
     * @param enter
     * @return
     */
    AdmSysUser accountDeatil(IdEnter enter);


    GeneralResult updateStatus(IdEnter idEnter);
}