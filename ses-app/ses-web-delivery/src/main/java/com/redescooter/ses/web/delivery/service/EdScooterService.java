package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.vo.edscooter.*;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:MobileService
 * @description: EdScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:24
 */
public interface EdScooterService {
    /**
     * 状态分组
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countStatus(GeneralEnter enter);

    /**
     * 列表
     *
     * @param enter
     * @return
     */
    PageResult<EdScooterResult> list(EdScooterListEnter enter);

    /**
     * 查询已下班且未离职的司机列表
     *
     * @param enter
     * @return
     */
    List<DriverListResult> offDrivers(GeneralEnter enter);

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
     * 分配记录
     *
     * @param enter
     * @return
     */
    PageResult<EdScooterHistroyResult> assignMobileHistroy(EdScooterHistroyEnter enter);

    /**
     * todo 暂无数据 暂时不写
     *
     * @param enter
     * @return
     */
    PageResult<EdScooterHistroyResult> repairMobileHistroy(EdScooterHistroyEnter enter);

    /**
     * 修改车辆状态
     *
     * @param enter
     * @return
     */
    GeneralResult chanageScooterStatus(ChanageStatusEnter enter);
}
