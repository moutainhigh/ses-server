package com.redescooter.ses.web.ros.service.codebase.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.codebase.RSNService;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
@Service
@Slf4j
public class RSNServiceImpl implements RSNService {

    /**
     * 列表
     */
    @Override
    public PageResult<RSNListResult> getList(RSNListEnter enter) {
        return null;
    }

    /**
     * 详情
     */
    @Override
    public RSNDetailResult getDetail(StringEnter enter) {
        return null;
    }

    /**
     * 导出
     */
    @Override
    public GeneralResult export(RSNListEnter enter) {
        return null;
    }

}
