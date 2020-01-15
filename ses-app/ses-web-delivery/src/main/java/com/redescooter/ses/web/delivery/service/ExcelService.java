package com.redescooter.ses.web.delivery.service;


import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:20 下午
 * @ClassName: ExcelService
 * @Function: 表格服务支持
 */
public interface ExcelService<T extends GeneralEnter> {

    /**
     * 快递订单导入
     *
     * @param enter
     * @return
     */
    ImportExcelOrderResult readExcelDataByOrder(ImportExcelOrderEnter enter);

    /**
     * 表格模板下载
     *
     * @param t
     */
    ResponseEntity<byte[]> downloadExcelTemplate(T t);

    /**
     * 快递表格生成
     *
     * @param t
     * @return
     */
    default ResponseEntity<byte[]> exportOrderExcel(T t) {

        // 创建一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        ResponseEntity<byte[]> responseEntity = null;
        // 设置文档属性
        workbook.createInformationProperties();
        // 获取文档的文档摘要信息
        DocumentSummaryInformation sumInfo = workbook.getDocumentSummaryInformation();
        // 设置摘要信息
        sumInfo.setCompany("RedEScooter");
        sumInfo.setManager("rede");
        sumInfo.setCategory("Orer Info");
        // 创建一个新的单元格样式并将其添加到工作簿的样式表中。
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 获取与给定格式字符串匹配的格式索引，自动将“文本”转换为excel的格式字符串来表示文本
        short format = HSSFDataFormat.getBuiltinFormat(DateUtil.DEFAULT_DATETIME_FORMAT);
        // 设置表格中的日期格式,设置数据格式(必须是有效格式)
        cellStyle.setDataFormat(format);

        // 为这个HSSFWorkbook创建一个HSSFSheet，将它添加到工作表中
        HSSFSheet sheet = workbook.createSheet("Orders-List");

        // 设置表格列名
        // 在工作表中创建一个新行
        HSSFRow row = sheet.createRow(0);
        // 在行中创建新的单元格,参数为列号
        HSSFCell cell0 = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        HSSFCell cell4 = row.createCell(4);
        HSSFCell cell5 = row.createCell(4);
        HSSFCell cell6 = row.createCell(4);
        HSSFCell cell7 = row.createCell(4);
        HSSFCell cell8 = row.createCell(4);
        HSSFCell cell9 = row.createCell(4);
        HSSFCell cell10 = row.createCell(4);
        HSSFCell cell11 = row.createCell(4);
        HSSFCell cell12 = row.createCell(4);
        HSSFCell cell13 = row.createCell(4);
        HSSFCell cell14 = row.createCell(4);

        // 为单元格设置一个字符串值
        cell0.setCellValue(ExpressOrderExcleData.CUSTOMER_REFERENCE);
        cell1.setCellValue("昵称");
        cell2.setCellValue("段位");
        cell3.setCellValue("创建时间");
        cell4.setCellValue("是否可用");

        // 创建一个http请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置，参数：1.控制方式-内嵌，2.文件名，在浏览器需转换格式
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("Orders-template.xls".getBytes(Constant.UTF_8), Constant.UTF_8));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 创建一个字节数组输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            // 使用给定的主体、头和状态代码创建一个新的http实体
            responseEntity = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }


}
