package com.redescooter.ses.web.ros.service.codebase.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.codebase.VINService;
import com.redescooter.ses.web.ros.vo.codebase.SpecificatResult;
import com.redescooter.ses.web.ros.vo.codebase.VINDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
@Service
@Slf4j
public class VINServiceImpl implements VINService {

    /**
     * 车型数据源
     */
    @Override
    public List<SpecificatResult> getSpecificatTypeData(GeneralEnter enter) {
        return null;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<VINListResult> getList(VINListEnter enter) {
        return null;
    }

    /**
     * 详情
     */
    @Override
    public VINDetailResult getDetail(StringEnter enter) {
        return null;
    }

    /**
     * 导出
     */
    @Override
    public GeneralResult export(VINListEnter enter) {
        return null;
    }

}
