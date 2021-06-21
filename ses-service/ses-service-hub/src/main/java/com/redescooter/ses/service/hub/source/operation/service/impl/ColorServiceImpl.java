package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.service.hub.source.operation.dao.ColorMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeSpecificatGroup;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeSpecificatGroupService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/9 11:00
 */
@DubboService
@DS("operation")
public class ColorServiceImpl implements ColorService {

    @Resource
    private ColorMapper colorMapper;

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @Override
    public ColorDTO getColorInfoById(Long id) {
        return colorMapper.getColorInfoById(id);
    }

    @Override
    public List<SelectBaseResultDTO> getScooterColorList() {
        return colorMapper.getScooterColorList();
    }

    /**
     * 获取低速信息
     */
    @Override
    public SpecificGroupDTO getLowSpeed() {
        LambdaQueryWrapper<OpeSpecificatGroup> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSpecificatGroup::getDr, Constant.DR_FALSE);
        qw.like(OpeSpecificatGroup::getGroupName, "Low");
        qw.last("limit 1");
        OpeSpecificatGroup group = opeSpecificatGroupService.getOne(qw);
        SpecificGroupDTO result = new SpecificGroupDTO();
        result.setId(group.getId());
        result.setGroupName(group.getGroupName());
        return result;
    }

}
