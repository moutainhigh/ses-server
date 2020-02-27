package com.redescooter.ses.web.ros.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.BomRosService;
import com.redescooter.ses.web.ros.service.ExcelService;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.verifyhandler.PartsExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ClassName ExcelServiceImpl
 * @Author Jerry
 * @date 2020/02/26 17:05
 * @Description:
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private PartsRosService partsRosService;


    @Override
    public ImportExcelPartsResult readExcelDataByParts(ImportPartsEnter enter) {

        ImportExcelPartsResult result = new ImportExcelPartsResult();

        ExcelImportResult<ExpressPartsExcleData> excelImportResult = importExcelService.setiExcelVerifyHandler(new PartsExcelVerifyHandlerImpl()).importOssExcel(enter.getUrl(),
                ExpressPartsExcleData.class, new ImportParams());
        if (excelImportResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }

        List<ExpressPartsExcleData> successList = excelImportResult.getList();
        List<ExpressPartsExcleData> failList = excelImportResult.getFailList();

        //验证是否有不合法的Eecel数据
        if (failList.size() > 0) {
            Map<String, String> map = null;
            List<Map<String, String>> errorMsgList = new ArrayList<>();
            result.setSuccess(Boolean.FALSE);
            result.setSuccessNum(successList.size());
            result.setFailNum(failList.size());
            for (ExpressPartsExcleData excle : failList) {
                map = new HashMap<>();
                map.put(String.valueOf(excle.getRowNum()), excle.getErrorMsg());
                errorMsgList.add(map);
            }
            result.setErrorMsgList(errorMsgList);
            return result;
        }
        if (CollectionUtils.isEmpty(successList)) {
            //表格数据为空 做逻辑判断
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

        return partsRosService.savePartsList(successList, enter);
    }

    @Override
    public void download(String fileName, String path, HttpServletResponse response) {

    }
}
