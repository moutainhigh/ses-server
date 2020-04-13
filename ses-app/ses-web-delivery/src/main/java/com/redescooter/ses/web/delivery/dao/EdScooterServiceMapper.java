package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterGreenDataResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterListEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterResult;

import java.util.List;

/**
 * @ClassName:MobileServiceMapper
 * @description: MobileServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 16:46
 */
public interface EdScooterServiceMapper {

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * 车辆列表统计
     *
     * @param enter
     */
    Integer listCount(EdScooterListEnter enter);

    /**
     * 车辆列表数据
     *
     * @param enter
     * @return
     */
    List<EdScooterResult> list(EdScooterListEnter enter);

    /**
     * 车辆详情
     *
     * @param enter
     * @return
     */
    EdScooterResult detail(IdEnter enter);

    /**
     * 车辆环保数据展示
     *
     * @param enter
     * @return
     */
    EdScooterGreenDataResult scooterGreenData(IdEnter enter);

    /**
     * 车辆已还车分配记录
     *
     * @param enter
     * @return
     */
    int assignEdScooterHistroyCount(EdScooterHistroyEnter enter);

    /**
     * 使用中的分配记录
     *
     * @param enter
     * @return
     */
    int usingAssignEdScooterHistroyCount(EdScooterHistroyEnter enter);

    /**
     * 车辆已还车分配记录
     *
     * @param enter
     * @return
     */
    List<EdScooterHistroyResult> assignEdScooterHistroyList(EdScooterHistroyEnter enter);

    /**
     * 车辆 使用中的分配记录
     *
     * @param enter
     * @return
     */
    List<EdScooterHistroyResult> usingAssignEdScooterHistroyList(EdScooterHistroyEnter enter);

    /**
     * 车辆列表 完善司机信息
     *
     * @param scooterIdList
     * @return
     */
    List<EdScooterResult> driverUserProfile(List<Long> scooterIdList);
}
