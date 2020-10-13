package com.redescooter.ses.web.ros.service.qctemplete.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.qctemplete.ProductionQcTmepleteService;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName:ProductionQcTmepleteServiceiMPL
 * @description: ProductionQcTmepleteServiceiMPL
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 13:59
 */
@Service
public class ProductionQcTmepleteServiceImpl implements ProductionQcTmepleteService {

    /**
     * 部件质检模板保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SaveQcTemplateEnter enter) {
        return null;
    }

    /**
     * 质检模板详情
     *
     * @param enter
     * @return
     */
    @Override
    public List<QcTemplateDetailResult> detail(IdEnter enter) {
        return null;
    }
}
