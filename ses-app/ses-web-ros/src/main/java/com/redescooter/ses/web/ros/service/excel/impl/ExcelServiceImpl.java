package com.redescooter.ses.web.ros.service.excel.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.BooleanEnter;
import com.redescooter.ses.api.foundation.service.setting.ParameterSettingService;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.web.ros.dao.bom.BomRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.bom.PartsRosService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.setting.RosParameterService;
import com.redescooter.ses.web.ros.verifyhandler.ParameterExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.verifyhandler.PartsExcelVerifyHandlerImpl;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterEnter;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterExcleData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
    private BomRosServiceMapper bomRosServiceMapper;

    @Autowired
    private OpePartsDraftService opePartsDraftService;

    @Autowired
    private RosParameterService rosParameterService;

    @Autowired
    private ParameterSettingService parameterSettingService;

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
        } else {
            // 验证导入的PartsN在数据库中是否存在
            List<String> partNos = successList.stream().map(ExpressPartsExcleData::getPartsN).collect(Collectors.toList());
            QueryWrapper<OpePartsDraft> qw = new QueryWrapper<>();
            qw.in(OpePartsDraft.COL_PARTS_NUMBER, partNos);
            List<OpePartsDraft> partsDraftList = opePartsDraftService.list(qw);
            if (CollectionUtils.isNotEmpty(partsDraftList)) {
                // 说明已存在这些partN了
                Map<String, String> map = null;
                List<Map<String, String>> errorMsgList = new ArrayList<>();
                result.setSuccess(Boolean.FALSE);
                result.setSuccessNum(successList.size());
                result.setFailNum(partsDraftList.size());
                List<String> list = partsDraftList.stream().map(OpePartsDraft::getPartsNumber).collect(Collectors.toList());
                List<ExpressPartsExcleData> fail = new ArrayList<>();
                for (ExpressPartsExcleData data : successList) {
                    if (list.contains(data.getPartsN())) {
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
     * 参数设置导入
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportExcelPartsResult readExcelDataByParameter(ImportParameterEnter enter) {
        ImportExcelPartsResult result = new ImportExcelPartsResult();

        ExcelImportResult<ImportParameterExcleData> excelImportResult = importExcelService.setiExcelVerifyHandler(new ParameterExcelVerifyHandlerImpl()).importOssExcel(enter.getUrl(),
                ImportParameterExcleData.class, new ImportParams());
        if (excelImportResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }

        List<ImportParameterExcleData> successList = excelImportResult.getList();
        List<ImportParameterExcleData> failList = excelImportResult.getFailList();
        //验证是否有不合法的Eecel数据
        if (CollectionUtils.isNotEmpty(failList)) {
            Map<String, String> map = null;
            List<Map<String, String>> errorMsgList = new ArrayList<>();
            result.setSuccess(Boolean.FALSE);
            result.setSuccessNum(successList.size());
            result.setFailNum(failList.size());
            for (ImportParameterExcleData excle : failList) {
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

        //分组校验
        BooleanEnter booleanEnter = new BooleanEnter(Boolean.FALSE);
        booleanEnter.setSystemId(SystemTypeEnums.REDE_ROS.getCode());
        List<ParameterGroupResultList> parameterGroupResultList = parameterSettingService.groupList(booleanEnter);

        if (CollectionUtils.isEmpty(parameterGroupResultList)) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }
        //分组校验
        successList.forEach(item -> {
            Boolean groupExist = Boolean.FALSE;
            for (ParameterGroupResultList group : parameterGroupResultList) {
                if (StringUtils.equals(group.getName(), item.getGroupName())) {
                    groupExist = Boolean.TRUE;
                    break;
                }
            }
            if (!groupExist) {
                throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
            }
        });

        //列表
        List<ParameterResult> parameterResultList = parameterSettingService.checkParameterName(successList.stream().map(ImportParameterExcleData::getParameterName).collect(Collectors.toList()),
                SystemTypeEnums.REDE_ROS.getValue());

        if (CollectionUtils.isNotEmpty(parameterResultList)) {
            Map<String, String> map = null;
            List<Map<String, String>> errorMsgList = new ArrayList<>();
            result.setSuccess(Boolean.FALSE);
            result.setSuccessNum(successList.size());
            result.setFailNum(parameterResultList.size());
            List<ImportParameterExcleData> fail = new ArrayList<>();
            for (ImportParameterExcleData item : successList) {
                if (parameterResultList.stream().map(ParameterResult::getParameterName).collect(Collectors.toSet()).contains(item.getParameterName())) {
                    map = new HashMap<>();
                    map.put(String.valueOf(item.getRowNum()), "ParameterName Already exists");
                    errorMsgList.add(map);
                    fail.add(item);
                }
            }
            result.setErrorMsgList(errorMsgList);
            return result;
        }
        rosParameterService.saveParameterBatch(enter, successList);

        result.setSuccess(Boolean.TRUE);
        result.setSuccessNum(successList.size());
        result.setFailNum(0);
        result.setRequestId(enter.getRequestId());
        return result;
    }
}
