package com.redescooter.ses.web.ros.service.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCreateClientEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.CreateDocumentAttributesEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentByIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyQueryClientOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyClientResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyIdResult;

/**
 * @ClassName:SellsyService
 * @description: SellsyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/18 19:38
 */
public interface SellsyService {
    
    /**
     * sellsy 具体执行器
     *
     * @param enter
     * @return
     */
    public SellsyGeneralResult sellsyExecution(SellsyExecutionEnter enter);
}
