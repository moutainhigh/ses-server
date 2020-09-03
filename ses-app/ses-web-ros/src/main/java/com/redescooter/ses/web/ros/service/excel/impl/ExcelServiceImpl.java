package com.redescooter.ses.web.ros.service.excel.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.bom.PartsRosService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyDocumentService;
import com.redescooter.ses.web.ros.verifyhandler.PartsExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyImportExcelResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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
    private SellsyDocumentService sellsyDocumentService;

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

    @Autowired
    private OpePartsDraftService opePartsDraftService;

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
        }else {
            // 验证导入的PartsN在数据库中是否存在
            List<String> partNos = successList.stream().map(ExpressPartsExcleData::getPartsN).collect(Collectors.toList());
            QueryWrapper<OpePartsDraft> qw = new QueryWrapper<>();
            qw.in(OpePartsDraft.COL_PARTS_NUMBER,partNos);
            List<OpePartsDraft> partsDraftList  = opePartsDraftService.list(qw);
            if(CollectionUtils.isNotEmpty(partsDraftList)){
                // 说明已存在这些partN了
                Map<String, String> map = null;
                List<Map<String, String>> errorMsgList = new ArrayList<>();
                result.setSuccess(Boolean.FALSE);
                result.setSuccessNum(successList.size());
                result.setFailNum(partsDraftList.size());
                List<String> list = partsDraftList.stream().map(OpePartsDraft::getPartsNumber).collect(Collectors.toList());
                List<ExpressPartsExcleData> fail = new ArrayList<>();
                for (ExpressPartsExcleData data : successList) {
                    if(list.contains(data.getPartsN())){
                        fail.add(data);
                    }
                }
                for (ExpressPartsExcleData excle : fail) {
                    map = new HashMap<>();
                    map.put(String.valueOf(excle.getRowNum()), "PartsN Already exists");
                    errorMsgList.add(map);
                }
                result.setErrorMsgList(errorMsgList);
                return result;
            }
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
        List<String> usingProductNumList = bomRosServiceMapper.checkProductNums(enter);
        if (CollectionUtils.isNotEmpty(usingProductNumList)) {
            productNSet.forEach(item -> {
                if (usingProductNumList.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_EXIST.getCode(), ExceptionCodeEnums.PARTS_NUMBER_EXIST.getMessage());
                }
            });
        }
        return partsRosService.savePartsList(successList, enter);
    }

    /**
     * 导入零部件
     *
     * @param file
     * @return
     */
    @Override
    public SellsyImportExcelResult readExcelDataBySellsy(MultipartFile file) {
        SellsyImportExcelResult result = new SellsyImportExcelResult();
        ImportParams importParams = new ImportParams();
        ExcelImportResult<SellsyExcleData> excelImportResult = importExcelService.setiExcelVerifyHandler(new SellsyExcelVerifyHandlerImpl()).importExcelByFile(file,
                SellsyExcleData.class, importParams);
        if (excelImportResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }

        List<SellsyExcleData> successList = excelImportResult.getList();
        List<SellsyExcleData> failList = excelImportResult.getFailList();

        //验证是否有不合法的Eecel数据
        if (failList.size() > 0) {
            Map<String, String> map = null;
            List<Map<String, String>> errorMsgList = new ArrayList<>();
            result.setSuccess(Boolean.FALSE);
            result.setSuccessNum(successList.size());
            result.setFailNum(failList.size());
            for (SellsyExcleData excle : failList) {
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
        return sellsyDocumentService.saveSellsyInvoid(successList);
    }

    @Override
    public void download(String fileName, String path) {

    }

    /**
     * 询价单数据导出
     *
     * @param fileName
     * @param path
     */
    @Override
    public void downloadInquiryExcel(String fileName, String path) {

    }
}
