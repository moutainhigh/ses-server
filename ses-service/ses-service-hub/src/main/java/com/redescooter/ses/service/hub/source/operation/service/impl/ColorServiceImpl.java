package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.service.hub.source.operation.dao.ColorMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/9 11:00
 */
@DubboService
public class ColorServiceImpl implements ColorService {

    @Resource
    private ColorMapper colorMapper;

    @Override
    public ColorDTO getColorInfoById(Long id) {
        return colorMapper.getColorInfoById(id);
    }

    @Override
    public List<SelectBaseResultDTO> getScooterColorList() {
        return colorMapper.getScooterColorList();
    }

}
