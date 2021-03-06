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
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoItemEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewItemTemplateResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceResult;
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
 * @Version???1.3
 * @create: 2020/03/30 13:03
 */
public interface AssemblyService {

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    Map<String, String> statusList(GeneralEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    Map<String, String> paymentTypeList(GeneralEnter enter);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    List<SaveAssemblyProductResult> queryAssemblyProduct(SaveAssemblyProductEnter enter);

    /**
     * ???????????????
     * 1????????? ????????????????????????????????????
     * 2???????????????????????? ????????????
     * 3????????????????????????????????????????????????
     * 4???????????????
     *
     * @param enter
     * @return
     */
    GeneralResult saveAssembly(SaveAssemblyEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    List<FactoryCommonResult> factoryList(GeneralEnter enter);

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    List<ConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    PageResult<AssemblyResult> ordinaryList(AssemblyListEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    PageResult<AssemblyResult> list(AssemblyListEnter enter);

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    AssemblyResult detail(IdEnter enter);

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    AssemblyResult ordinaryDetail(IdEnter enter);

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    List<CommonNodeResult> assemblyNode(IdEnter enter);

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    List<CommonNodeResult> ordinaryAssemblyNode(IdEnter enter);

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    Map<String, String> qcResultList(GeneralEnter enter);

    /**
     * ????????????
     * //todo ROS ??????
     *
     * @param enter
     * @return
     */
    List<AssemblyQcInfoResult> assemblyQcInfo(AssemblyQcInfoEnter enter);

    /**
     * ??????????????????
     * //todo ROS ??????
     *
     * @param enter
     * @return
     */
    List<AssemblyQcItemResult> assemblyQcInfoItem(AssemblyQcInfoItemEnter enter);

    /**
     * ????????????????????????
     * //todo ROS ??????
     *
     * @param enter
     * @return
     */
    AssemblyQcItemViewResult assemblyQcItemView(IdEnter enter);

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    List<AssemblyQcItemViewItemTemplateResult> viewItemTemplate(IdEnter enter);


    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    void export(IdEnter enter);

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter);

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    GeneralResult cancle(IdEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    GeneralResult startPrepare(StartPrepareEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    GeneralResult startAssembly(IdEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    GeneralResult startQc(IdEnter enter);

    /**
     * Qc ??????
     *
     * @param enter
     * @return
     */
    GeneralResult completeQc(IdEnter enter);

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    GeneralResult inWh(IdEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    GeneralResult saveNode(SaveNodeEnter enter);

    /**
     * ??????????????????
     *
     * @return
     */
    PaymentDetailResullt paymentDetail(IdEnter enter);

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    GeneralResult pay(PayEnter enter);

    /**
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    List<productItemResult> productItemList(IdEnter enter);

    /**
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    List<productItemResult> ordinaryProductItemList(IdEnter enter);

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    List<ProductAssemblyTraceResult> productAssemblyTrace(IdEnter enter);

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    List<ProductAssemblyTraceItemResult> productAssemblyTraceItem(IdEnter enter);

}
