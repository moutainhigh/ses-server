package com.redescooter.ses.web.ros.service.impl;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import com.redescooter.ses.api.common.enums.bom.PartsStatusEnums;
import com.redescooter.ses.api.common.enums.bom.SNClassEnums;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.service.ExcelService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.vo.bom.parts.ExpressPartsExcleData;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dao.BomRosServiceMapper;
import com.redescooter.ses.web.ros.service.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.CombinationResult;
import com.redescooter.ses.web.ros.vo.bom.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.ScooterListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;

/**
 * @ClassName:BomRosServiceImpl
 * @description: BomRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 16:17
 */
@Service
public class BomRosServiceImpl implements BomRosService {

    @Autowired
    private BomRosServiceMapper bomRosServiceMapper;

    @Autowired
    private OpePartsService partsService;

    @Autowired
    private ExcelService excelService;

    @Reference
    private IdAppService idAppService;

    @Reference
    private GenerateService generateService;

    @Override
    public ImportExcelPartsResult importParts(ImportPartsEnter enter) {

        return excelService.readExcelDataByParts(enter);
    }

    @Override
    public GeneralResult savePartsList(List<ExpressPartsExcleData> list, ImportPartsEnter enter) {

        OpeParts save = null;
        List<OpeParts> saveList = new ArrayList<>();
        String lot = generateService.getOrderNo();
        for (ExpressPartsExcleData excleData : list) {
            save = new OpeParts();
            save.setId(idAppService.getId(SequenceName.OPE_PARTS));
            save.setDr(0);
            save.setTenantId(0L);
            save.setUserId(enter.getUserId());
            save.setImportLot(lot);
            save.setStatus(PartsStatusEnums.NORMAL.getValue());
            save.setPartsType(excleData.getType());
            save.setSec(excleData.getEsc());
            save.setPartsNumber(excleData.getPartsN());
            save.setCnName(excleData.getCnName() == null ? null : excleData.getCnName());
            save.setFrName(excleData.getFrName() == null ? null : excleData.getFrName());
            save.setEnName(excleData.getEnName() == null ? null : excleData.getEnName());
            save.setSnClassFlag(SNClassEnums.getValueByCode(excleData.getSnClass()));
            save.setPartsQty(0);
            save.setSupplierId(0L);
            save.setRevision(0);
            save.setCreatedBy(enter.getUserId());
            save.setCreatedTime(new Date());
            save.setUpdatedBy(enter.getUserId());
            save.setUpdatedTime(new Date());
            saveList.add(save);
        }
        partsService.batchInsert(saveList);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @desc: 车辆列表
     * @param: enter
     * @retrn: PageResult
     * @auther: alex
     * @date: 2020/2/25 10:31
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ScooterListResult> scooterList(ScooterListEnter enter) {

        int count = bomRosServiceMapper.scooterListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ScooterListResult> scooterListResultList = bomRosServiceMapper.scooterList(enter);

        return null;
    }

    /**
     * @param enter
     * @desc: 保存整车
     * @param: enter
     * @eturn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 10:36
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveScooter(SaveScooterEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: sec 区域查询
     * @param: enter
     * @retrn: SecResult
     * @auther: alex
     * @date: 2020/2/25 12:33
     * @Version: Ros 1.2
     */
    @Override
    public List<SecResult> secList(GeneralEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 详情部件列表查询
     * @param: SaveScooterPartListEnter
     * @retrn: SaveScooterPartListResult
     * @auther: alex
     * @date: 2020/2/25 12:43
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<QueryPartListResult> partList(QueryPartListEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 整车详情
     * @param: enter
     * @retrn: ScooterDetailResult
     * @auther: alex
     * @date: 2020/2/25 13:19
     * @Version: Ros 1.2
     */
    @Override
    public ScooterDetailResult scooterDetail(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除整车的配件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 13:20
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteScooterPart(DeletePartEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除整车
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:37
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteScooter(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 套餐列表
     * @paam: enter
     * @retrn: CombinationListResult
     * @auther: alex
     * @date: 2020/2/25 14:03
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<CombinationResult> combinationList(CombinationListEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 套餐详情
     * @param: id
     * @retrn: CombinationResult
     * @auther: alex
     * @date: 2020/2/25 14:05
     * @Version: Ros 1.2
     */
    @Override
    public CombinationResult combinationDetail(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除套餐里的部件
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:07
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteCombinationPart(DeletePartEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 删除套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:08
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult deleteCombination(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @desc: 保存套餐
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 14:29
     * @Version: Ros 1.2
     */
    @Override
    public GeneralResult saveCombination(SaveCombinationEnter enter) {
        return null;
    }
}
