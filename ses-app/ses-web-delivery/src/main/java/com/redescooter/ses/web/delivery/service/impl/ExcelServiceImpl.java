package com.redescooter.ses.web.delivery.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.poi.EasyPoiUtils;
import com.redescooter.ses.web.delivery.service.ExcelService;
import com.redescooter.ses.web.delivery.verifyhandler.OrdersExcelVerifyHandlerImpl;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    public ImportExcelOrderResult readExcelDataByOrder(ImportExcelOrderEnter enter) {

        ImportExcelOrderResult result = new ImportExcelOrderResult();

        ExcelImportResult<ExpressOrderExcleData> excelImportResult = importExcelService.setiExcelVerifyHandler(new OrdersExcelVerifyHandlerImpl()).importOssExcel(enter.getUrl(), ExpressOrderExcleData.class, new ImportParams());

        List<ExpressOrderExcleData> successList = excelImportResult.getList();
        List<ExpressOrderExcleData> failList = excelImportResult.getFailList();
        //验证是否有不合法的Eecel数据
        if (failList.size() > 0) {
            Map<String, String> map = null;
            List<Map<String, String>> errorMsgList = new ArrayList<>();
            result.setSuccess(Boolean.FALSE);
            result.setSuccessNum(successList.size());
            result.setFailNum(failList.size());
            for (ExpressOrderExcleData excle : failList) {
                map = new HashMap<>();
                map.put(String.valueOf(excle.getRowNum()), excle.getErrorMsg());
                errorMsgList.add(map);
            }
            result.setErrorMsgList(errorMsgList);
            return result;
        }
        //表格数据为空 做逻辑判断
        if (CollectionUtils.isEmpty(successList)) {
            result.setSuccess(Boolean.FALSE);
            Map<String, String> map = new TreeMap<>();
            map.put("msg", "The table data is empty and the import failed.");
            List<Map<String, String>> mapList = new ArrayList<>();
            mapList.add(map);
            result.setSuccessNum(0);
            result.setFailNum(successList.size());
            result.setErrorMsgList(mapList);
            return result;
        }

        //数据返回
        result.setSuccess(Boolean.TRUE);
        result.setSuccessNum(successList.size());
        result.setFailNum(failList.size());
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 快递订单模板下载
     *
     * @param fileName
     * @param path
     * @param response
     * @return
     */
    @Override
    public void download(String fileName, String path, HttpServletResponse response) {
        List<ExpressOrderExcleData> list = new ArrayList<>();
        try {
            Workbook workbook = EasyPoiUtils.exportExcel(ExpressOrderExcleData.class, list, path, fileName);
            EasyPoiUtils.downLoadExcel(fileName, workbook, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
