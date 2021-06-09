package com.redescooter.ses.web.ros.controller.delete;

import com.redescooter.ses.web.ros.service.delete.DeleteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:45
 */
@Api(value = "删除数据控制器", tags = "删除数据控制器")
@CrossOrigin
@RestController
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    private DeleteService deleteService;












}
