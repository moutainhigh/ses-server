package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialpartsStockDetailResult;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @description:原料库服务层
 * @author: Aleks
 * @create: 2020/12/28 15:06
 */
public interface WmsMaterialStockService {

    /**
     * 原料库部件库存列表
     * @param enter
     * @return
     */
    PageResult<MaterialStockPartsListResult> materialStockPartsList(MaterialStockPartsListEnter enter);


    /**
     * 原料库部件详情
     * @param enter
     * @return
     */
    MaterialpartsStockDetailResult materialStockPartsDetail(IdEnter enter);

}
