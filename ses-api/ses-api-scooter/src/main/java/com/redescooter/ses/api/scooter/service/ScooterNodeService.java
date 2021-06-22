package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 17:11
 */
public interface ScooterNodeService {

    /**
     * 列表
     */
    PageResult<InquiryListResult> getList(InquiryListAppEnter enter);

    /**
     * 详情
     */
    InquiryDetailResult getDetail(StringEnter enter);

    /**
     * 录入车辆
     */
    GeneralResult inputScooter(InputScooterEnter enter);

    /**
     * 录入电池
     */
    List<String> inputBattery(InputBatteryEnter enter);

    /**
     * 设置软体
     */
    ScoScooterResult setScooterModel(SetModelEnter enter);

    /**
     * 设置软体完成后节点流转
     */
    GeneralResult updateNode(Long userId, Long scooterId);

    /**
     * 绑定VIN
     */
    Map<String, String> bindVin(BindVinEnter enter);

}
