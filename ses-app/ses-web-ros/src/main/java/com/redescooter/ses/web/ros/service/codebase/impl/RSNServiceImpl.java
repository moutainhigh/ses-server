package com.redescooter.ses.web.ros.service.codebase.impl;

import cn.hutool.poi.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
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
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCodebaseRsnMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.codebase.RSNService;
import com.redescooter.ses.web.ros.utils.ExcelUtil;
import com.redescooter.ses.web.ros.vo.codebase.ExportRSNResult;
import com.redescooter.ses.web.ros.vo.codebase.Node;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNDetailScooterResult;
import com.redescooter.ses.web.ros.vo.codebase.RSNListEnter;
import com.redescooter.ses.web.ros.vo.codebase.RSNListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
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
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

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
        List<Node> nodes = Lists.newArrayList();
        RSNDetailScooterResult scooterInfo = new RSNDetailScooterResult();
        String rsn = enter.getKeyword();
        scooterInfo.setRsn(rsn);

        // rsn生成
        LambdaQueryWrapper<OpeCodebaseRsn> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        qw.eq(OpeCodebaseRsn::getRsn, rsn);
        qw.last("limit 1");
        OpeCodebaseRsn codebaseRsn = opeCodebaseRsnService.getOne(qw);
        nodes.add(new Node("1", codebaseRsn == null ? "-" : DateUtil.getTimeStr(codebaseRsn.getCreatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));

        // 入正式库
        LambdaQueryWrapper<OpeWmsStockSerialNumber> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        lqw.eq(OpeWmsStockSerialNumber::getStockType, 1);
        lqw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        lqw.last("limit 1");
        OpeWmsStockSerialNumber chSerialNumber = opeWmsStockSerialNumberService.getOne(lqw);
        nodes.add(new Node("2", chSerialNumber == null ? "-" : DateUtil.getTimeStr(chSerialNumber.getCreatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));

        // 进入法国仓库
        LambdaQueryWrapper<OpeWmsStockSerialNumber> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeWmsStockSerialNumber::getStockType, 2);
        wrapper.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        wrapper.last("limit 1");
        OpeWmsStockSerialNumber frSerialNumber = opeWmsStockSerialNumberService.getOne(wrapper);
        nodes.add(new Node("3", frSerialNumber == null ? "-" : DateUtil.getTimeStr(frSerialNumber.getCreatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));

        // 绑定VIN
        LambdaQueryWrapper<OpeCarDistribute> vinWrapper = new LambdaQueryWrapper<>();
        vinWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        vinWrapper.eq(OpeCarDistribute::getRsn, rsn);
        vinWrapper.isNotNull(OpeCarDistribute::getVinCode);
        vinWrapper.last("limit 1");
        OpeCarDistribute vinDistribute = opeCarDistributeMapper.selectOne(vinWrapper);
        nodes.add(new Node("4", vinDistribute == null || null == vinDistribute.getUpdatedTime() ? "-" : DateUtil.getTimeStr(vinDistribute.getUpdatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));

        // 绑定询价单
        LambdaQueryWrapper<OpeCarDistribute> inquiryWrapper = new LambdaQueryWrapper<>();
        inquiryWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        inquiryWrapper.eq(OpeCarDistribute::getRsn, rsn);
        inquiryWrapper.isNotNull(OpeCarDistribute::getRsn);
        inquiryWrapper.isNotNull(OpeCarDistribute::getTabletSn);
        inquiryWrapper.isNotNull(OpeCarDistribute::getBluetoothAddress);
        inquiryWrapper.last("limit 1");
        OpeCarDistribute inquiryDistribute = opeCarDistributeMapper.selectOne(inquiryWrapper);
        nodes.add(new Node("5", inquiryDistribute == null || null == inquiryDistribute.getUpdatedTime() ? "-" : DateUtil.getTimeStr(inquiryDistribute.getUpdatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));

        // 设置软体
        LambdaQueryWrapper<OpeCarDistribute> setWrapper = new LambdaQueryWrapper<>();
        setWrapper.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        setWrapper.eq(OpeCarDistribute::getRsn, rsn);
        setWrapper.last("limit 1");
        OpeCarDistribute setModel = opeCarDistributeMapper.selectOne(setWrapper);
        if (null != setModel) {
            LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
            nodeWrapper.eq(OpeCarDistributeNode::getDr, Constant.DR_FALSE);
            nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, setModel.getCustomerId());
            nodeWrapper.last("limit 1");
            OpeCarDistributeNode node = opeCarDistributeNodeMapper.selectOne(nodeWrapper);
            if (null != node && node.getAppNode() == 6 && node.getFlag() == 2) {
                nodes.add(new Node("6", DateUtil.getTimeStr(setModel.getUpdatedTime(), DateUtil.DEFAULT_DATETIME_FORMAT)));
            } else {
                nodes.add(new Node("6", "-"));
            }
        } else {
            nodes.add(new Node("6", "-"));
        }

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
            scooterInfo.setVin(distribute.getVinCode());
        }
        result.setNodes(nodes);
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
        if (CollectionUtils.isEmpty(list)) {
            return new GeneralResult(excelPath);
        }
        List<Map<String, Object>> dataMap = new ArrayList<>();
        Integer i = 1;
        for (ExportRSNResult item : list) {
            if (StringUtils.isNotBlank(enter.getTimeZone()) && StringManaConstant.GMT_TIME_ZONE.equals(enter.getTimeZone())) {
                item.setGenerateDate(DateUtil.dateAddHour(item.getGenerateDate(), 8));
                item.setFinishDate(DateUtil.dateAddHour(item.getFinishDate(), 8));
            }
            dataMap.add(toMap(item, i));
            i++;
        }
        String sheetName = "RSN";
        String[] headers = {"id", "rsn", "status", "generateDate", "updaterTime"};
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
            log.error("导出RSN异常", e);
        }
        return new GeneralResult(excelPath);
    }

    @Transactional
    @Override
    public Boolean importRsn(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        long size = file.getSize();
        if (StringUtils.isBlank(fileName) || size == 0) {
            throw new SesWebRosException(ExceptionCodeEnums.EXCEL_IMPORT_DATA_IS_EMPTY.getCode(), ExceptionCodeEnums.EXCEL_IMPORT_DATA_IS_EMPTY.getMessage());
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.EXCEL_IMPORT_DATA_IS_EMPTY.getCode(), ExceptionCodeEnums.EXCEL_IMPORT_DATA_IS_EMPTY.getMessage());
        }
        ExcelReader excelReader = cn.hutool.poi.excel.ExcelUtil.getReader(inputStream);
        List<List<Object>> read = excelReader.read(1, excelReader.getRowCount());
        if (CollectionUtils.isEmpty(read)) {
            throw new SesWebRosException(ExceptionCodeEnums.EXCEL_NO_DATA.getCode(), ExceptionCodeEnums.EXCEL_NO_DATA.getMessage());
        }
        List<String> rsnCodes = Lists.newArrayList();
        for (int i = 0; i < read.size(); i++) {
            List list = read.get(i);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            rsnCodes.addAll(list);
        }
        List<OpeCodebaseRsn> rsnList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCodebaseRsn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        queryWrapper.in(OpeCodebaseRsn::getRsn, rsnCodes);
        List<OpeCodebaseRsn> list = opeCodebaseRsnService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.VIN_EXISTS_CODEBASE.getCode(), ExceptionCodeEnums.VIN_EXISTS_CODEBASE.getMessage());
        }
        for (String rsnCode : rsnCodes) {
            OpeCodebaseRsn rsn = new OpeCodebaseRsn();
            rsn.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RSN));
            rsn.setDr(Constant.DR_FALSE);
            rsn.setStatus(1);
            rsn.setRsn(rsnCode);
            rsn.setCreatedTime(new Date());
            rsnList.add(rsn);
        }
        return opeCodebaseRsnService.saveBatch(rsnList);
    }

    private Map<String, Object> toMap(ExportRSNResult param, Integer i) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", i);
        map.put("rsn", param.getRsn() == null ? "--" : param.getRsn());
        map.put("status", param.getStatus() == null ? "--" : param.getStatus());
        map.put("generateDate", param.getGenerateDate() == null ? "--" : DateUtil.getTimeStr(param.getGenerateDate(), DateUtil.DEFAULT_DATETIME_FORMAT));
        map.put("updaterTime", param.getFinishDate() == null ? "--" : DateUtil.getTimeStr(param.getFinishDate(), DateUtil.DEFAULT_DATETIME_FORMAT));
        return map;
    }

}
