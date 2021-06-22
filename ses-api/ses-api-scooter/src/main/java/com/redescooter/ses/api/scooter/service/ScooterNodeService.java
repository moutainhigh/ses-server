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

import java.util.List;

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
    GeneralResult setScooterModel(SetModelEnter enter);

    /**
     * 绑定VIN
     */
    GeneralResult bindVin(BindVinEnter enter);

}
