package com.redescooter.ses.web.ros.service.restproduction.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsDraftServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsSec;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsSecService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsRelationService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeProductionQualityTempateService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.verifyhandler.RosExcelParse;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.DraftAnnounEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosCheckAnnounSafeCodeEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosParseExcelData;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsBatchOpEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsExportEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosRepeatResult;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private OpeProductionPartsDraftService opeProductionPartsDraftService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private StaffServiceMapper staffServiceMapper;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private RosProductionPartsDraftServiceMapper rosProductionPartsDraftServiceMapper;

    @Autowired
    private RosProductionPartsServiceMapper rosProductionPartsServiceMapper;

    @Value("${Request.privateKey}")
    private String privatekey;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private OpeSupplierService opeSupplierService;

    @Autowired
    private OpePartsSecService opePartsSecService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    @Autowired
    private OpeProductionQualityTempateService opeProductionQualityTempateService;

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult partsSave(StringEnter enter) {
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // ?????????????????????????????????????????????????????????????????????????????????
        List<OpeProductionParts> partsList = new ArrayList<>();
        List<OpeProductionPartsDraft> draftList = new ArrayList<>();

        for (RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter : enters) {
            // ?????????????????????????????????
            if (Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getPartsNo())) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            // ????????????????????????????????????
            // ??????????????????????????? ??????????????????????????????????????????????????????
            Integer isAnnoun = rosPartsSaveOrUpdateEnter.getIsAnnoun();
            switch (isAnnoun) {
                case 0:
                    // ????????? ???????????????
                    OpeProductionPartsDraft draft = new OpeProductionPartsDraft();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, draft);
                    draft.setPartsSec(rosPartsSaveOrUpdateEnter.getPartsSec());
                    draft.setDwg(rosPartsSaveOrUpdateEnter.getDwg());
                    draft.setCreatedBy(enter.getUserId());
                    draft.setUpdatedBy(enter.getUserId());
                    draft.setCreatedTime(new Date());
                    draft.setUpdatedTime(new Date());
                    draft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_DRAFT));
                    // ?????????????????????????????????????????? ????????????????????????????????????????????????????????????
                    if (checkMsgPer(rosPartsSaveOrUpdateEnter)) {
                        draft.setPerfectFlag(true);
                    } else {
                        draft.setPerfectFlag(false);
                    }
                    draftList.add(draft);
                default:
                    break;
                case 1:
                    // ??????????????????????????????(??????????????????)
                    if (!checkMsgPer(rosPartsSaveOrUpdateEnter)) {
                        throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
                    }
                    // ??????????????? ????????????????????????????????????
                    Set<String> partsNos = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
                    // ?????????????????????list???????????????????????????????????????  ???????????????????????????
                    if (partsNos.size() != enters.size()) {
                        throw new SesWebRosException(ExceptionCodeEnums.PARTS_NO_REPEAT.getCode(), ExceptionCodeEnums.PARTS_NO_REPEAT.getMessage());
                    }
                    OpeProductionParts parts = new OpeProductionParts();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, parts);
                    parts.setAnnounUserId(enter.getUserId());
                    parts.setCreatedBy(enter.getUserId());
                    parts.setUpdatedBy(enter.getUserId());
                    parts.setCreatedTime(new Date());
                    parts.setUpdatedTime(new Date());
                    parts.setDwg(rosPartsSaveOrUpdateEnter.getDwg());
                    parts.setQcFlag(Boolean.FALSE);
                    parts.setAnnounUserId(enter.getUserId());
                    parts.setOpAnnounUserId(enter.getUserId());
                    parts.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS));
                    partsList.add(parts);
                    break;
            }
        }
        if (CollectionUtils.isNotEmpty(draftList)) {
            opeProductionPartsDraftService.saveOrUpdateBatch(draftList);
        }
        if (CollectionUtils.isNotEmpty(partsList)) {
            opeProductionPartsService.saveOrUpdateBatch(partsList);
        }
        return new GeneralResult();
    }


    /**
     * @return
     * @Author Aleks
     * @Description ????????????????????????
     * @Date 2020/9/25 15:42
     * @Param [rosPartsSaveOrUpdateEnter]
     **/
    public boolean checkMsgPer(RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter) {
        boolean falg = false;
        if (!Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getPartsNo()) && StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getPartsSec()) && StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getPartsType()) && StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getSnClass()) &&
                StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getIdCalss()) && StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getSupplierId()) && StringManaConstant.entityIsNotNull(rosPartsSaveOrUpdateEnter.getProcurementCycle()) && 0 < rosPartsSaveOrUpdateEnter.getProcurementCycle() && !Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getCnName()) &&
                !Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getEnName())) {
            // ?????????????????????????????????  ??????true
            falg = true;
        }
        return falg;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult partsDelete(RosPartsBatchOpEnter enter) {
        opeProductionPartsDraftService.removeByIds(Arrays.asList(enter.getId().split(",")));
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult partsEdit(StringEnter enter) {
        // ??????????????????????????????
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            // ???????????????
            enters = JSONArray.parseArray(enter.getKeyword(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isNotEmpty(enters)) {
            List<Long> ids = enters.stream().map(RosPartsSaveOrUpdateEnter::getId).collect(Collectors.toList());
            QueryWrapper<OpeProductionPartsDraft> qw = new QueryWrapper<>();
            qw.in(OpeProductionPartsDraft.COL_ID, ids);
            List<OpeProductionPartsDraft> updateList = opeProductionPartsDraftService.list(qw);
            if (CollectionUtils.isEmpty(updateList)) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            // ????????????
            List<OpeProductionParts> partsList = new ArrayList<>();
            // ??????????????????
            List<Long> deleteList = new ArrayList<>();
            for (OpeProductionPartsDraft partsDraft : updateList) {
                for (RosPartsSaveOrUpdateEnter partsSaveOrUpdateEnter : enters) {
                    // ???????????????  ??????????????????
                    Integer isAnnoun = partsSaveOrUpdateEnter.getIsAnnoun() == null ? 0 : partsSaveOrUpdateEnter.getIsAnnoun();
                    if (partsDraft.getId().equals(partsSaveOrUpdateEnter.getId()) && isAnnoun.equals(0)) {
                        partsDraft.setPartsNo(partsSaveOrUpdateEnter.getPartsNo());
                        partsDraft.setPartsSec(partsSaveOrUpdateEnter.getPartsSec());
                        partsDraft.setPartsType(partsSaveOrUpdateEnter.getPartsType());
                        partsDraft.setSnClass(partsSaveOrUpdateEnter.getSnClass());
                        partsDraft.setIdCalss(partsSaveOrUpdateEnter.getIdCalss());
                        partsDraft.setPartsIsAssembly(partsSaveOrUpdateEnter.getPartsIsAssembly());
                        partsDraft.setPartsIsForAssembly(partsSaveOrUpdateEnter.getPartsIsForAssembly());
                        partsDraft.setEnName(partsSaveOrUpdateEnter.getEnName());
                        partsDraft.setCnName(partsSaveOrUpdateEnter.getCnName());
                        partsDraft.setFrName(partsSaveOrUpdateEnter.getFrName());
                        partsDraft.setSupplierId(partsSaveOrUpdateEnter.getSupplierId());
                        partsDraft.setProcurementCycle(Objects.isNull(partsSaveOrUpdateEnter.getProcurementCycle()) ? 0 : partsSaveOrUpdateEnter.getProcurementCycle());
                        partsDraft.setUpdatedTime(new Date());
                        partsDraft.setDwg(partsSaveOrUpdateEnter.getDwg());
                        // ????????????????????????
                        RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter = new RosPartsSaveOrUpdateEnter();
                        BeanUtils.copyProperties(partsDraft, rosPartsSaveOrUpdateEnter);
                        if (checkMsgPer(rosPartsSaveOrUpdateEnter)) {
                            partsDraft.setPerfectFlag(true);
                        } else {
                            partsDraft.setPerfectFlag(false);
                        }
                        partsDraft.setUpdatedBy(enter.getUserId());
                        break;
                    } else if (partsDraft.getId().equals(partsSaveOrUpdateEnter.getId()) && isAnnoun.equals(1)) {
                        // ??????????????????????????????
                        if (!checkMsgPer(partsSaveOrUpdateEnter)) {
                            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
                        }
                        // ??????????????? ????????????????????????????????????
                        Set<String> partsNos = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
                        // ?????????????????????list???????????????????????????????????????  ???????????????????????????
                        if (partsNos.size() != enters.size()) {
                            throw new SesWebRosException(ExceptionCodeEnums.PARTS_NO_REPEAT.getCode(), ExceptionCodeEnums.PARTS_NO_REPEAT.getMessage());
                        }
                        OpeProductionParts parts = new OpeProductionParts();
                        BeanUtils.copyProperties(partsSaveOrUpdateEnter, parts);
                        parts.setAnnounUserId(enter.getUserId());
                        parts.setCreatedBy(enter.getUserId());
                        parts.setUpdatedBy(enter.getUserId());
                        parts.setCreatedTime(new Date());
                        parts.setUpdatedTime(new Date());
                        parts.setDwg(partsSaveOrUpdateEnter.getDwg());
                        parts.setAnnounUserId(enter.getUserId());
                        parts.setOpAnnounUserId(enter.getUserId());
                        parts.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS));
                        partsList.add(parts);
                        // ???????????? ???????????????
                        deleteList.add(partsDraft.getId());
                        break;
                    }
                }
            }
            opeProductionPartsDraftService.updateBatchById(updateList);
            if (CollectionUtils.isNotEmpty(partsList)) {
                opeProductionPartsService.saveOrUpdateBatch(partsList);
            }
            if (CollectionUtils.isNotEmpty(deleteList)) {
                opeProductionPartsDraftService.removeByIds(deleteList);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public PageResult<RosPartsListResult> partsList(RosPartsListEnter enter) {
        // ??????????????????tab
        Integer classType = enter.getClassType();
        int totalNum = 0;
        List<RosPartsListResult> resultList = new ArrayList<>();
        switch (classType) {
            case 1:
                // ???????????????
                totalNum = rosProductionPartsDraftServiceMapper.partsDraftTotal(enter);
                if (0 == totalNum) {
                    return PageResult.createZeroRowResult(enter);
                }
                resultList = rosProductionPartsDraftServiceMapper.partsDraftList(enter);
            default:
                break;
            case 2:
                // ???????????????
                totalNum = rosProductionPartsServiceMapper.partsTotal(enter);
                if (0 == totalNum) {
                    return PageResult.createZeroRowResult(enter);
                }
                resultList = rosProductionPartsServiceMapper.partsList(enter);
                //partsQualityTempate(resultList);
                if (CollectionUtils.isNotEmpty(resultList)) {
                    for (RosPartsListResult o : resultList) {
                        // ????????????????????? 0??? 1???
                        Integer qcFlag = o.getQcFlag();
                        qcFlag = qcFlag == null ? 0 : qcFlag;
                        if (qcFlag == 0) {
                            o.setQcTempletePromptIcon(Boolean.TRUE);
                        } else if (qcFlag == 1) {
                            o.setQcTempletePromptIcon(Boolean.FALSE);
                        }
                    }
                }
                break;
        }

        for (RosPartsListResult result : resultList) {
            // classType????????????????????????
            result.setClassType(classType);
        }
        return PageResult.create(enter, totalNum, resultList);
    }


    /*private List<RosPartsListResult> partsQualityTempate(List<RosPartsListResult> resultList) {
        if (CollectionUtils.isNotEmpty(resultList)) {
            QueryWrapper<OpeProductionQualityTempate> queryWrapper = new QueryWrapper<OpeProductionQualityTempate>();
            queryWrapper.in(OpeProductionQualityTempate.COL_PRODUCTION_ID,
                    resultList.stream().map(RosPartsListResult::getId).collect(Collectors.toList()));
            queryWrapper.in(OpeProductionQualityTempate.COL_PRODUCTION_TYPE,
                    Integer.valueOf(BomCommonTypeEnums.PARTS.getValue()),
                    Integer.valueOf(BomCommonTypeEnums.BATTERY.getValue()),
                    Integer.valueOf(BomCommonTypeEnums.ACCESSORY.getValue()));
            List<OpeProductionQualityTempate> opeProductionQualityTempateList =
                    opeProductionQualityTempateService.list(queryWrapper);
            if (CollectionUtils.isEmpty(opeProductionQualityTempateList)) {
                return resultList;
            }
            // ?????? ??????????????? ???????????? ?????????Icon ????????????
            resultList.stream()
                    .filter(item -> opeProductionQualityTempateList.stream()
                            .anyMatch(qc -> (qc.getProductionId().equals(item.getId())
                                    && item.getPartsType().equals(qc.getProductionType()))))
                    .forEach(item -> item.setQcTempletePromptIcon(Boolean.FALSE));
        }
        return resultList;
    }*/

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult partsAnnoun(DraftAnnounEnter enter) {
        // ?????? ?????????????????????????????? ?????????????????????
        String[] draftIds = enter.getId().split(",");
        QueryWrapper<OpeProductionPartsDraft> qw = new QueryWrapper<>();
        qw.in(OpeProductionPartsDraft.COL_ID, draftIds);
        List<OpeProductionPartsDraft> draftList = opeProductionPartsDraftService.list(qw);
        if (CollectionUtils.isEmpty(draftList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(), ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
        }
        // ??????????????????(?????????????????????????????????????????????????????????)
        checkMsgDraft(draftList);
        // ????????????  ???????????????(??????????????????????????????????????????????????????????????????)
        List<OpeProductionParts> partsList = new ArrayList<>();
        for (OpeProductionPartsDraft draft : draftList) {
            OpeProductionParts parts = new OpeProductionParts();
            BeanUtils.copyProperties(draft, parts);
            parts.setCreatedBy(enter.getUserId());
            parts.setCreatedTime(new Date());
            parts.setUpdatedBy(enter.getUserId());
            parts.setUpdatedTime(new Date());
            partsList.add(parts);
        }
        opeProductionPartsDraftService.removeByIds(Arrays.asList(draftIds));
        opeProductionPartsService.saveOrUpdateBatch(partsList);
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????
    private void checkMsgDraft(List<OpeProductionPartsDraft> draftList) {
        // ????????????????????????
        boolean flag = true;
        for (OpeProductionPartsDraft draft : draftList) {
            if (StringManaConstant.entityIsNotNull(draft.getPerfectFlag()) && !draft.getPerfectFlag()) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
        }
        // ???????????????????????????????????????????????????????????????????????????
//        List<String> partsNos = draftList.stream().map(OpeProductionPartsDraft::getPartsNo).collect(Collectors.toList());
//        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
//        qw.in(OpeProductionParts.COL_PARTS_NO,partsNos);
//        List<OpeProductionParts> list = opeProductionPartsService.list(qw);
//        if (CollectionUtils.isNotEmpty(list)){
//            throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_EXIST.getCode(), ExceptionCodeEnums.PARTS_NUMBER_EXIST.getMessage());
//        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public List<OpeProductionPartsDraft> importParts(ImportPartsEnter enter) {
        // ??????????????????
        ExcelImportResult<RosParseExcelData> excelImportResult = importExcelService.setiExcelVerifyHandler(new RosExcelParse()).importOssExcel(enter.getUrl(), RosParseExcelData.class, new ImportParams());
        if (StringManaConstant.entityIsNull(excelImportResult)) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        // ???????????????????????????
        List<RosParseExcelData> successList = excelImportResult.getList();
        if (CollectionUtils.isNotEmpty(excelImportResult.getFailList())) {
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_MSG_NOT_PERFECT.getCode(), ExceptionCodeEnums.PARTS_MSG_NOT_PERFECT.getMessage());
        }
        if (CollectionUtils.isEmpty(successList)) {
            // ???????????????????????????  ???????????????
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        // ????????????????????? ????????????
        return excelDataToDraft(successList, enter);
    }


    public List<OpeProductionPartsDraft> excelDataToDraft(List<RosParseExcelData> dataList, ImportPartsEnter enter) {
        // ???????????????????????????????????????????????????
        // ??????????????????????????? ????????? sec
        List<OpeSupplier> supplierList = opeSupplierService.list();
        List<OpePartsSec> secList = opePartsSecService.list();
        List<OpeProductionPartsDraft> retureList = new ArrayList<>();
        for (RosParseExcelData data : dataList) {
            OpeProductionPartsDraft draft = new OpeProductionPartsDraft();
            draft.setPartsNo(data.getPartsNo());
            draft.setMainDrawing(data.getMainDrawing());
            draft.setCnName(data.getChineseName());
            draft.setEnName(data.getEnglishName());
            draft.setItem(data.getItem());
            draft.setEcnNumber(data.getEcnNumber());
            draft.setDrawingSize(data.getDrawingSize());
            try {
                draft.setWeight(Double.parseDouble(Strings.isNullOrEmpty(data.getWeight()) ? "0" : data.getWeight()));
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.WEIGHT_ILLEGAL.getCode(), ExceptionCodeEnums.WEIGHT_ILLEGAL.getMessage());
            }
            try {
                draft.setPartsQty(NumberUtil.getStrToNum(data.getQuantity()));
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.QUANTITY_ILLEGAL.getCode(), ExceptionCodeEnums.QUANTITY_ILLEGAL.getMessage());
            }
            draft.setRateTyp(data.getRateTyp());
            draft.setSellCalss(data.getSellClass());
            // sec?????????
            draft.setPartsSec(getSecId(data.getSec(), secList));
            // ???????????????
            draft.setSupplierId(getSupplierId(data.getSupplier1(), supplierList));
            draft.setSupplierId2(getSupplierId(data.getSupplier2(), supplierList));
            // ????????????
            draft.setPartsType(getPartsType(data.getType()));
            draft.setTenantId(enter.getTenantId());
//            draft.setDeptId();
            if (!Strings.isNullOrEmpty(draft.getSellCalss())) {
                Integer snClass = null;
                if (draft.getSellCalss().equals("SC")) {
                    snClass = 0;
                } else if (draft.getSellCalss().equals("SSC")) {
                    snClass = 1;
                }
                draft.setSnClass(snClass);
            }
            draft.setIdCalss(0);
            draft.setPartsIsAssembly(0);
            draft.setPartsIsForAssembly(0);
            draft.setPerfectFlag(false);
            draft.setCreatedBy(enter.getUserId());
            draft.setCreatedTime(new Date());
            draft.setUpdatedBy(enter.getUserId());
            draft.setUpdatedTime(new Date());
//            draft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS));
            retureList.add(draft);
        }
        return retureList;
    }


    // ??????secName?????????id
    public Long getSecId(String secName, List<OpePartsSec> secList) {
        if (CollectionUtils.isEmpty(secList)) {
            return null;
        }
        Long secId = 0L;
        for (OpePartsSec sec : secList) {
            if (sec.getName().equals(secName)) {
                secId = sec.getId();
                break;
            }
        }
        return secId;
    }


    // ???????????????name?????????id
    public Long getSupplierId(String name, List<OpeSupplier> supplierList) {
        if (CollectionUtils.isEmpty(supplierList)) {
            return null;
        }
        Long supplierId = 0L;
        for (OpeSupplier supplier : supplierList) {
            if (supplier.getSupplierName().equals(name)) {
                supplierId = supplier.getId();
                break;
            }
        }
        return supplierId;
    }


    public Integer getPartsType(String partsTypeName) {
        if (Strings.isNullOrEmpty(partsTypeName)) {
            return 1;
        }
        Integer type = 1;
        if ("Parts".equals(partsTypeName)) {
            type = 1;
        } else if ("Accessory".equals(partsTypeName)) {
            type = 2;
        } else if ("Battery".equals(partsTypeName)) {
            type = 3;
        } else if ("Scooter".equals(partsTypeName)) {
            type = 4;
        } else if ("Combination".equals(partsTypeName)) {
            type = 5;
        } else if ("Ecu_Meter".equals(partsTypeName)) {
            type = 6;
        }
        return type;
    }


    @Override
    public List<StaffDataResult> announUser(Long tenantId) {
        List<StaffDataResult> resultList = staffServiceMapper.announUser(tenantId);
        return resultList;
    }

    @Override
    public BooleanResult checkAnnounUserSafeCode(RosCheckAnnounSafeCodeEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getPrincipal());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        boolean flag = true;
        if (StringManaConstant.entityIsNull(staff.getIfSafeCode()) || 1 != staff.getIfSafeCode()) {
            // ??????????????? ??????false
            flag = false;
        } else {
            // ?????????????????????  ???????????????????????????  ????????????RSA??????????????????????????????  ?????????????????????????????? 2020 09 24??????
            String oldSafeCode = "";
            String newSafeCode = "";
            try {
                oldSafeCode = RsaUtils.decrypt(SesStringUtils.stringTrim(staff.getSafeCode()), privatekey);
                newSafeCode = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getSafeCode()), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            if (!oldSafeCode.equals(newSafeCode)) {
                flag = false;
            }
        }
        // ??????????????????????????????
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        jedisCluster.set(key, String.valueOf(flag));
        jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
        BooleanResult result = new BooleanResult();
        result.setSuccess(flag);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    @Override
    public GeneralResult partsCopy(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description ???????????? ???????????????
     * @Date 2020/9/24 17:09
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult partsDisable(RosPartsBatchOpEnter enter) {
        String[] ids = enter.getId().split(",");
        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
        qw.in(OpeProductionParts.COL_ID, ids);
        List<OpeProductionParts> list = opeProductionPartsService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            //??????????????????????????????
            /*List<OpeProductionPartsRelation> productionPartsRelationList =
                    opeProductionPartsRelationService.list(new LambdaQueryWrapper<OpeProductionPartsRelation>().in(OpeProductionPartsRelation::getPartsId, ids));
            if (CollectionUtils.isNotEmpty(productionPartsRelationList)){

                Map<Integer,List<Long>> productMap = new HashMap<>();
                productionPartsRelationList.forEach(item->{
                    if (productMap.containsKey(item.getProductionType())){
                        List<Long> productIds = productMap.get(item.getProductionType());
                        productMap.put(item.getProductionType(),productIds);
                    }else {
                        List<Long> productIds = new ArrayList<>();
                        productIds.add(item.getProductionId());
                        productMap.put(item.getProductionType(),productIds);
                    }
                });

                if (CollectionUtil.isNotEmpty(productMap)) {
                    if (productMap.containsKey(ProductTypeEnums.COMBINATION.getValue())) {
                        List<OpeProductionCombinBom> productionCombinBomList = opeProductionCombinBomService.list(new LambdaQueryWrapper<OpeProductionCombinBom>()
                                .in(OpeProductionCombinBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())
                                .in(OpeProductionCombinBom::getId, productMap.get(ProductTypeEnums.COMBINATION.getValue())));
                        if (CollectionUtils.isEmpty(productionCombinBomList)) {
                            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getMessage());
                        }
                    }
                    if (productMap.containsKey(ProductTypeEnums.COMBINATION.getValue())) {
                        List<OpeProductionScooterBom> scooterList = opeProductionScooterBomService.list(new LambdaQueryWrapper<OpeProductionScooterBom>()
                                .in(OpeProductionScooterBom::getBomStatus, ProductionBomStatusEnums.ACTIVE.getValue(), ProductionBomStatusEnums.TO_BE_ACTIVE.getValue())
                                .in(OpeProductionScooterBom::getId, productMap.get(ProductTypeEnums.SCOOTER.getValue())));
                        if (CollectionUtils.isEmpty(scooterList)) {
                            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getCode(), ExceptionCodeEnums.PART_IS_BIND_PRODUCT.getMessage());
                        }
                    }
                }
            }*/
            for (OpeProductionParts parts : list) {
                if (parts.getDisable().equals(1)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                parts.setDisable(1);
            }
            opeProductionPartsService.updateBatchById(list);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 ???????????????????????????
        Integer num1 = 0;
        Integer num2 = 0;
        map.put("1", opeProductionPartsService.count());
        map.put("2", opeProductionPartsDraftService.count());
        return map;
    }

    @Override
    public GeneralResult partsExport(String id, HttpServletResponse response) {
        List<RosPartsExportEnter> exportList = new ArrayList<>();
        if (Strings.isNullOrEmpty(id)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        List<Long> ids = new ArrayList<>();
        for (String s : id.split(",")) {
            ids.add(Long.parseLong(s));
        }
        // ????????????????????????
        List<RosPartsListResult> lists = rosProductionPartsServiceMapper.partsExport(ids);
        if (CollectionUtils.isNotEmpty(lists)) {
            buildExportList(exportList, lists);
            try {
                // ??????????????????????????????
                response.setHeader("content-Type", "application/vnd.ms-excel");
                // ???????????????????????????
                response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
                // =========easypoi??????
                ExportParams exportParams = new ExportParams();
                exportParams.setSheetName("Parts");
                exportParams.setCreateHeadRows(true);
                exportParams.setHeaderColor(Short.valueOf("1"));
                // exportParams.setDataHanlder(null);//?????????????????????????????????handler?????????????????????
                Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RosPartsExportEnter.class, exportList);
                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                System.out.println("+++++++++++++++++++");
            }
        }
        return new GeneralResult();
    }


    private void buildExportList(List<RosPartsExportEnter> exportList, List<RosPartsListResult> lists) {
        for (RosPartsListResult list : lists) {
            RosPartsExportEnter exportEnter = new RosPartsExportEnter();
            BeanUtils.copyProperties(list, exportEnter);
//            exportEnter.setAssembly(list.getPartsIsAssembly()==null?"No":(list.getPartsIsAssembly()==1?"Yes":"No"));
//            exportEnter.setForAssembly(list.getPartsIsForAssembly()==null?"No":(list.getPartsIsForAssembly()==1?"Yes":"No"));
            exportEnter.setPartsSecName(list.getPartsSecName());
            String type = "--";
            if (StringManaConstant.entityIsNotNull(list.getPartsType())) {
                if (1 == list.getPartsType()) {
                    type = "Parts";
                } else if (2 == list.getPartsType()) {
                    type = "Accessory";
                } else if (3 == list.getPartsType()) {
                    type = "Battery";
                }
            }
            exportEnter.setType(type);
            exportEnter.setSnClass(list.getSnClass() == null ? "SC" : (list.getSnClass() == 0 ? "SC" : "SSC"));
            exportEnter.setCnName(list.getCnName());
            exportEnter.setEnName(list.getEnName());
//            exportEnter.setFrName(list.getFrName());
            exportEnter.setSupplierName(list.getSupplierName());
            exportEnter.setProcurementCycle(list.getProcurementCycle() == null ? "0" : list.getProcurementCycle().toString());
            exportList.add(exportEnter);
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description ?????????????????? ???????????????????????????
     * @Date 2020/9/25 16:38
     * @Param [enter]
     **/
    @Override
    public List<RosRepeatResult> saveAnnounCheck(StringEnter enter) {
        List<RosRepeatResult> list = new ArrayList<>();
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        Set<String> set = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
//        if(set.size() == enters.size()){
//            return list;
//        }
        for (RosPartsSaveOrUpdateEnter saveOrUpdateEnter : enters) {
            if (Strings.isNullOrEmpty(saveOrUpdateEnter.getPartsNo()) || StringManaConstant.entityIsNull(saveOrUpdateEnter.getPartsSec()) || StringManaConstant.entityIsNull(saveOrUpdateEnter.getPartsType()) || StringManaConstant.entityIsNull(saveOrUpdateEnter.getSnClass()) ||
                    StringManaConstant.entityIsNull(saveOrUpdateEnter.getIdCalss()) && StringManaConstant.entityIsNull(saveOrUpdateEnter.getSupplierId()) || StringManaConstant.entityIsNull(saveOrUpdateEnter.getProcurementCycle()) || 0 == saveOrUpdateEnter.getProcurementCycle() || Strings.isNullOrEmpty(saveOrUpdateEnter.getCnName()) ||
                    Strings.isNullOrEmpty(saveOrUpdateEnter.getEnName())) {
                // ?????????????????????????????????  ?????????????????????
                throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
            }
        }
        // ??????????????? ?????????????????? ?????????????????????????????????
        Map<String, List<RosPartsSaveOrUpdateEnter>> map = enters.stream().collect(Collectors.groupingBy(RosPartsSaveOrUpdateEnter::getPartsNo));
        for (String key : map.keySet()) {
            if (1 < map.get(key).size()) {
                // ?????????????????????  ????????????????????????list????????????1 ????????????????????????????????? ????????????
                List<RosPartsSaveOrUpdateEnter> repeatList = map.get(key);
                for (RosPartsSaveOrUpdateEnter repeat : repeatList) {
                    RosRepeatResult result = new RosRepeatResult();
                    result.setPartsNo(repeat.getPartsNo());
                    result.setCnName(repeat.getCnName());
                    result.setEnName(repeat.getEnName());
                    result.setFrName(repeat.getFrName());
                    list.add(result);
                }
            }
        }
//        // 2020 9 27 ??????  ????????????????????????????????????????????? ??????????????????
//        list.addAll(findRepeat(enters));
        return list;
    }


    @Override
    public List<RosRepeatResult> saveAnnounCheck2(StringEnter enter) {
        List<RosRepeatResult> list = new ArrayList<>();
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getKeyword(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        list.addAll(findRepeat(enters));
        return list;
    }


    @Override
    public List<RosRepeatResult> partsDisableCheck(RosPartsBatchOpEnter enter) {
        List<RosRepeatResult> resultList = new ArrayList<>();
        if (Strings.isNullOrEmpty(enter.getId())) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
        qw.in(OpeProductionParts.COL_ID, enter.getId().split(","));
        List<OpeProductionParts> parts = opeProductionPartsService.list(qw);
        if (CollectionUtils.isEmpty(parts)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCTION_PART_IS_NOT_EXIST.getMessage());
        }
        List<Long> partsIds = parts.stream().map(OpeProductionParts::getId).collect(Collectors.toList());
        QueryWrapper<OpeProductionPartsRelation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.in(OpeProductionPartsRelation.COL_PARTS_ID, partsIds);
        // ????????????id????????????????????????
        List<OpeProductionPartsRelation> relationList = opeProductionPartsRelationService.list(relationQueryWrapper);
        if (CollectionUtils.isNotEmpty(relationList)) {
            // ??????????????????
            for (OpeProductionParts part : parts) {
                for (OpeProductionPartsRelation relation : relationList) {
                    if (part.getId().equals(relation.getPartsId())) {
                        RosRepeatResult result = new RosRepeatResult();
                        result.setPartsNo(part.getPartsNo());
                        result.setCnName(part.getCnName());
                        result.setEnName(part.getEnName());
                        result.setFrName(part.getFrName());
                        resultList.add(result);
                        break;
                    }
                }
            }
        }
        return resultList;
    }


    /*
     * @Author Aleks
     * @Description  ?????????????????????????????? ??????????????????????????????
     * @Date  2020/9/27 14:09
     * @Param [enters]
     * @return
     **/
    public List<RosRepeatResult> findRepeat(List<RosPartsSaveOrUpdateEnter> enters) {
        List<RosRepeatResult> list = new ArrayList<>();
        Set<String> set = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
        qw.in(OpeProductionParts.COL_PARTS_NO, set);
        List<OpeProductionParts> parts = opeProductionPartsService.list(qw);
        if (CollectionUtils.isNotEmpty(parts)) {
            // ????????????????????????  ??????????????????????????????
            for (RosPartsSaveOrUpdateEnter enter : enters) {
                for (OpeProductionParts part : parts) {
                    if (enter.getPartsNo().equals(part.getPartsNo())) {
                        if (Strings.isNullOrEmpty(enter.getPartsNo())) {
                            throw new SesWebRosException(ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getCode(), ExceptionCodeEnums.BOM_MSG_IS_NOT_COMPLETE.getMessage());
                        }
                        RosRepeatResult repeatResult = new RosRepeatResult();
                        repeatResult.setPartsNo(enter.getPartsNo());
                        repeatResult.setCnName(enter.getCnName());
                        repeatResult.setEnName(enter.getEnName());
                        repeatResult.setFrName(enter.getFrName());
                        list.add(repeatResult);
                        break;
                    }
                }
            }
        }
        return list;
    }

}
