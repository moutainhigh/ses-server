package com.redescooter.ses.web.ros.dao.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutProductListResult;

import java.util.List;

/**
 * @ClassName:WhOutServiceMapper
 * @description: WhOutServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 15:21
 */
public interface WhOutServiceMapper {
    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    Integer productListCount(WhOutProductListEnter enter);

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    List<WhOutProductListResult> productListList(WhOutProductListEnter enter);

    /**
     * 收件人列表
     * @param enter
     * @return
     */
    List<WhOutConsigneeResult> consigneeList(GeneralEnter enter);
}
