package com.redescooter.ses.web.delivery.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:14 下午
 * @ClassName: ExcelServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ImportExcelService importExcelService;

    /**
     * 快递订单导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelOrderResult importExcelByOrder(ImportExcelOrderEnter enter) {

        ImportExcelOrderResult result = new ImportExcelOrderResult();

        ExcelImportResult<ExpressOrderExcleData> excelList = importExcelService.setiExcelVerifyHandler(null).importOssExcel(enter.getUrl(), ExpressOrderExcleData.class, new ImportParams());

        excelList.getList().forEach(date -> {
            System.out.println(date.toString());
            System.out.println("-------------");
        });

        return null;
    }

    /**
     * 表格模板下载
     *
     * @param enter
     */
    @Override
    public void downloadExcelTemplate(GeneralEnter enter) {

    }
}
