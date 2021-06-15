package com.redescooter.ses.web.ros.service.codebase.impl;

import cn.hutool.poi.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.app.common.service.FileAppService;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCodebaseVinMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.codebase.VINService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.codebase.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 9:48
 */
@Service
@Slf4j
public class VINServiceImpl implements VINService {

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseVinMapper opeCodebaseVinMapper;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeColorService opeColorService;

    @Value("${excel.folder}")
    private String excelFolder;

    /**
     * 文件上传oss
     */
    @Autowired
    private FileAppService fileAppService;

    @Autowired
    private ImportExcelService importExcelService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeSpecificatTypeService typeService;

    /**
     * 车型数据源
     */
    @Override
    public List<SpecificatResult> getSpecificatTypeData(GeneralEnter enter) {
        List<SpecificatResult> resultList = Lists.newArrayList();
        LambdaQueryWrapper<OpeSpecificatType> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSpecificatType::getDr, Constant.DR_FALSE);
        List<OpeSpecificatType> list = opeSpecificatTypeService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeSpecificatType item : list) {
                SpecificatResult result = new SpecificatResult();
                result.setSpecificatTypeId(item.getId());
                result.setSpecificatTypeName(item.getSpecificatName());
                resultList.add(result);
            }
        }
        return resultList;
    }

    /**
     * 列表
     */
    @Override
    public PageResult<VINListResult> getList(VINListEnter enter) {
        int count = opeCodebaseVinMapper.getVinListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<VINListResult> list = opeCodebaseVinMapper.getVinList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 详情
     */
    @Override
    public VINDetailResult getDetail(StringEnter enter) {
        VINDetailResult result = new VINDetailResult();
        String vin = enter.getKeyword();
        Long customerId = null;

        // VIN在码库信息
        LambdaQueryWrapper<OpeCodebaseVin> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseVin::getVin, vin);
        qw.last("limit 1");
        OpeCodebaseVin codebaseVin = opeCodebaseVinService.getOne(qw);
        if (null != codebaseVin) {
            result.setVin(codebaseVin.getVin());
            result.setGenerateDate(codebaseVin.getCreatedTime());
            result.setFinishDate(codebaseVin.getUpdatedTime());
        }

        // 车辆信息
        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getVinCode, vin);
        lqw.last("limit 1");
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(lqw);
        if (null != distribute) {
            customerId = distribute.getCustomerId();
            result.setBbi(distribute.getBbi());
            result.setController(distribute.getController());
            result.setElectricMachinery(distribute.getElectricMachinery());
            result.setMeter(distribute.getMeter());
            result.setImei(distribute.getImei());
            result.setBluetoothAddress(distribute.getBluetoothAddress());
            result.setRsn(distribute.getRsn());
            String battery = distribute.getBattery();
            if (StringUtils.isBlank(battery)) {
                result.setBatteryList(new ArrayList<>());
            } else {
                String[] split = battery.split(",");
                List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                batteryList.removeAll(Collections.singleton(null));
                result.setBatteryList(batteryList);
            }
        }

        if (null != customerId) {
            // 询价单信息
            LambdaQueryWrapper<OpeCustomerInquiry> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE);
            wrapper.eq(OpeCustomerInquiry::getCustomerId, customerId);
            wrapper.last("limit 1");
            OpeCustomerInquiry inquiry = opeCustomerInquiryService.getOne(wrapper);
            if (null != inquiry) {
                LambdaQueryWrapper<OpeCustomerInquiryB> inquiryBWrapper = new LambdaQueryWrapper<>();
                inquiryBWrapper.eq(OpeCustomerInquiryB::getDr, Constant.DR_FALSE);
                inquiryBWrapper.eq(OpeCustomerInquiryB::getInquiryId, inquiry.getId());
                inquiryBWrapper.last("limit 1");
                OpeCustomerInquiryB inquiryB = opeCustomerInquiryBService.getOne(inquiryBWrapper);

                LambdaQueryWrapper<OpeCarDistribute> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
                queryWrapper.eq(OpeCarDistribute::getCustomerId, customerId);
                queryWrapper.last("limit 1");
                OpeCarDistribute inquiryDistribute = opeCarDistributeMapper.selectOne(queryWrapper);
                if (null != inquiryDistribute) {
                    result.setInquiryId(inquiry.getId());
                    result.setOrderNo(inquiry.getOrderNo());
                    result.setSpecificatTypeName(getSpecificatNameById(inquiryDistribute.getSpecificatTypeId()));
                    result.setColorName(getColorNameById(inquiryDistribute.getColorId()));
                    result.setSeatNumber(inquiryDistribute.getSeatNumber());
                    result.setBatteryNum(inquiryB.getProductQty());
                    result.setEmail(inquiry.getEmail());
                    result.setCustomerName(StringUtils.isBlank(inquiry.getFullName()) ? inquiry.getCompanyName() : inquiry.getFullName());
                }
            }
        }
        return result;
    }

    /**
     * 导出
     */
    @Override
    public GeneralResult export(VINListEnter enter) {
        String excelPath = "";
        List<ExportVINResult> list = opeCodebaseVinMapper.exportVin(enter);
        if (CollectionUtils.isEmpty(list)) {
            return new GeneralResult(excelPath);
        }
        List<Map<String, Object>> dataMap = new ArrayList<>();

        Integer i = 1;
        for (ExportVINResult item : list) {
            if (StringUtils.isNotBlank(enter.getTimeZone()) && StringManaConstant.GMT_TIME_ZONE.equals(enter.getTimeZone())) {
                item.setGenerateDate(DateUtil.dateAddHour(item.getGenerateDate(), 8));
                item.setFinishDate(DateUtil.dateAddHour(item.getFinishDate(), 8));
            }
            dataMap.add(toMap(item, i));
            i++;
        }
        String sheetName = "VIN";
        String[] headers = {"id", "vin", "status", "generateDate", "updaterTime"};
        String exportExcelName = String.valueOf(System.currentTimeMillis());
        try {
            String path = ExcelUtil.exportExcel(sheetName, dataMap, headers, exportExcelName, excelFolder);
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), in);
            String uplaod = fileAppService.uplaod(null, null, multipartFile);
            if (file.exists()) {
                file.delete();
            }
            return new GeneralResult(uplaod);
        } catch (Exception e) {
            log.error("导出VIN异常", e);
        }
        return new GeneralResult(excelPath);
    }

    /**
     * @Title: saveScooterImportExcel
     * @Description: // 导入VIN
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @Date: 2021/6/11 4:44 下午
     * @Author: Charles
     */
    @Transactional
    @Override
    public Boolean importVin(MultipartFile file) {
        List<List<Object>> read = ExcelUtil.readExcel(file);
        List<VinImportData> vinImportDataList = new ArrayList<>();
        List<String> vinCodes = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            List list = read.get(i);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            if (StringUtils.isBlank(list.get(1).toString())) {
                continue;
            }
            VinImportData data = new VinImportData();
            data.setSeatNumber(Integer.parseInt(list.get(0).toString()));
            data.setVin(list.get(1).toString());
            vinCodes.add(list.get(1).toString());
            vinImportDataList.add(data);
        }
        List<OpeCodebaseVin> vinList = Lists.newArrayList();

        LambdaQueryWrapper<OpeCodebaseVin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
        queryWrapper.in(OpeCodebaseVin::getVin, vinCodes);
        List<OpeCodebaseVin> list = opeCodebaseVinService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.BASECODE_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.BASECODE_EXISTS_CODEBASE.getMessage());
        }

        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_DR, Constant.DR_FALSE);
        List<OpeSpecificatType> typeList = typeService.list(qw);
        Map<String, Long> map = typeList.stream().collect(Collectors.toMap(OpeSpecificatType::getSpecificatName, OpeSpecificatType::getId));
        for (VinImportData vinImportData : vinImportDataList) {
            String vinCode = vinImportData.getVin().substring(0, 7);
            OpeCodebaseVin vin = new OpeCodebaseVin();
            if (StringUtils.equals(vinCode, "VXSR2A3")) {
                vin.setSpecificatTypeId(map.get("E100"));
            }
            if (StringUtils.equals(vinCode, "VXSR2A2")) {
                vin.setSpecificatTypeId(map.get("E50"));
            }
            if (StringUtils.equals(vinCode, "VXSR2A4")) {
                vin.setSpecificatTypeId(map.get("E125"));
            }
            vin.setId(idAppService.getId(SequenceName.OPE_CODEBASE_VIN));
            vin.setDr(Constant.DR_FALSE);
            vin.setStatus(1);
            vin.setVin(vinImportData.getVin());
            vin.setSeatNumber(vinImportData.getSeatNumber());
            vin.setCreatedTime(new Date());
            vinList.add(vin);
        }
        return opeCodebaseVinService.saveBatch(vinList);
    }

    private Map<String, Object> toMap(ExportVINResult param, Integer i) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", i);
        map.put("vin", param.getVin() == null ? "--" : param.getVin());
        map.put("status", param.getStatus() == null ? "--" : param.getStatus());
        map.put("generateDate", param.getGenerateDate() == null ? "--" : DateUtil.getTimeStr(param.getGenerateDate(), DateUtil.DEFAULT_DATETIME_FORMAT));
        map.put("updaterTime", param.getFinishDate() == null ? "--" : DateUtil.getTimeStr(param.getFinishDate(), DateUtil.DEFAULT_DATETIME_FORMAT));
        return map;
    }

    /**
     * 根据型号id获取型号名称
     */
    public String getSpecificatNameById(Long specificatId) {
        if (null == specificatId) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeService.getById(specificatId);
        if (null != specificatType) {
            String name = specificatType.getSpecificatName();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
    }

    /**
     * 根据颜色id获取颜色名称
     */
    public String getColorNameById(Long colorId) {
        if (null == colorId) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getMessage());
        }
        OpeColor color = opeColorService.getById(colorId);
        if (null != color) {
            String colorName = color.getColorName();
            if (StringUtils.isNotBlank(colorName)) {
                return colorName;
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
    }

}
