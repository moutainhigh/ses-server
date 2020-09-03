package com.redescooter.ses.app.common.service.excel;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/9/2019 6:53 下午
 * @ClassName: ImportExcelService
 * @Function: TODO
 */
public interface ImportExcelService<T> {

    /**
     * 解析表格操作
     *
     * @param url
     * @return
     */
    ExcelImportResult importOssExcel(String url, Class<?> pojoClass, ImportParams params);

    /**
     * 解析表格操作
     *
     * @param file
     * @return
     */
    ExcelImportResult importExcelByFile(MultipartFile file, Class<?> pojoClass, ImportParams params);

    /**
     * 设置表格验证方式，非必须设置的
     * @param iExcelVerifyHandler
     * @return
     */
    ImportExcelService setiExcelVerifyHandler(IExcelVerifyHandler iExcelVerifyHandler);

}
