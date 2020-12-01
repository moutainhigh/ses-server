package com.redescooter.ses.web.ros.service.qctemplete;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.SaveByCopyIdEnter;

import java.util.List;

/**
 * @ClassName:ProductionQcTmepleteService
 * @description: ProductionQcTmepleteService
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 13:54
 */
public interface ProductionQcTmepleteService {

    /**
     * 产品 质检模板保存
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveQcTemplateEnter enter);

    /**
     * 产品质检模板详情
     * 
     * @param enter
     * @return
     */
    List<QcTemplateDetailResult> detail(QcTempleteDetailEnter enter);

    /**
     * 产品 质检模板复制 快速保存
     * 
     * @param enter
     * @return
     */
    GeneralResult saveByCopyId(SaveByCopyIdEnter enter);
}
