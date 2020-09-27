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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * 解析表格操作
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
            List<T> failList = new ArrayList<>();
            if (excelImportResult.getFailList().size() > 0) {
                for (T t : excelImportResult.getFailList()) {
                    if(!isAllFieldNull(t)){
                        failList.add(t);
                    }
                }
                excelImportResult.setFailList(failList);
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


    //判断该对象是否
    public static boolean isAllFieldNull(Object obj) throws Exception {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {
            int i = 0;
            //遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);// 得到此属性的值
            if (!Objects.isNull(val) && !f.getName().equals("errorMsg")  && !f.getName().equals("rowNum")) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
            i ++;
        }
        return flag;
    }

}
