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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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

        String filePath = "excel/ExpressOrder.xlsx";
        Resource resource = new ClassPathResource(filePath);//用来读取resources下的文件
        InputStream is = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            File file = resource.getFile();
            if (!file.exists()) {
                log.error("模板文件不存在");
            }
            is = new FileInputStream(file);
            os = response.getOutputStream();
            bis = new BufferedInputStream(is);
            //设置响应头信息
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream; charset=UTF-8");
            StringBuffer contentDisposition = new StringBuffer("attachment; filename=\"");
            String fileName = new String(file.getName().getBytes(), "utf-8");
            contentDisposition.append(fileName).append("\"");
            response.setHeader("Content-disposition", contentDisposition.toString());
            //边读边写
            byte[] buffer = new byte[500];
            int i;
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("模板文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
