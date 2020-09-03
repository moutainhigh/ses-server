package com.redescooter.ses.app.common.service.excel.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/9/2019 6:54 下午
 * @ClassName: ImportExcelServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class ImportExcelServiceImpl<T> implements ImportExcelService<T> {

    @Autowired
    private FileAppService fileAppService;

    private IExcelVerifyHandler iExcelVerifyHandler;

    public IExcelVerifyHandler getiExcelVerifyHandler() {
        return iExcelVerifyHandler;
    }

    @Override
    public ImportExcelService setiExcelVerifyHandler(IExcelVerifyHandler iExcelVerifyHandler) {
        this.iExcelVerifyHandler = iExcelVerifyHandler;
        return this;
    }

    /**
     * 解析表格操作 文件是通过url 在oss上下载下来后进行解析
     *
     * @param url
     * @return
     */
    @Override
    public ExcelImportResult<T> importOssExcel(String url, Class<?> pojoClass, ImportParams params) {

        if (iExcelVerifyHandler != null) {
            params.setVerifyHandler(getiExcelVerifyHandler());
        }

        long start = System.currentTimeMillis();
        ExcelImportResult<T> excelImportResult = null;
        try {
            excelImportResult = ExcelImportUtil.importExcelMore(fileAppService.download(url), pojoClass, params);
            System.out.println(System.currentTimeMillis() - start);
            if (excelImportResult.getFailList().size() > 0) {
                log.info("解析不合法数据为{}", excelImportResult.getFailList().toString());
                log.info("本次解析Excel不合法数据共计{}条数据", excelImportResult.getFailList().size());
            } else {
                log.info("解析正常数据为{}", excelImportResult.getList().toString());
                log.info("本次解析Excel正常数据共计{}条数据", excelImportResult.getList().size());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析Excel发生错误：{}", e.getMessage());
        }

        return excelImportResult;
    }

    /**
     * 解析表格操作 直接解析文件
     *
     * @param file
     * @param pojoClass
     * @param params
     * @return
     */
    @Override
    public ExcelImportResult importExcelByFile(MultipartFile file, Class<?> pojoClass, ImportParams params) {
        if (iExcelVerifyHandler != null) {
            params.setVerifyHandler(getiExcelVerifyHandler());
        }

        long start = System.currentTimeMillis();
        ExcelImportResult<T> excelImportResult = null;
        try {
            excelImportResult = ExcelImportUtil.importExcelMore(transferToFile(file), pojoClass, params);
            System.out.println(System.currentTimeMillis() - start);
            if (excelImportResult.getFailList().size() > 0) {
                log.info("解析不合法数据为{}", excelImportResult.getFailList().toString());
                log.info("本次解析Excel不合法数据共计{}条数据", excelImportResult.getFailList().size());
            } else {
                log.info("解析正常数据为{}", excelImportResult.getList().toString());
                log.info("本次解析Excel正常数据共计{}条数据", excelImportResult.getList().size());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析Excel发生错误：{}", e.getMessage());
        }
        return excelImportResult;
    }


    private File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
