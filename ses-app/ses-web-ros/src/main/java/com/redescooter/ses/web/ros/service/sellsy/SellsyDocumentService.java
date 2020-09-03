package com.redescooter.ses.web.ros.service.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyDocumentOneEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.SellsyUpdateDocumentStatusEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResut;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyDocumentListResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public List<SellsyDocumentListResult> queryDocumentList(SellsyDocumentListEnter enter);

    /**
     * 查询指定单据
     * @param enter
     */
    public JSONObject queryDocumentOne(SellsyDocumentOneEnter enter);

    /**
     * 发票创建
     * @param enter
     */
    public SellsyIdResut createDocument(SellsyClientServiceCreateDocumentEnter enter);

    /**
     * 更新单据状态
     * @param enter
     */
    public void upateDocumentStatus(SellsyUpdateDocumentStatusEnter enter);

    /**
     * 导入发票
     *
     * @param file
     * @return
     */
    SellsyImportExcelResult importSellsyDocument(MultipartFile file);

    /**
     * 解析到的发票数据保存到数据库
     * @param successList
     * @return
     */
    SellsyImportExcelResult saveSellsyInvoid(List<SellsyExcleData> successList);
}
