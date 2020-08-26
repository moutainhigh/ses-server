package com.redescooter.ses.web.ros.service.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentByIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;

/**
 * @ClassName:DocumentService
 * @description: DocumentService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 14:33
 */
public interface SellsyDocumentService {
    /**
     * 单据列表
     *
     * @param enter
     */
    public PageResult<SellsyDocumentListResult> queryDocumentList(SellsyDocumentListEnter enter);
    
    /**
     * 查询指定单据
     * @param enter
     */
    public JSONObject queryDocumentById(SellsyDocumentByIdEnter enter);
    
    /**
     * 发票创建
     * @param enter
     */
    public void createDocument(SellsyCreateDocumentEnter enter);
}
