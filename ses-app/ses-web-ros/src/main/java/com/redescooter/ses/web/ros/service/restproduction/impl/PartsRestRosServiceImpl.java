package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Transactional
    public GeneralResult partsAnnoun(DraftAnnounEnter enter) {
        // 发布 能进行发布的只有草稿 且支持批量发布
        String[] draftIds = enter.getId().split(",");
        QueryWrapper<OpeProductionPartsDraft> qw = new QueryWrapper<>();
        qw.in(OpeProductionPartsDraft.COL_ID,draftIds);
        List<OpeProductionPartsDraft> draftList = opeProductionPartsDraftService.list(qw);
        if (CollectionUtils.isEmpty(draftList)){
            throw new SesWebRosException(ExceptionCodeEnums.DRAFT_NOT_EXIST.getCode(), ExceptionCodeEnums.DRAFT_NOT_EXIST.getMessage());
        }
        // 校验草稿信息的完善度
        checkMsgPerf(draftList);
        return new GeneralResult();
    }


    // 校验草稿信息的完善度
    private void checkMsgPerf(List<OpeProductionPartsDraft> draftList) {
        boolean flag = true;
        for (OpeProductionPartsDraft draft : draftList) {
            if (draft.getPerfectFlag() != null && !draft.getPerfectFlag()){
                flag = false;
                break;
            }
        }
        if (!flag){
            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
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
        if(staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        boolean flag = true;
        if(staff.getIfSafeCode() == null || staff.getIfSafeCode() != 1){
            // 没有安全码 返回false
            flag = false;
        }else if(!staff.getSafeCode().equals(enter.getSafeCode())){
            // 安全码不一样 返回false
            flag = false;
        }

        return flag;
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
        Map<String, Integer> map = new HashMap<>();
        // 1 2 分别对应草稿、部件
        Integer num1 = 0;
        Integer num2 = 0;
        map.put("1", num1);
        map.put("2", num2);
        return map;
    }

    @Override
    public GeneralResult partsExport(String id, HttpServletResponse response) {
        return new GeneralResult();
    }
}
