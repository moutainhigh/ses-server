package com.redescooter.ses.web.ros.service.production.assembly.assembly;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:04
 */
@Service
public class AssemblyServiceImpl implements AssemblyService {


    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByTypes(GeneralEnter enter) {
        return null;
    }

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        return null;
    }

    /**
     * 付款方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> paymentTypeList(GeneralEnter enter) {
        return null;
    }

    /**
     * 保存组装单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveAssembly(SaveAssemblyEnter enter) {
        return null;
    }

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> factoryList(GeneralEnter enter) {
        return null;
    }

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        return null;
    }

    /**
     * 组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AssemblyResult> list(AssemblyListEnter enter) {
        return null;
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public AssemblyResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 组装单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> assemblyNode(IdEnter enter) {
        return null;
    }

    /**
     * 组装单信息导出
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(IdEnter enter) {
        return null;
    }

    /**
     * 设置支付信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter) {
        return null;
    }

    /**
     * 质检记录
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcResult> assemblyQcTrces(AssemblyQcEnter enter) {
        return null;
    }

    /**
     * 取消组装单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancle(IdEnter enter) {
        return null;
    }

    /**
     * 开始备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startPrepare(IdEnter enter) {
        return null;
    }

    /**
     * 开始组装
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startAssembly(IdEnter enter) {
        return null;
    }

    /**
     * 开始质检
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startQc(IdEnter enter) {
        return null;
    }

    /**
     * Qc 完成
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        return null;
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult inWh(IdEnter enter) {
        return null;
    }
}
