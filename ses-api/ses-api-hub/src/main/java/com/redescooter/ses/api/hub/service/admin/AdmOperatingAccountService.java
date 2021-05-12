package com.redescooter.ses.api.hub.service.admin;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.common.vo.oms.EditAccountEnter;
import com.redescooter.ses.api.common.vo.oms.SavePasswordAccountEnter;
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
    GeneralResult saveAdmOperatingAccount(SavePasswordAccountEnter enter);

    /**
     * 修改
     */
    GeneralResult updateByPk(EditAccountEnter enter);

    /**
     * 删除
     */
    Integer deleteByPk(IdEnter enter);


    /**
     * 分页查询
     */
    PageResult<OperatingAccountListResult> list(OperatingEnter enter);





    GeneralResult updateStatus(IdEnter idEnter);

    GeneralResult editPassword(SavePasswordAccountEnter enter);


    GeneralResult logout(GeneralEnter enter);
}