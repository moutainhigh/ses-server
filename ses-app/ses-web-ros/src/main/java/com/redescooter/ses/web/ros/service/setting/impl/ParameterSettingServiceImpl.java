package com.redescooter.ses.web.ros.service.setting.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.setting.ParameterSettingService;
import com.redescooter.ses.web.ros.vo.setting.ParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.ParameterResult;
import com.redescooter.ses.web.ros.vo.setting.SaveParamentEnter;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:52
 *  @version：V ROS 1.7.1
 *  @Description:
 */

@Service
public class ParameterSettingServiceImpl implements ParameterSettingService {
    /**
     * 参数名称
     * @param enter
     * @return
     */
    @Override
    public PageResult<ParameterResult> list(ParameterListEnter enter) {
        List<ParameterResult> list = new ArrayList<>();
        list.add(ParameterResult.builder()
                .id(100000L)
                .groupId(100000L)
                .groupName("你猜")
                .key("我也不知道")
                .value("我也不知道")
                .createdById(100000L)
                .createdByFirtName("你猜")
                .createdByLastName("你猜")
                .upadtedById(100001L)
                .upadtedByFirtName("你猜")
                .upadtedByLastName("你猜")
                .updatedTime(new Date())
                .createdTime(new Date())
                .build());

        return PageResult.create(enter, 1, list);
    }

    /**
     * 详情
     * @param enter
     * @return
     */
    @Override
    public ParameterResult detail(IdEnter enter) {
        return ParameterResult.builder()
                .id(100000L)
                .groupId(100000L)
                .groupName("你猜")
                .key("我也不知道")
                .value("我也不知道")
                .createdById(100000L)
                .createdByFirtName("你猜")
                .createdByLastName("你猜")
                .upadtedById(100001L)
                .upadtedByFirtName("你猜")
                .upadtedByLastName("你猜")
                .updatedTime(new Date())
                .createdTime(new Date())
                .build();
    }

    /**
     * 删除
     * @param enter
     * @return
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult export(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    @Override
    public GeneralResult importParament(GeneralEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 参数保存编辑
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveParamentEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }
}