package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosCheckAnnounSafeCode;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNamePartsRosServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/23 18:01
 * @Version V1.0
 **/
@Service
public class PartsRestRosServiceImpl implements PartsRosService {

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private ExcelService excelService;

    @Override
    @Transactional
    public GeneralResult partsSave(StringEnter enter) {
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return new GeneralResult();
    }

    @Override
    public GeneralResult partsDelete(IdEnter enter) {
        opeProductionPartsService.removeById(enter.getId());
        return new GeneralResult();
    }

    @Override
    public GeneralResult partsEdit(StringEnter enter) {
        return new GeneralResult();
    }

    @Override
    public PageResult<RosPartsListResult> partsList(RosPartsListEnter enter) {
        RosPartsListResult result = new RosPartsListResult();
        result.setId(0L);
        result.setPartsNo("0000001");
        result.setPartsSec("F01");
        result.setPartsType(1);
        result.setSnClass(0);
        result.setPartsIsAssembly(0);
        result.setPartsIsForAssembly(0);
        result.setCnName("中文名称");
        result.setEnName("英文名称");
        result.setFrName("发文名称");
        result.setSupplierName("供应商名称");
        result.setProductionCycle(20);
        result.setDwgUrl("文件地址");

        return PageResult.create(enter, 1, Lists.newArrayList(result));
    }

    @Override
    public GeneralResult partsAnnoun(IdEnter enter) {
        return new GeneralResult();
    }

    @Override
    public ImportExcelPartsResult importParts(ImportPartsEnter enter) {
        ImportExcelPartsResult result = new ImportExcelPartsResult();
        // 逻辑需要调整
        return excelService.readExcelDataByParts(enter);
    }

    @Override
    public List<StaffDataResult> announUser(Long tenantId) {
        StaffDataResult result = new StaffDataResult();
        result.setPrincipal(0L);
        result.setPrincipalName("Amy");
        return Lists.newArrayList(result);
    }

    @Override
    public Boolean checkAnnounUserSafeCode(RosCheckAnnounSafeCode enter) {
        return true;
    }

    @Override
    public GeneralResult partsCopy(IdEnter enter) {
        return new GeneralResult();
    }

    @Override
    public GeneralResult partsDisable(IdEnter enter) {
        return new GeneralResult();
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String,Integer> map = new HashMap<>();
        // 1 2 分别对应草稿、部件
        Integer num1 = 0;
        Integer num2 = 0;
        map.put("1",num1);
        map.put("2",num2);
        return map;
    }

    @Override
    public GeneralResult partsExport(String id, HttpServletResponse response) {
        return new GeneralResult();
    }
}
