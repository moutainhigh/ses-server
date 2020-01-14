package com.redescooter.ses.web.delivery.service;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:20 下午
 * @ClassName: ExcelService
 * @Function: 表格服务支持
 */
public interface ExcelService {

    /**
     * 快递订单导入
     * @param enter
     * @return
     */
    ImportExcelOrderResult importExcelByOrder(ImportExcelOrderEnter enter);

    /**
     * 表格模板下载
     * @param enter
     */
    void downloadExcelTemplate(GeneralEnter enter);
}
