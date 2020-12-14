package com.redescooter.ses.web.ros.service.excel;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterEnter;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:20 下午
 * @ClassName: ExcelService
 * @Function: 表格服务支持
 */
public interface ExcelService<T extends GeneralEnter> {

    /**
     * 零部件表格导入
     *
     * @param enter
     * @return
     */
    ImportExcelPartsResult readExcelDataByParts(ImportPartsEnter enter);

    /**
     * 零部件表格导入
     *
     * @param enter
     * @return
     */
    ImportExcelPartsResult readExcelDataByParameter(ImportParameterEnter enter);
}
