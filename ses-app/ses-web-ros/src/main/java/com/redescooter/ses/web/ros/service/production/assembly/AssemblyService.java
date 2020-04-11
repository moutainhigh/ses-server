package com.redescooter.ses.web.ros.service.production.assembly;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.StartPrepareEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.productItemResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyService
 * @description: AssemblyService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:03
 */
public interface AssemblyService {

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    Map<String, String> statusList(GeneralEnter enter);

    /**
     * 付款方式
     *
     * @param enter
     * @return
     */
    Map<String, String> paymentTypeList(GeneralEnter enter);

    /**
     * 查询可组装的商品列表
     *
     * @return
     */
    List<SaveAssemblyProductResult> queryAssemblyProduct(SaveAssemblyProductEnter enter);

    /**
     * 保存组装单
     * 1、验证 库存所支持的产品最大数量
     * 2、对产品部品进行 分类整合
     * 3、形成出库单、组装单、组装单子表
     * 4、保存日志
     *
     * @param enter
     * @return
     */
    GeneralResult saveAssembly(SaveAssemblyEnter enter);

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    List<FactoryCommonResult> factoryList(GeneralEnter enter);

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    List<ConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 组装列表
     *
     * @param enter
     * @return
     */
    PageResult<AssemblyResult> ordinaryList(AssemblyListEnter enter);

    /**
     * 组装列表
     *
     * @param enter
     * @return
     */
    PageResult<AssemblyResult> list(AssemblyListEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    AssemblyResult detail(IdEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    AssemblyResult ordinaryDetail(IdEnter enter);

    /**
     * 组装单节点
     *
     * @param enter
     * @return
     */
    List<CommonNodeResult> assemblyNode(IdEnter enter);

    /**
     * 组装单节点
     *
     * @param enter
     * @return
     */
    List<CommonNodeResult> ordinaryAssemblyNode(IdEnter enter);

    /**
     * 质检记录
     *
     * @param enter
     * @return
     */
    List<AssemblyQcResult> assemblyQcTrces(AssemblyQcEnter enter);

//    List<> assemblyQcItem(IdEnter);

    /**
     * 组装单信息导出
     *
     * @param enter
     * @return
     */
    GeneralResult export(IdEnter enter);

    /**
     * 设置支付信息
     *
     * @param enter
     * @return
     */
    GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter);

    /**
     * 取消组装单
     *
     * @param enter
     * @return
     */
    GeneralResult cancle(IdEnter enter);

    /**
     * 开始备料
     *
     * @param enter
     * @return
     */
    GeneralResult startPrepare(StartPrepareEnter enter);

    /**
     * 开始组装
     *
     * @param enter
     * @return
     */
    GeneralResult startAssembly(IdEnter enter);

    /**
     * 开始质检
     *
     * @param enter
     * @return
     */
    GeneralResult startQc(IdEnter enter);

    /**
     * Qc 完成
     *
     * @param enter
     * @return
     */
    GeneralResult completeQc(IdEnter enter);

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    GeneralResult inWh(IdEnter enter);

    /**
     * 保存节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveNode(SaveNodeEnter enter);

    /**
     * 支付信息详情
     *
     * @return
     */
    PaymentDetailResullt paymentDetail(IdEnter enter);

    /**
     * 支付
     *
     * @param enter
     * @return
     */
    GeneralResult pay(PayEnter enter);

    /**
     * 组装单详情商品列表
     *
     * @param enter
     * @return
     */
    List<productItemResult> productItemList(IdEnter enter);

}
