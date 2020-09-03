package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dao.sys.PositionServiceMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.Position;
import java.util.List;

/**
 * @ClassNameSysPositionServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:25
 * @Version V1.0
 **/
public class SysPositionServiceImpl implements SysPositionService {
    @Autowired
    private PositionServiceMapper positionServiceMapper;
    /**
     * 岗位类型查询
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionTypeResult> selectPositionType(GeneralEnter enter) {
        return positionServiceMapper.positionTypeList(enter.getTenantId());
    }
}
