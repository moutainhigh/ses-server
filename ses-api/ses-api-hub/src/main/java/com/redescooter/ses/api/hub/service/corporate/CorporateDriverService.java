package com.redescooter.ses.api.hub.service.corporate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 10:39 上午
 * @ClassName: CorporateDriverService
 * @Function: TODO
 */
public interface CorporateDriverService {

    /**
     * @param enter
     * @return
     */
    GeneralResult updateDriverDef1(List<IdEnter> enter);
}
