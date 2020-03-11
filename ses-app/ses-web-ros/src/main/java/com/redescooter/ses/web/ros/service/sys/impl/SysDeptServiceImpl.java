package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysDeptServiceImpl
 * @Author Jerry
 * @date 2020/03/11 20:21
 * @Description:
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private OpeSysDeptService sysDeptService;

    @Override
    public GeneralResult save(SaveDeptEnter enter) {


        return null;
    }

    @Override
    public List<DeptTreeReslt> trees(GeneralEnter enter) {
        return null;
    }


    private OpeSysDept buildDept(SaveDeptEnter enter) {
        OpeSysDept dept = new OpeSysDept();
        return dept;
    }

}
