package com.redescooter.ses.web.ros.service.delete;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;

import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
public interface DeleteService {

    /**
     * 删除车辆bom
     */
    GeneralResult deleteScooterBom(IdEnter enter);
    /**
     * 删除客户对应关系
     * @param idEnter
     * @return
     */
    GeneralResult deleteCustomer(IdEnter idEnter);

    /**
     * 删除组装件bom
     */
    GeneralResult deleteCombinBom(IdEnter enter);

    /**
     * 删除车辆
     */
    GeneralResult deleteScooter(StringEnter enter);

    /**
     * 删除部件
     */
    GeneralResult deletePart(IdEnter enter);

    GeneralResult deleteDeposit(GeneralEnter enter);

    /**
     * 最新的删除车辆
     */
    GeneralResult deleteScooterNew(StringEnter enter);

}
