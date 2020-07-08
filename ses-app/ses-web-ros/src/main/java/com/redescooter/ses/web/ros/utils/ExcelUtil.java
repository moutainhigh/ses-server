package com.redescooter.ses.web.ros.utils;

import com.redescooter.ses.app.common.service.FileAppService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassNameExcelUtil
 * @Description
 * @Author Aleks
 * @Date2020/7/6 11:24
 * @Version V1.0
 **/
public class ExcelUtil {

    @Autowired
    private FileAppService fileAppService;

    public  static String exportExcel(String sheetName, List<Map<String, Object>> dataList,
                                    String[] headers,String exportExcelName,String path) {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        // 生成表格中非标题栏的样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
//        style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成表格中非标题栏的字体
        XSSFFont font = workbook.createFont();
//        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);


        // 设置表格标题栏的样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
//        titleStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置标题栏字体
        XSSFFont titleFont = workbook.createFont();
//        titleFont.setColor(HSSFColor.WHITE.index);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBold(true);
        // 把字体应用到当前的样式
        titleStyle.setFont(titleFont);

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> data = it.next();
            int i = 0;
            for(String key : data.keySet()){
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                XSSFRichTextString text = new XSSFRichTextString(data.get(key)+"");
                cell.setCellValue(text);
                i++;
            }
        }
        OutputStream out = null;
        String tmpPath = "";
        try {
            tmpPath =new StringBuilder(path).append("/").append(exportExcelName).append(".xlsx").toString();
            out = new FileOutputStream(tmpPath);
            workbook.write(out);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tmpPath;
    }


//    public static void main(String[] args) {
//        List<Map<String, Object>> data = new ArrayList<>();
//        // 使用 LinkedHashMap 保证有序，即标题和数据对应上
//        Map<String, Object> map1 = new LinkedHashMap<>();
//        map1.put("id", 1);
//        map1.put("name", "张三");
//        map1.put("age", 23);
//        map1.put("sex", "男");
//
//        Map<String, Object> map2 = new LinkedHashMap<>();
//        map2.put("id", 2);
//        map2.put("name", "李四");
//        map2.put("age", 20);
//        map2.put("sex", "女");
//
//        data.add(map1);
//        data.add(map2);
//        String sheetName = "导出的数据";
//        String[] headers = {"ID","名称","年龄","性别"};
//        String exportExcelName = "表格";
//        exportExcel(sheetName, data, headers, exportExcelName);
//    }


    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        //第一步，创建一个HSSFWorkbook，对应一个Excel文件</span>
        if (wb ==  null )
            wb  =  new HSSFWorkbook();

        //第二步，在workbook中添加一个sheet,对应Excel文件中的sheet</span>
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制</span>
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.); // 创建一个居中格式

        // 声明列对象
        HSSFCell cell =  null ;

        // 创建标题
        for ( int  i = 0; i < title.length; i++ ) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for  ( int i = 0; i< values.length; i++ ) {
            row  = sheet.createRow(i );
            for( int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象</span>
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    public static void main(String[] args) {
        String[] title = {"ID","名称","年龄","性别"};
        String fileName = String.valueOf(System.currentTimeMillis());
        String sheetName = "询价单";
        String[][] content = new String[4][4];
        try {
            for (int i = 0; i < 4; i++) {
                content[i][0] = "123";
                content[i][1] = "456";
                content[i][2] = "789";
                content[i][3] = "147";
            }
        }catch (Exception e){

        }
        HSSFWorkbook wb = getHSSFWorkbook(sheetName,title,content,null);
        try {
//            this.setResponseHeader(res);
        }catch (Exception e){

        }

    }

    private  void setResponseHeader(HttpServletResponse response,String fileName){
        try {
            try {
                fileName = new String(fileName.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        }catch (Exception e){

        }
    }

    public static HSSFWorkbook exportExcelTwo(String title,String[] headers,List mapList,OutputStream out){
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽度为15个字符
        sheet.setDefaultColumnWidth(20);
        //生成一个样式，用来设置标题样式
        HSSFCellStyle style = workbook.createCellStyle();
        //生成一个字体
        HSSFFont font = workbook.createFont();
//        font.setColor(HSSFColor.VIOLET.index);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式,用于设置内容样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
