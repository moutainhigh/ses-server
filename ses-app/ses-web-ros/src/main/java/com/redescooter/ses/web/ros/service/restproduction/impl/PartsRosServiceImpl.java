package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
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

import java.util.List;

/**
 * @ClassNamePartsRosServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/23 18:01
 * @Version V1.0
 **/
@Service
public class PartsRosServiceImpl implements PartsRosService {

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private ExcelService excelService;

    @Override
    @Transactional
    public GeneralResult partsSave(List<RosPartsSaveOrUpdateEnter> enter) {

        return new GeneralResult();
    }

    @Override
    public GeneralResult partsDelete(IdEnter enter) {
        opeProductionPartsService.removeById(enter.getId());
        return new GeneralResult();
    }

    @Override
    public GeneralResult partsEdit(List<RosPartsSaveOrUpdateEnter> enter) {
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
}
