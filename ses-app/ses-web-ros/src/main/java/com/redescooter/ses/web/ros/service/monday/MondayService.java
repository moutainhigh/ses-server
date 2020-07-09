package com.redescooter.ses.web.ros.service.monday;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;

/**
 * @ClassName:MondayService
 * @description: MondayService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:46
 */
public interface MondayService {


    GeneralResult getMondayData(GeneralEnter enter);
}
