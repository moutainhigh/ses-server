package com.redescooter.ses.web.ros.service.assign;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeResult;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2020/12/27 14:50
 */
public interface ToBeAssignService {

    /**
     * 待分配列表
     */
    PageResult<ToBeAssignListResult> getToBeAssignList(ToBeAssignListEnter enter);

    /**
     * 待分配列表点击分配带出数据
     */
    ToBeAssignDetailResult getToBeAssignDetail(CustomerIdEnter enter);

    /**
     * 填写完座位数点击下一步
     */
    ToBeAssignNextStopResult getSeatNext(ToBeAssignSeatNextEnter enter);

    /**
     * 填写完车牌点击下一步
     */
    ToBeAssignNextStopResult getLicensePlateNext(ToBeAssignLicensePlateNextEnter enter);

    /**
     * 根据R.SN获得颜色
     */
    ToBeAssignColorResult getColorByRSN(StringEnter enter);

    /**
     * 填写完R.SN并点击提交
     */
    ToBeAssignNextStopResult submit(ToBeAssignSubmitEnter enter);

    /**
     * 查询客户走到哪个节点并带出数据
     */
    ToBeAssignNodeResult getNode(CustomerIdEnter enter);

    /**
     * 待分配列表和已分配列表的tab数量统计
     */
    Map<String, Object> getTabCount(GeneralEnter enter);

    /**
     * 点击分配按钮校验车辆库存数量
     */
    BooleanResult checkScooterStock(CustomerIdEnter enter);

    /**
     * 生成105条SSN
     */
    List<String> testGenerateVINCode(GeneralEnter enter);

}
