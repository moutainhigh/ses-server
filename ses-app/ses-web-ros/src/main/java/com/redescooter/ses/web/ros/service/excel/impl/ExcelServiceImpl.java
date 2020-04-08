package com.redescooter.ses.web.ros.service.excel.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.verifyhandler.PartsExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

    @Autowired
    private OpePartsService partsService;

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

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

        //1.判断productNList里面是否有重复的产品编号
        Set<String> productNSet = new HashSet<>();
        successList.forEach(su -> {
            productNSet.add(su.getPartsN());
        });
        if (productNSet.size() != successList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_REPEAT.getCode(), ExceptionCodeEnums.PARTS_NUMBER_REPEAT.getMessage());
        }

        //2.判断与数据库中部件草稿中是否有已存在的产品编号
        List<String> usingProductNumList = bomRosServiceMapper.UsingProductNumList(enter);
        if (CollectionUtils.isNotEmpty(usingProductNumList)) {
            productNSet.forEach(item -> {
                if (usingProductNumList.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_EXIST.getCode(), ExceptionCodeEnums.PARTS_NUMBER_EXIST.getMessage());
                }
            });
        }
        return partsRosService.savePartsList(successList, enter);
    }

    @Override
    public void download(String fileName, String path, HttpServletResponse response) {

    }
}
