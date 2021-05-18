package com.redescooter.ses.web.ros.service.codebase.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.oss.ProtocolEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.starter.common.config.OssConfig;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCodebaseRsnMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.codebase.RSNService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.codebase.ExportRSNResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailScooterResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
public class RSNServiceImpl implements RSNService {

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseRsnMapper opeCodebaseRsnMapper;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Value("${excel.folder}")
    private String excelFolder;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 列表
     */
    @Override
    public PageResult<RSNListResult> getList(RSNListEnter enter) {
        int count = opeCodebaseRsnMapper.getRsnListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<RSNListResult> list = opeCodebaseRsnMapper.getRsnList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 详情
     */
    @Override
    public RSNDetailResult getDetail(StringEnter enter) {
        RSNDetailResult result = new RSNDetailResult();
        Map<String, String> map = Maps.newHashMap();
        RSNDetailScooterResult scooterInfo = new RSNDetailScooterResult();
        String rsn = enter.getKeyword();

        // rsn生成
        LambdaQueryWrapper<OpeCodebaseRsn> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseRsn::getRsn, rsn);
        qw.last("limit 1");
        OpeCodebaseRsn codebaseRsn = opeCodebaseRsnService.getOne(qw);
        map.put("1", codebaseRsn == null ? "-" : codebaseRsn.getCreatedTime().toString());

        // 入正式库
        LambdaQueryWrapper<OpeWmsStockSerialNumber> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        lqw.eq(OpeWmsStockSerialNumber::getStockType, 1);
        lqw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        lqw.last("limit 1");
        OpeWmsStockSerialNumber chSerialNumber = opeWmsStockSerialNumberService.getOne(lqw);
        map.put("2", chSerialNumber == null ? "-" : chSerialNumber.getCreatedTime().toString());

        // 进入法国仓库
        LambdaQueryWrapper<OpeWmsStockSerialNumber> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeWmsStockSerialNumber::getStockType, 2);
        wrapper.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        wrapper.last("limit 1");
        OpeWmsStockSerialNumber frSerialNumber = opeWmsStockSerialNumberService.getOne(wrapper);
        map.put("3", frSerialNumber == null ? "-" : frSerialNumber.getCreatedTime().toString());

        // 绑定询价单
        LambdaQueryWrapper<OpeCarDistribute> inquiryWrapper = new LambdaQueryWrapper<>();
        inquiryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        inquiryWrapper.eq(OpeCarDistribute::getRsn, rsn);
        inquiryWrapper.isNotNull(OpeCarDistribute::getWarehouseAccountId);
        inquiryWrapper.last("limit 1");
        OpeCarDistribute inquiryDistribute = opeCarDistributeMapper.selectOne(inquiryWrapper);
        map.put("4", inquiryDistribute == null ? "-" : inquiryDistribute.getCreatedTime().toString());

        // 绑定VIN
        LambdaQueryWrapper<OpeCarDistribute> vinWrapper = new LambdaQueryWrapper<>();
        vinWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        vinWrapper.eq(OpeCarDistribute::getRsn, rsn);
        vinWrapper.isNotNull(OpeCarDistribute::getVinCode);
        vinWrapper.last("limit 1");
        OpeCarDistribute vinDistribute = opeCarDistributeMapper.selectOne(vinWrapper);
        map.put("5", vinDistribute == null ? "-" : vinDistribute.getUpdatedTime().toString());

        // 设置软体
        map.put("6", vinDistribute == null ? "-" : vinDistribute.getUpdatedTime().toString());

        // 车辆信息
        LambdaQueryWrapper<OpeCarDistribute> scooterWrapper = new LambdaQueryWrapper<>();
        scooterWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        scooterWrapper.eq(OpeCarDistribute::getRsn, rsn);
        scooterWrapper.last("limit 1");
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(scooterWrapper);
        if (null != distribute) {
            scooterInfo.setBbi(distribute.getBbi());
            scooterInfo.setController(distribute.getController());
            scooterInfo.setElectricMachinery(distribute.getElectricMachinery());
            scooterInfo.setMeter(distribute.getMeter());
            scooterInfo.setImei(distribute.getImei());
            scooterInfo.setBluetoothAddress(distribute.getBluetoothAddress());
            scooterInfo.setRsn(distribute.getRsn());
            scooterInfo.setVin(distribute.getVinCode());
        }
        result.setNodeRecord(map);
        result.setScooterInfo(scooterInfo);
        return result;
    }

    /**
     * 导出
     */
    @Override
    public GeneralResult export(RSNListEnter enter) {
        String excelPath = "";
        List<ExportRSNResult> list = opeCodebaseRsnMapper.exportRsn(enter);
        if (CollectionUtils.isNotEmpty(list)) {
            List<Map<String, Object>> dataMap = new ArrayList<>();
            Integer i = 1;
            for (ExportRSNResult item : list) {
                item.setGenerateDate(DateUtil.dateAddHour(item.getGenerateDate(), 8));
                item.setFinishDate(DateUtil.dateAddHour(item.getFinishDate(), 8));
                dataMap.add(toMap(item, i));
                i++;
            }
            String sheetName = "RSN";
            String[] headers = {"id", "rsn", "status", "generateDate", "finishDate"};
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
                log.error("导出RSN异常", e);
            }
        }
        return new GeneralResult(excelPath);
    }

    private Map<String, Object> toMap(ExportRSNResult param, Integer i) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", i);
        map.put("rsn", param.getRsn() == null ? "--" : param.getRsn());
        map.put("status", param.getStatus() == null ? "--" : param.getStatus());
        map.put("generateDate", param.getGenerateDate() == null ? "--" : param.getGenerateDate());
        map.put("finishDate", param.getFinishDate() == null ? "--" : param.getFinishDate());
        return map;
    }

}
