package com.redescooter.ses.web.ros.controller.assign;

import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 车辆待分配控制器
 * @Author Chris
 * @Date 2020/12/27 14:48
 */
@Api(value = "车辆待分配控制器", tags = "车辆待分配控制器")
@CrossOrigin
@RestController
@RequestMapping("/tobe/assign")
public class ToBeAssignController {

    @Autowired
    private ToBeAssignService toBeAssignService;

    /**
     * 待分配列表
     */

    /**
     * 待分配列表点击分配带出数据
     */

    /**
     * 填写完座位数点击下一步
     */

    /**
     * 得到各个型号的座位数和VIN Code
     */

    /**
     * 填写完车牌点击下一步
     */

    /**
     * 得到各个型号的座位数,VIN Code和车牌
     */

    /**
     * 根据R.SN获得颜色
     */

    /**
     * 填写完R.SN并点击提交
     */


}
