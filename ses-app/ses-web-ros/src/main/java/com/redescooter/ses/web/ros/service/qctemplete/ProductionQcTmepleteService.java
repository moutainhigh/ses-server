package com.redescooter.ses.web.ros.service.qctemplete;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;

import java.util.List;

/**
 * @ClassName:ProductionQcTmepleteService
 * @description: ProductionQcTmepleteService
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 13:54
 */
public interface ProductionQcTmepleteService {

    /**
     * 部件质检模板保存
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveQcTemplateEnter enter);

    /**
     * 质检模板详情
     * 
     * @param enter
     * @return
     */
    List<QcTemplateDetailResult> detail(IdEnter enter);
}
