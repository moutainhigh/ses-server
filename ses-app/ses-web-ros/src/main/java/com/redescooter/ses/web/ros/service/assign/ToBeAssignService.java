package com.redescooter.ses.web.ros.service.assign;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;

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
    ToBeAssignDetailResult getToBeAssignDetail(IdEnter enter);

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

}
