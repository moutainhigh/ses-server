package com.redescooter.ses.web.ros.service.sellsy;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyClientServiceCreateDocumentEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.enter.document.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.*;
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
    public SellsyIdResult createDocument(SellsyClientServiceCreateDocumentEnter enter);


    /**
     * 创建付款
     * @param enter
     * @return
     */
    public SellsyCreateDocumentPaymentResult createDocumentPayment(SellsyCreateDocumentPaymentEnter enter);

    /**
     * 创建文档付款状态
     * @param enter
     * @return
     */
    public DeleteDocumentPaymentReuslt deleteDocumentPayment(SellsyDeleteDocumentPaymentEnter enter);

    /**
     * 获取指定付款记录
     * @param enter
     * @return
     */
    public SellsyQueryDocumentPaymentOneResult queryDocumentPaymentOne(SellsyQueryDocumentPaymentEnter enter);

    /**
     * 付款记录 查询
     * @param enter
     * @return
     */
    public List<SellsyQueryDocumentPaymentListEnter> queryDocumentPaymentList(QueryDocumentPaymentListEnter enter);


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

    /**
     * 根据数据库数据批量创建发票
     * @return
     */
    List<SellsyIdResult> createDcumentList();

    /**
     * 一整张发票为主整体 创建发票
     * @return
     */
    List<SellsyIdResult> createDcumentTotalList(IdEnter enter);

    public List<SellsyIdResult> createDcumentTotalOne(IdEnter enter);

    /**
     * 确认以生成的发票是否都存在
     * @return
     */
    GeneralResult checkDocumentExist(IdEnter enter);

    /**
     *
     * @param enter
     * @return
     */
    GeneralResult checkDocumentTTCFile(IdEnter enter);

    /**
     * 发票状态
     * @param enter
     * @return
     */
    GeneralResult checkDocumentStatus(IdEnter enter);

    /**
     * 校验付款记录
     * @param enter
     * @return
     */
    GeneralResult checkDocumentPayment(IdEnter enter);

    /**
     * 校验付款记录的付款日期、付款金额
     * @param enter
     * @return
     */
    GeneralResult checkPaymentDataAmount(IdEnter enter);
}
