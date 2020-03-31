package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.List;

/**
 * @ClassName:AssemblyServiceMapper
 * @description: AssemblyServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 16:12
 */
public interface AssemblyServiceMapper {
    /**
     * 类型状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByTypes(GeneralEnter enter);
}
