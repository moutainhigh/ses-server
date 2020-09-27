package com.redescooter.ses.web.ros.service.restproduction.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.app.common.service.excel.ImportExcelService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsDraftServiceMapper;
import com.redescooter.ses.web.ros.dao.restproduction.RosProductionPartsServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.excel.ExcelService;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.verifyhandler.RosExcelParse;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

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

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private OpeSupplierService opeSupplierService;

    @Autowired
    private OpePartsSecService opePartsSecService;



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
            // 先检验部件编号是否为空
            if(Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getPartsNo())){
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            // 先判断是保存草稿还是发布
            // 如果点的是发布的话 需要校验信息是否完善、不完善的不能发
            Integer isAnnoun = rosPartsSaveOrUpdateEnter.getIsAnnoun();
            switch (isAnnoun) {
                case 0:
                    // 不发布 保存到草稿
                    OpeProductionPartsDraft draft = new OpeProductionPartsDraft();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, draft);
                    draft.setPartsSec(rosPartsSaveOrUpdateEnter.getPartsSec());
                    draft.setDwg(rosPartsSaveOrUpdateEnter.getDwgUrl());
                    draft.setCreatedBy(enter.getUserId());
                    draft.setUpdatedBy(enter.getUserId());
                    draft.setCreatedTime(new Date());
                    draft.setUpdatedTime(new Date());
                    draft.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PARTS_DRAFT));
                    // 判断是否信息完善（产品说的是 全部页面上全部字段都有值，才叫信息完善）
                    if(checkMsgPer(rosPartsSaveOrUpdateEnter)){
                        draft.setPerfectFlag(true);
                    }else {
                        draft.setPerfectFlag(false);
                    }
                    draftList.add(draft);
                default:
                    break;
                case 1:
                    // 先要判断信息是否完善(等产品给逻辑)
                    if (!checkMsgPer(rosPartsSaveOrUpdateEnter)) {
                        throw new SesWebRosException(ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getCode(), ExceptionCodeEnums.PLEASE_COMPLETE_MSG.getMessage());
                    }
                    // 如果是发布 判断这次的部件号是否重复
                    Set<String> partsNos = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
                    // 如果部件编号的list长度不等于传进来的数组长度  说明部件号有重复的
                    if(partsNos.size() != enters.size()){
                        throw new SesWebRosException(ExceptionCodeEnums.PARTS_NO_REPEAT.getCode(), ExceptionCodeEnums.PARTS_NO_REPEAT.getMessage());
                    }
                    OpeProductionParts parts = new OpeProductionParts();
                    BeanUtils.copyProperties(rosPartsSaveOrUpdateEnter, parts);
                    parts.setAnnounUserId(enter.getUserId());
                    parts.setCreatedBy(enter.getUserId());
                    parts.setUpdatedBy(enter.getUserId());
                    parts.setCreatedTime(new Date());
                    parts.setUpdatedTime(new Date());
                    parts.setDwg(rosPartsSaveOrUpdateEnter.getDwgUrl());
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
     * @Author Aleks
     * @Description  校验信息是否完善
     * @Date  2020/9/25 15:42
     * @Param [rosPartsSaveOrUpdateEnter]
     * @return
     **/
    public boolean checkMsgPer(RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter){
        boolean falg = false;
        if(!Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getPartsNo()) && rosPartsSaveOrUpdateEnter.getPartsSec() != null && rosPartsSaveOrUpdateEnter.getPartsType() != null && rosPartsSaveOrUpdateEnter.getSnClass() != null &&
                rosPartsSaveOrUpdateEnter.getIdCalss() != null && rosPartsSaveOrUpdateEnter.getSupplierId() != null && rosPartsSaveOrUpdateEnter.getProcurementCycle() != null && !Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getCnName()) &&
                !Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getEnName()) && !Strings.isNullOrEmpty(rosPartsSaveOrUpdateEnter.getFrName())){
            // 上面这些都不为空的时候  才是true
            falg = true;
        }
        return falg;
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
                         partsDraft.setProcurementCycle(partsSaveOrUpdateEnter.getProcurementCycle());
                         partsDraft.setUpdatedTime(new Date());
                         // 判断信息是否完善
                         RosPartsSaveOrUpdateEnter rosPartsSaveOrUpdateEnter = new RosPartsSaveOrUpdateEnter();
                         BeanUtils.copyProperties(partsDraft,rosPartsSaveOrUpdateEnter);
                         if(checkMsgPer(rosPartsSaveOrUpdateEnter)){
                             partsDraft.setPerfectFlag(true);
                         }else {
                             partsDraft.setPerfectFlag(false);
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
        opeProductionPartsService.saveOrUpdateBatch(partsList);
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
    @Transactional
    public List<OpeProductionPartsDraft> importParts(ImportPartsEnter enter) {
        // 逻辑需要调整
        ExcelImportResult<RosParseExcelData> excelImportResult = importExcelService.setiExcelVerifyHandler(new RosExcelParse()).importOssExcel(enter.getUrl(), RosParseExcelData.class, new ImportParams());
        if (excelImportResult == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        // 拿到需要导入的数据
        List<RosParseExcelData> successList = excelImportResult.getList();
        if(CollectionUtils.isEmpty(successList)){
            // 如果没有成功的数据  直接抛异常
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        // 拿到成功的数据 直接返回
        return excelDataToDraft(successList,enter);
    }


    public List<OpeProductionPartsDraft>  excelDataToDraft(List<RosParseExcelData> dataList,ImportPartsEnter enter){
        // 部分字段需要转换一下才能保存到草稿
        // 找到需要转换的东西 供应商 sec
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
            draft.setWeight(Double.parseDouble(data.getWeight()));
            draft.setPartsQty(data.getQuantity()==null?0:Integer.parseInt(data.getQuantity()));
            draft.setRateTyp(data.getRateTyp());
            draft.setSellCalss(data.getSellClass());
            // sec转化的
            draft.setPartsSec(getSecId(data.getSec(),secList));
            // 供应商转化
            draft.setSupplierId(getSupplierId(data.getSupplier1(),supplierList));
            draft.setSupplierId2(getSupplierId(data.getSupplier2(),supplierList));
            // 类型转化
            draft.setPartsType(getPartsType(data.getType()));
            draft.setTenantId(enter.getTenantId());
//            draft.setDeptId();
            draft.setSnClass(0);
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


    // 通过secName匹配到id
    public Long getSecId(String secName,List<OpePartsSec> secList){
        if(CollectionUtils.isEmpty(secList)){
            return null;
        }
        Long secId = 0L;
        for (OpePartsSec sec : secList) {
            if (sec.getName().equals(secName)){
                secId = sec.getId();
                break;
            }
        }
        return secId;
    }


    // 通过供应商name匹配到id
    public Long getSupplierId(String name,List<OpeSupplier> supplierList){
        if (CollectionUtils.isEmpty(supplierList)){
            return null;
        }
        Long supplierId = 0L;
        for (OpeSupplier supplier : supplierList) {
            if(supplier.getSupplierName().equals(name)){
                supplierId = supplier.getId();
                break;
            }
        }
        return supplierId;
    }


    public Integer getPartsType(String partsTypeName){
        if (Strings.isNullOrEmpty(partsTypeName)){
            return 1;
        }
        Integer type = 1;
        if("Parts".equals(partsTypeName)){
            type = 1;
        }else if("Accessory".equals(partsTypeName)){
            type = 2;
        }else if("Battery".equals(partsTypeName)){
            type = 3;
        }else if("Scooter".equals(partsTypeName)){
            type = 4;
        }else if("Combination".equals(partsTypeName)){
            type = 5;
        }
        return type;
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
        // 把校验结果放在缓存里
        String key = JedisConstant.CHECK_SAFE_CODE_RESULT + enter.getRequestId();
        jedisCluster.set(key, String.valueOf(flag));
        jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
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
        List<RosPartsExportEnter> exportList = new ArrayList<>();
        if(Strings.isNullOrEmpty(id)){
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        List<Long> ids = new ArrayList<>();
        for (String s : id.split(",")) {
            ids.add(Long.parseLong(s));
        }
        // 查询出对应的数据
        List<RosPartsListResult>  lists = rosProductionPartsServiceMapper.partsExport(ids);
        if(CollectionUtils.isNotEmpty(lists)){
            buildExportList(exportList, lists);
            try {
                // 设置响应输出的头类型
                response.setHeader("content-Type", "application/vnd.ms-excel");
                // 下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
                // =========easypoi部分
                ExportParams exportParams = new ExportParams();
                exportParams.setSheetName("Parts");
               exportParams.setCreateHeadRows(true);
                exportParams.setHeaderColor(Short.valueOf("1"));
                // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
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
            BeanUtils.copyProperties(list,exportEnter);
//            exportEnter.setAssembly(list.getPartsIsAssembly()==null?"No":(list.getPartsIsAssembly()==1?"Yes":"No"));
//            exportEnter.setForAssembly(list.getPartsIsForAssembly()==null?"No":(list.getPartsIsForAssembly()==1?"Yes":"No"));
            exportEnter.setPartsSecName(list.getPartsSecName());
            String type = "--";
            if(list.getPartsType() != null){
                if(list.getPartsType() == 1){
                    type = "Parts";
                }else if(list.getPartsType() == 2){
                    type = "Accessory";
                }else if(list.getPartsType() == 3){
                    type = "Battery";
                }
            }
            exportEnter.setType(type);
            exportEnter.setSnClass(list.getSnClass()==null?"SC":(list.getSnClass()==1?"SC":"SSC"));
            exportEnter.setCnName(list.getCnName());
            exportEnter.setEnName(list.getEnName());
//            exportEnter.setFrName(list.getFrName());
            exportEnter.setSupplierName(list.getSupplierName());
            exportEnter.setProcurementCycle(list.getProcurementCycle() == null?"0":list.getProcurementCycle().toString());
            exportList.add(exportEnter);
        }
    }



    /**
     * @Author Aleks
     * @Description  保存发布校验 有没有重复的部件号
     * @Date  2020/9/25 16:38
     * @Param [enter]
     * @return
     **/
    @Override
    public List<RosRepeatResult> saveAnnounCheck(StringEnter enter) {
        List<RosRepeatResult> list = new ArrayList<>();
        List<RosPartsSaveOrUpdateEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), RosPartsSaveOrUpdateEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        Set<String> set = enters.stream().map(RosPartsSaveOrUpdateEnter::getPartsNo).collect(Collectors.toSet());
        if(set.size() == enters.size()){
            return list;
        }
        // 进行到这里 说明有重复的 需要把重复的数据找出来
        Map<String, List<RosPartsSaveOrUpdateEnter>> map = enters.stream().collect(Collectors.groupingBy(RosPartsSaveOrUpdateEnter::getPartsNo));
        for (String key : map.keySet()) {
            if(map.get(key).size() > 1){
                // 根据部件号分组  一个部件号对应的list长度大于1 说明这个部件号是重复的 要做处理
                List<RosPartsSaveOrUpdateEnter> repeatList = map.get(key);
                for (RosPartsSaveOrUpdateEnter repeat : repeatList) {
                    RosRepeatResult  result = new RosRepeatResult();
                    result.setPartsNo(repeat.getPartsNo());
                    result.setCnName(repeat.getCnName());
                    result.setEnName(repeat.getEnName());
                    result.setFrName(repeat.getFrName());
                    list.add(result);
                }
            }
        }
        return list;
    }
}
