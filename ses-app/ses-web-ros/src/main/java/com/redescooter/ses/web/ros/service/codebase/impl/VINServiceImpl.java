package com.redescooter.ses.web.ros.service.codebase.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.starter.common.config.OssConfig;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCodebaseVinMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCodebaseVin;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.codebase.VINService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.codebase.ExportVINResult;
import com.redescooter.ses.web.ros.vo.codebase.SpecificatResult;
import com.redescooter.ses.web.ros.vo.codebase.VINDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private OssConfig ossConfig;

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
        if (CollectionUtils.isNotEmpty(list)) {
            for (ExportVINResult item : list) {
                if ("1".equals(item.getStatus())) {
                    item.setStatus("待分配");
                } else if ("2".equals(item.getStatus())) {
                    item.setStatus("已分配");
                }
            }
        }

        List<Map<String, Object>> dataMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Integer i = 1;
            for (ExportVINResult item : list) {
                item.setGenerateDate(DateUtil.dateAddHour(item.getGenerateDate(), 8));
                item.setFinishDate(DateUtil.dateAddHour(item.getFinishDate(), 8));
                dataMap.add(toMap(item, i));
                i++;
            }
            String sheetName = "VIN";
            String[] headers = {"id", "vin", "status", "generateDate", "finishDate"};
            String exportExcelName = String.valueOf(System.currentTimeMillis());
            try {
                String path = ExcelUtil.exportExcel(sheetName, dataMap, headers, exportExcelName, excelFolder);
                // 文件夹不存在，则进行创建
                if (!new File(excelFolder).exists()) {
                    new File(excelFolder).mkdir();
                }
                File file = new File(path);
                FileInputStream in = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), in);

                ClientConfiguration conf = new ClientConfiguration();
                conf.setProtocol(ProtocolEnums.getProtocol(ossConfig.getProtocol()));
                OSSClient ossClient;
                ossClient = new OSSClient(ossConfig.getInternalEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getSecretAccesskey(), conf);
                String fileName = System.currentTimeMillis() + ".xlsx";
                ossClient.putObject(ossConfig.getDefaultBucketName(), fileName, multipartFile.getInputStream());
                String bucket = ossConfig.getDefaultBucketName();
                excelPath = "https://" + bucket + "." + ossConfig.getPublicEndpointDomain() + "/" + fileName;
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                log.error("导出VIN异常", e);
            }
        }
        return new GeneralResult(excelPath);
    }

    private Map<String, Object> toMap(ExportVINResult param, Integer i) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", i);
        map.put("vin", param.getVin() == null ? "--" : param.getVin());
        map.put("status", param.getStatus() == null ? "--" : param.getStatus());
        map.put("generateDate", param.getGenerateDate() == null ? "--" : param.getGenerateDate());
        map.put("finishDate", param.getFinishDate() == null ? "--" : param.getFinishDate());
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
