package com.redescooter.ses.web.delivery.service.express.impl;

import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.poi.EasyPoiUtils;
import com.redescooter.ses.tool.utils.DownloadFileUtil;
import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.service.express.OrderService;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 4:34 下午
 * @ClassName: OrderServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ExcelService excelService;

    /**
     * 快递订单模板下载
     *
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downloadTemplate() {
        return excelService.downloadExcelTemplate(new GeneralEnter());
    }

    @Override
    public void download(HttpServletResponse response, HttpServletRequest request) {
        try {

            String fileName = "ExpressOrder.xls";
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            String filePath = new File(ResourceUtils.getURL("classpath:excel").getPath()) + "/" + fileName;
            FileInputStream input = null;
            input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 表格订单导入
     *
     * @param enter
     * @return
     */
    @Override
    public ImportExcelOrderResult importOrders(ImportExcelOrderEnter enter) {
        ImportExcelOrderResult orderResult = excelService.readExcelDataByOrder(enter);

        //解析成功后，进行数据的保存
        if (orderResult.getSuccess()) {

        }


        return orderResult;
    }


}
