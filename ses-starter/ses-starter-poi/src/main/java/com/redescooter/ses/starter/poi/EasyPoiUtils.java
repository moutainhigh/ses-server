package com.redescooter.ses.starter.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/1/2020 1:33 下午
 * @ClassName: EasyPoiUtils
 * @Function: TODO
 */
public class EasyPoiUtils {

    /**
     * 浏览器下载excel
     *
     * @param fileName
     * @param workbook
     * @param response
     * @throws Exception
     */

    public static void downLoadExcel(String fileName, Workbook workbook ,HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel
     *
     * @param pojoClass
     * @param dataSet
     * @param path
     * @param filename
     * @throws IOException
     */
    public static Workbook exportExcel(Class<?> pojoClass, Collection<?> dataSet, String path, String filename,HttpServletResponse response) throws IOException {

        File savefile = new File(path);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, dataSet);
        FileOutputStream fos = new FileOutputStream(path + filename);
//        workbook.write(fos);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;"+filename+"=user.xls");
        workbook.write(response.getOutputStream());
        fos.close();

        return workbook;
    }

    /**
     * 根据Map创建对应的Excel(一个excel 创建多个sheet)
     *
     * @param list     list 多个Map key title 对应表格Title key entity 对应表格对应实体 key data
     *                  Collection 数据
     * @param path     路径
     * @param filename 　文件名
     * @throws IOException
     */
    public static void exportExcel(List<Map<String, Object>> list, String path, String filename) throws IOException {
        File savefile = new File(path);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);

        FileOutputStream fos = new FileOutputStream(path + filename);
        workbook.write(fos);
        fos.close();
    }


    /**
     * 导入excel
     *
     * @param file
     * @param pojoClass
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(File file, Class<?> pojoClass, ImportParams params) {
        long start = new Date().getTime();
        List<T> list = ExcelImportUtil.importExcel(file, pojoClass, params);
        return list;
    }
}