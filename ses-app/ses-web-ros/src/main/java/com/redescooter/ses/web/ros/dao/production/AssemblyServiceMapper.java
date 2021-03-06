package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
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
import com.redescooter.ses.web.ros.vo.production.assembly.QcItemViewResult;
import com.redescooter.ses.web.ros.vo.production.assembly.productItemResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:AssemblyServiceMapper
 * @description: AssemblyServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 16:12
 */
public interface AssemblyServiceMapper {
    /**
     * 类型状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByTypes(GeneralEnter enter);

    /**
     * 组装单 统计
     *
     * @param enter
     * @param statusList
     * @return
     */
    int assemblyListCount(@Param("enter") AssemblyListEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 组装单列表
     *
     * @param enter
     * @param statusList
     * @return
     */
    List<AssemblyResult> assemblyList(@Param("enter") AssemblyListEnter enter, @Param("statusList") List<String> statusList);

    List<AssemblyResult> ordinaryList(@Param("enter") AssemblyListEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 支付信息
     *
     * @param assemblyIds
     * @return
     */
    List<PaymentItemDetailResult> paymentItemDetailListByAssIds(List<Long> assemblyIds);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    AssemblyResult detail(IdEnter enter);

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
     * 获取部件详情列表
     *
     * @param partIds
     * @return
     */
    List<PartDetailDto> partDetailListByPartIds(List<Long> partIds);

    /**
     * 支付信息详情
     *
     * @param id
     * @return
     */
    List<PaymentItemDetailResult> paymentItemList(Long id);

    /**
     * 组装单详情中 商品列表
     *
     * @param enter
     * @return
     */
    List<productItemResult> productItemList(IdEnter enter);

    /**
     * 车辆组装记录
     * ROS 1.4 补充的接口
     *
     * @param enter
     * @return
     */
    List<ProductAssemblyTraceResult> productAssemblyTrace(IdEnter enter);

    /**
     * 组装记录条目
     *
     * @param enter
     * @return
     */
    List<ProductAssemblyTraceItemResult> productAssemblyItemTrace(IdEnter enter);

    /**
     * 查询组装记录
     *
     * @param ids
     * @return
     */
    List<ProductAssemblyTraceItemResult> productAssemblyItemTraceByIds(List<Long> ids);

    /**
     * 产品之间信息
     *
     * @param enter
     * @return
     */
    List<AssemblyQcInfoResult> assemblyQcInfoList(AssemblyQcInfoEnter enter);

    /**
     * 产品之间信息统计
     *
     * @param enter
     * @return
     */
    int assemblyQcInfoCount(AssemblyQcInfoEnter enter);

    /**
     * 质检结果下的质检条目
     *
     * @param enter
     * @return
     */
    List<AssemblyQcItemResult> assemblyQcInfoItem(AssemblyQcInfoItemEnter enter);

    /**
     * 质检条目的质检项
     *
     * @param enter
     * @return
     */
    AssemblyQcItemViewResult assemblyQcItemView(IdEnter enter);

    /**
     * 产品质检项条目
     *
     * @param serialN
     * @return
     */
    List<QcItemViewResult> qcItemViewResult(String serialN);

    /**
     * 产品质检模板结果
     *
     * @param id
     * @return
     */
    List<AssemblyQcItemViewItemTemplateResult> viewItemTemplate(Long id);

    /**
     * 查询质检条目信息
     *
     * @param enter
     * @param ids
     * @return
     */
    List<AssemblyQcItemResult> assemblyQcInfoItemByIds(@Param("enter") AssemblyQcInfoEnter enter, @Param("ids") List<Long> ids);
}
