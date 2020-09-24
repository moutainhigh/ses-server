package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsDraftServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsDraftService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
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

    @Reference
    private IdAppService idAppService;

    @Autowired
    private ExcelService excelService;

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

    @Override
    @Transactional
    public GeneralResult partsSave(StringEnter enter) {
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 新增的时候可能会进草稿，也可能会进部件，先定义两个集合
        List<OpeProductionParts> partsList = new ArrayList<>();
        List<OpeProductionPartsDraft> draftList = new ArrayList<>();

        for (RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter : enters) {
            // 先判断是保存草稿还是发布
            // 如果点的是发布的话 需要校验信息是否完善、不完善的不能发
            // TODO 等产品给完善的逻辑
            Integer isAnnoun = rosPartsSaveOrUpdateEnter.getIsAnnoun();
            switch (isAnnoun) {
                case 0:
                    // 不发布 保存到草稿
                    OpeProductionPartsDraft draft = new OpeProductionPartsDraft();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, draft);
                    draft.setPartsSec(rosPartsSaveOrUpdateEnter.getPartsSecId());
                    draft.setCreatedBy(enter.getUserId());
                    draft.setUpdatedBy(enter.getUserId());
                    draft.setCreatedTime(new Date());
                    draft.setUpdatedTime(new Date());
                    draft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_DRAFT));
                    draftList.add(draft);
                default:
                    break;
                case 1:
                    // 先要判断信息是否完善(等产品给逻辑)
                    if (false) {
                        throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
                    }
                    OpeProductionParts parts = new OpeProductionParts();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, parts);
                    parts.setAnnounUserId(enter.getUserId());
                    parts.setCreatedBy(enter.getUserId());
                    parts.setUpdatedBy(enter.getUserId());
                    parts.setCreatedTime(new Date());
                    parts.setUpdatedTime(new Date());
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

    @Override
    public GeneralResult partsDelete(RosPartsBatchOpEnter enter) {
        opeProductionPartsDraftService.removeByIds(Arrays.asList(enter.getId().split(",")));
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult partsEdit(StringEnter enter) {
        // 只有草稿才有编辑操作
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            // 先解析出来
            enters = JSONArray.parseArray(enter.getSt(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if(CollectionUtils.isNotEmpty(enters)){
            List<Long> ids = enters.stream().map(RosPartsSaveOrUpdateEnter::getId).collect(Collectors.toList());
            QueryWrapper<OpeProductionPartsDraft> qw = new QueryWrapper<>();
            qw.in(OpeProductionPartsDraft.COL_ID,ids);
            List<OpeProductionPartsDraft> updateList = opeProductionPartsDraftService.list(qw);
            if(CollectionUtils.isEmpty(updateList)){
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            for (OpeProductionPartsDraft partsDraft : updateList) {
                for (RosPartsSaveOrUpdateEnter partsSaveOrUpdateEnter : enters) {
                     if (partsDraft.getId().equals(partsSaveOrUpdateEnter.getId())){
                         partsDraft.setPartsNo(partsSaveOrUpdateEnter.getPartsNo());
                         partsDraft.setPartsSec(partsSaveOrUpdateEnter.getPartsSecId());
                         partsDraft.setPartsType(partsSaveOrUpdateEnter.getPartsType());
                         partsDraft.setSnClass(partsSaveOrUpdateEnter.getSnClass());
                         partsDraft.setIdCalss(partsSaveOrUpdateEnter.getIdCalss());
                         partsDraft.setPartsIsAssembly(partsSaveOrUpdateEnter.getPartsIsAssembly());
                         partsDraft.setPartsIsForAssembly(partsSaveOrUpdateEnter.getPartsIsForAssembly());
                         partsDraft.setEnName(partsSaveOrUpdateEnter.getEnName());
                         partsDraft.setCnName(partsSaveOrUpdateEnter.getCnName());
                         partsDraft.setFrName(partsSaveOrUpdateEnter.getFrName());
                         partsDraft.setSupplierId(partsSaveOrUpdateEnter.getSupplierId());
                         partsDraft.setProcurementCycle(partsSaveOrUpdateEnter.getProcurementCycle());
                         partsDraft.setUpdatedTime(new Date());
                         // todo 判断信息是否完善
                         if(false){
                             partsDraft.setPerfectFlag(true);
                         }
                         partsDraft.setUpdatedBy(enter.getUserId());
                     }
                }
            }
            opeProductionPartsDraftService.updateBatchById(updateList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public PageResult<RosPartsListResult> partsList(RosPartsListEnter enter) {

        // 先判断是哪个tab
        Integer classType = enter.getClassType();
        int totalNum = 0;
        List<RosPartsListResult> resultList = new ArrayList<>();
        switch (classType) {
            case 1:
                // 草稿的列表
                totalNum = rosProductionPartsDraftServiceMapper.partsDraftTotal(enter);
                if (totalNum == 0) {
                    return PageResult.createZeroRowResult(enter);
                }
                resultList = rosProductionPartsDraftServiceMapper.partsDraftList(enter);
            default:
                break;
            case 2:
                // 部件的列表
                totalNum = rosProductionPartsServiceMapper.partsTotal(enter);
                if (totalNum == 0) {
                    return PageResult.createZeroRowResult(enter);
                }
                resultList = rosProductionPartsServiceMapper.partsList(enter);

                break;
        }
        for (RosPartsListResult result : resultList) {
            // classType也返回到列表上去
            result.setClassType(classType);
        }
        return PageResult.create(enter, totalNum, resultList);
    }

    @Override
    @Transactional
    public GeneralResult partsAnnoun(DraftAnnounEnter enter) {
        // 发布 能进行发布的只有草稿 且支持批量发布
        String[] draftIds = enter.getId().split(",");
        QueryWrapper<OpeProductionPartsDraft> qw = new QueryWrapper<>();
        qw.in(OpeProductionPartsDraft.COL_ID, draftIds);
        List<OpeProductionPartsDraft> draftList = opeProductionPartsDraftService.list(qw);
        if (CollectionUtils.isEmpty(draftList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(), ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
        }
        // 校验草稿信息(完善度，发布到部件中的部件号有没有重复)
        checkMsgDraft(draftList);
        // 通过校验  可以发布了(把草稿里面的数据干掉，同时在部件里面添加数据)
        List<OpeProductionParts> partsList = new ArrayList<>();
        for (OpeProductionPartsDraft draft : draftList) {
            OpeProductionParts parts = new OpeProductionParts();
            BeanUtils.copyProperties(draft,parts);
            partsList.add(parts);
        }
        opeProductionPartsDraftService.removeByIds(Arrays.asList(draftIds));
        opeProductionPartsService.updateBatchById(partsList);
        return new GeneralResult(enter.getRequestId());
    }


    // 校验草稿信息的完善度
    private void checkMsgDraft(List<OpeProductionPartsDraft> draftList) {
        // 先校验信息完善度
        boolean flag = true;
        for (OpeProductionPartsDraft draft : draftList) {
            if (draft.getPerfectFlag() != null && !draft.getPerfectFlag()) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
        }
        // 再校验当前要发布的草稿的部件号是否在部件中已经存在
        List<String> partsNos = draftList.stream().map(OpeProductionPartsDraft::getPartsNo).collect(Collectors.toList());
        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
        qw.in(OpeProductionParts.COL_PARTS_NO,partsNos);
        List<OpeProductionParts> list = opeProductionPartsService.list(qw);
        if (CollectionUtils.isNotEmpty(list)){
            throw new SesWebRosException(ExceptionCodeEnums.PARTS_NUMBER_EXIST.getCode(), ExceptionCodeEnums.PARTS_NUMBER_EXIST.getMessage());
        }
    }

    @Override
    public ImportExcelPartsResult importParts(ImportPartsEnter enter) {
        ImportExcelPartsResult result = new ImportExcelPartsResult();
        // 逻辑需要调整
        return excelService.readExcelDataByParts(enter);
    }

    @Override
    public List<StaffDataResult> announUser(Long tenantId) {
        List<StaffDataResult> resultList = staffServiceMapper.announUser(tenantId);
        return resultList;
    }

    @Override
    public Boolean checkAnnounUserSafeCode(RosCheckAnnounSafeCodeEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getPrincipal());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        boolean flag = true;
        if (staff.getIfSafeCode() == null || staff.getIfSafeCode() != 1) {
            // 没有安全码 返回false
            flag = false;
        } else {
            // 前端传的是密文  数据库存的也是密文  因为每次RSA加密的结果是不一样的  所以需要先解密再比较 2020 09 24追加
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
        return flag;
    }

    @Override
    public GeneralResult partsCopy(IdEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description 禁用操作 只针对部件
     * @Date 2020/9/24 17:09
     * @Param [enter]
     **/
    @Override
    public GeneralResult partsDisable(RosPartsBatchOpEnter enter) {
        String[] ids = enter.getId().split(",");
        QueryWrapper<OpeProductionParts> qw = new QueryWrapper<>();
        qw.in(OpeProductionParts.COL_ID, ids);
        List<OpeProductionParts> list = opeProductionPartsService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeProductionParts parts : list) {
                parts.setDisable(1);
            }
            opeProductionPartsService.updateBatchById(list);
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 1 2 分别对应草稿、部件
        Integer num1 = 0;
        Integer num2 = 0;
        map.put("1", opeProductionPartsService.count());
        map.put("2", opeProductionPartsDraftService.count());
        return map;
    }

    @Override
    public GeneralResult partsExport(String id, HttpServletResponse response) {
        return new GeneralResult();
    }
}
