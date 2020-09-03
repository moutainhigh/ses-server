package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dao.sys.PositionServiceMapper;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.vo.sys.position.PositionEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
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
@Slf4j
@Service
public class SysPositionServiceImpl implements SysPositionService {
    @Autowired
    private PositionServiceMapper positionServiceMapper;
    @Autowired
    private OpeSysPositionService opeSysPositionService;
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

    /**
     * 岗位列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionResult> list(PositionEnter enter) {

        return null;
    }
}
