package com.redescooter.ses.web.ros.dao.qctemplete;

import com.redescooter.ses.web.ros.vo.bom.QcResultResult;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;

import java.util.List;

/**
 * @ClassName:ProductionQcTmepleteServiceMapper
 * @description: ProductionQcTmepleteServiceMapper
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 16:02
 */
public interface ProductionQcTempleteServiceMapper {
    /**
     * 质检项模板详情
     * 
     * @param enter
     * @return
     */
    List<QcTemplateDetailResult> detail(QcTempleteDetailEnter enter);

    /**
     * 质检模板结果集
     * 
     * @param templeteId
     * @return
     */
    List<QcResultResult> detailQcResultList(List<Long> templeteId);
}
