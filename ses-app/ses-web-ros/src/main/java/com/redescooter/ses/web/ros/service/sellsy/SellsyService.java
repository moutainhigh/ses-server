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
    
    /**
     * 查询客户列表
     *
     * @param enter
     */
    public PageResult<SellsyClientResult> queryClientList(PageEnter enter);
    
    /**
     * 客户查询
     *
     * @param enter
     * @return
     */
    public SellsyClientResult queryClientById(SellsyQueryClientOneEnter enter);
    
    /**
     * 客户创建
     *
     * @param enter
     */
    public SellsyIdResult createClient(SellsyCreateClientEnter enter);
    
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
    public  void  createDocument(SellsyCreateDocumentEnter enter);
    
}
