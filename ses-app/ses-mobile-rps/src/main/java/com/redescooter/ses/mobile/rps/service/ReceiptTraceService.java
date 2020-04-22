package com.redescooter.ses.mobile.rps.service;

import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;

/**
 * @ClassName:ReceiptTraceService
 * @description: ReceiptTraceService 单据节点服务服务
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/22 19:15
 */
public interface ReceiptTraceService {

    /**
     * 保存调拨单节点
     *
     * @return
     */
    GeneralResult saveAllocateNode(SaveNodeEnter enter);

    /**
     * 保存组装单节点
     * @param enter
     * @return
     */
    GeneralResult saveAssemblyNode(SaveNodeEnter enter);
}
