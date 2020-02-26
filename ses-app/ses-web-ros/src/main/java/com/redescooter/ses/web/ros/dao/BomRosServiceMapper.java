package com.redescooter.ses.web.ros.dao;

import java.util.List;

import com.redescooter.ses.web.ros.vo.bom.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.ScooterListResult;

/**
 * @ClassName:BomRosServiceMapper
 * @description: BomRosServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:18
 */
public interface BomRosServiceMapper {

    /**
     * @desc: 整车列表
     * @paam: enter
     * @retrn: int
     * @auther: alex
     * @date: 2020/2/26 16:22
     * @Version: Ros 1.2
     */
    int scooterListCount(ScooterListEnter enter);

    /**
     * @desc: 整车列表
     * @param: enter
     * @retrn: List<ScooterListResult>
     * @auther: alex
     * @date: 2020/2/26 16:34
     * @Version: Ros 1.2
     */
    List<ScooterListResult> scooterList(ScooterListEnter enter);
}
