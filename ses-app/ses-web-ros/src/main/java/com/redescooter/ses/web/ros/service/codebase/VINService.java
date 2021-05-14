package com.redescooter.ses.web.ros.service.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.vo.codebase.SpecificatTypeResult;
import com.redescooter.ses.web.ros.vo.codebase.VINDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
public interface VINService {

    /**
     * 车型数据源
     */
    List<SpecificatTypeResult> getSpecificatTypeData(GeneralEnter enter);

    /**
     * 列表
     */
    PageResult<VINListResult> getList(VINListEnter enter);

    /**
     * 详情
     */
    VINDetailResult getDetail(StringEnter enter);

    /**
     * 导出
     */
    GeneralResult export(VINListEnter enter);

}
