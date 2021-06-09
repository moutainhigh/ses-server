package com.redescooter.ses.web.ros.service.delete;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
public interface DeleteService {

    /**
     * 删除车辆bom
     */
    GeneralResult deleteScooterBom(StringEnter enter);

    /**
     * 删除组装件bom
     */
    GeneralResult deleteCombinBom(StringEnter enter);

    /**
     * 删除车辆
     */
    GeneralResult deleteScooter(StringEnter enter);


}
