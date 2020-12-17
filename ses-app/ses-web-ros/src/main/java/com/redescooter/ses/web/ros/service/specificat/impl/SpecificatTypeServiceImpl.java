package com.redescooter.ses.web.ros.service.specificat.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.specificat.SpecificatDefGroupMapper;
import com.redescooter.ses.web.ros.dao.specificat.SpecificatDefMapper;
import com.redescooter.ses.web.ros.dao.specificat.SpecificatGroupServiceMapper;
import com.redescooter.ses.web.ros.dao.specificat.SpecificatTypeServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatDefService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatTypeService;
import com.redescooter.ses.web.ros.vo.specificat.*;
import com.redescooter.ses.web.ros.vo.specificat.dto.InsertSpecificTypeParamDTO;
import com.redescooter.ses.web.ros.vo.specificat.dto.SpecificGroupDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassNameSpecificatTypeServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:46
 * @Version V1.0
 **/
@Slf4j
@Service
public class SpecificatTypeServiceImpl implements SpecificatTypeService {

    @Resource
    private OpeSpecificatTypeService opeSpecificatTypeService;
    @Reference
    private IdAppService idAppService;
    @Resource
    private SpecificatDefService specificatDefService;
    @Resource
    private SpecificatTypeServiceMapper specificatTypeServiceMapper;
    @Resource
    private OpeSaleScooterService opeSaleScooterService;
    @Resource
    private SpecificatDefGroupMapper specificatDefGroupMapper;
    @Resource
    private SpecificatDefMapper specificatDefMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private SpecificatGroupServiceMapper specificatGroupServiceMapper;


    @Override
    @Transactional
    public GeneralResult specificatTypeSave(SpecificatTypeSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getSt())){
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NOT_NULL.getMessage());
        }
        List<SpecificatDefEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), SpecificatDefEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (enters.size() > 8){
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NUM_ERROR.getCode(), ExceptionCodeEnums.DEF_NUM_ERROR.getMessage());
        }
        // 校验名称的唯一性
        checkSpecificatName(enter);
        // 校验自定义项的名称和value
        checkDef(enters);
        OpeSpecificatType specificatType = new OpeSpecificatType();
        specificatType.setGroupId(enter.getGroupId());
        specificatType.setSpecificatName(enter.getSpecificatName());
        specificatType.setDr(0);
        // 生成编码
        String code = createTypeCode();
        specificatType.setSpecificatCode(code);
        specificatType.setCreatedBy(enter.getUserId());
        specificatType.setCreatedTime(new Date());
        specificatType.setUpdatedBy(enter.getUserId());
        specificatType.setUpdatedTime(new Date());
        specificatType.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_TYPE));
        opeSpecificatTypeService.saveOrUpdate(specificatType);
        // 再处理自定义项
        for (SpecificatDefEnter defEnter : enters) {
            defEnter.setSpecificatId(specificatType.getId());
        }
        specificatDefService.saveSpecificatDef(enters,enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @Transactional
    public GeneralResult specificatTypeDelete(IdEnter enter) {
        // todo 校验当前规格是否被使用
        // 2020 10 16 追加规格类型是否被销售车辆使用了，使用不能删除
        QueryWrapper<OpeSaleScooter> qw = new QueryWrapper<>();
        qw.eq(OpeSaleScooter.COL_SPECIFICAT_ID,enter.getId());
        int count = opeSaleScooterService.count(qw);
        if(count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_IS_USED.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_IS_USED.getMessage());
        }
        opeSpecificatTypeService.removeById(enter.getId());
        // 删除规格的自定义项
        specificatDefService.deleSpecificatDef(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @Transactional
    public GeneralResult specificatTypeEdit(SpecificatTypeSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getSt())){
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NOT_NULL.getMessage());
        }
        List<SpecificatDefEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), SpecificatDefEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (enters.size() > 8){
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NUM_ERROR.getCode(), ExceptionCodeEnums.DEF_NUM_ERROR.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeService.getById(enter.getId());
        if(specificatType == null){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        checkSpecificatName(enter);
        // 校验自定义项的名称和value
        checkDef(enters);
        specificatType.setSpecificatName(enter.getSpecificatName());
        specificatType.setGroupId(enter.getGroupId());
        specificatType.setUpdatedBy(enter.getUserId());
        specificatType.setUpdatedTime(new Date());
        opeSpecificatTypeService.updateById(specificatType);
        // 下面处理自定义项 先把之前的删除 再新增
        specificatDefService.deleSpecificatDef(specificatType.getId());
        for (SpecificatDefEnter defEnter : enters) {
            defEnter.setSpecificatId(specificatType.getId());
        }
        specificatDefService.saveSpecificatDef(enters,enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public PageResult<SpecificatTypeListResult> specificatTypeList(SpecificatTypeListEnter enter) {
        // 去空格
        SesStringUtils.objStringTrim(enter);
        int num = specificatTypeServiceMapper.listNum(enter);
        if(num == 0){
            return PageResult.createZeroRowResult(enter);
        }
        List<SpecificatTypeListResult> list = specificatTypeServiceMapper.specificatTypeList(enter);
        return PageResult.create(enter, num, list);
    }


    @Override
    public SpecificatTypeDetailResult specificatTypeDetail(IdEnter enter) {
        SpecificatTypeDetailResult result = new SpecificatTypeDetailResult();
        OpeSpecificatType specificatType = opeSpecificatTypeService.getById(enter.getId());
        if(specificatType == null){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        result = specificatTypeServiceMapper.specificatTypeDetail(enter.getId());
        // 再找规格类型的自定义项
        List<OpeSpecificatDef> defs = specificatDefService.getDef(enter.getId());
        List<SpecificatDefResult> resultDefs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(defs)){
            for (OpeSpecificatDef def : defs) {
                SpecificatDefResult specificatDefEnter = new SpecificatDefResult();
                BeanUtils.copyProperties(def,specificatDefEnter);
                resultDefs.add(specificatDefEnter);
            }
        }
        result.setDef(resultDefs);
        return result;
    }


    @Override
    public BooleanResult specificatNameCheck(SpecificatTypeSaveOrEditEnter enter) {
        BooleanResult booleanResult = new BooleanResult();
        boolean flag = true;
        if(Strings.isNullOrEmpty(enter.getSpecificatName())){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getMessage());
        }
        // 先对名称进行正则校验
//        Pattern p= Pattern.compile(RegexpConstant.SPECIFICATNAME);
//        Matcher m = p.matcher(enter.getSpecificatName());
//        if (!m.matches()){
//            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getMessage());
//        }
        QueryWrapper<OpeSpecificatType>  qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_SPECIFICAT_NAME,enter.getSpecificatName());
        if(enter.getId() != null){
            // 这个时候是修改 需要排除本身去校验
            qw.ne(OpeSpecificatType.COL_ID,enter.getId());
        }
        int count = opeSpecificatTypeService.count(qw);
        if(count > 0){
            // 已经存在返回false
            flag = false;
        }
        booleanResult.setSuccess(flag);
        booleanResult.setRequestId(enter.getRequestId());
        return booleanResult;
    }


    @Override
    public List<SpecificatTypeDataResult> specificatTypeData(GeneralEnter enter) {
        List<SpecificatTypeDataResult> resultList = specificatTypeServiceMapper.specificatTypeData();
        return resultList;
    }

//    if (Strings.isNullOrEmpty(enter.getSt())){
//        throw new SesWebRosException(ExceptionCodeEnums.DEF_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NOT_NULL.getMessage());
//    }
//    List<SpecificatDefEnter> enters = new ArrayList<>();
//        try {
//        enters = JSONArray.parseArray(enter.getSt(), SpecificatDefEnter.class);
//    } catch (Exception e) {
//        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
//    }

    @Override
    public GeneralResult insertSpecificType(InsertSpecificTypeParamDTO paramDTO) {
        Long userId = paramDTO.getUserId();

        /**
         * 规格类型名称唯一性校验
         */
        String specificName = specificatTypeServiceMapper.existsSpecificTypeByName(paramDTO.getSpecificatName());
        if (StringUtils.isNotBlank(specificName)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }

        // 请求参数校验
        List<SpecificDefGroupDTO> specificDefGroupList = checkSpecificationDef(paramDTO, 1);

        /**
         * 组装新增数据 -- 规格类型、规格自定义项分组、规则自定义项
         */
        // 规格类型数据
        Long id = idAppService.getId(SequenceName.OPE_SPECIFICAT_TYPE);
        paramDTO.setId(id);
        paramDTO.setCode(createTypeCode());
        paramDTO.setCreatedBy(userId);
        paramDTO.setCreatedTime(new Date());
        paramDTO.setUpdatedBy(userId);
        paramDTO.setUpdatedTime(new Date());

        // 规格自定义项集合
        List<SpecificDefDTO> specificDefList = new ArrayList<>();

        // 规格自定义项分组数据
        specificDefGroupList.forEach(defGroup -> {
            Long defGroupId = idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF_GROUP);
            defGroup.setId(defGroupId);
            defGroup.setSpecificatId(id);
            defGroup.setCreatedBy(userId);
            defGroup.setCreatedTime(new Date());
            defGroup.setUpdatedBy(userId);
            defGroup.setUpdatedTime(new Date());

            // 规格自定义项数据
            List<SpecificDefDTO> temp = defGroup.getGroupList();
            temp.forEach(t -> {
                t.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF));
                t.setSpecificatId(id);
                t.setSpecificDefGroupId(defGroupId);
                t.setCreatedBy(userId);
                t.setCreatedTime(new Date());
                t.setUpdatedBy(userId);
                t.setUpdatedTime(new Date());

                specificDefList.add(t);
            });
        });

        List<SpecificDefGroupDTO> newSpecificDefGroupList = specificDefGroupList;

        /**
         * -新增规格类型相关数据
         */
        transactionTemplate.execute(specificTypeStatus -> {
            try {
                specificatTypeServiceMapper.insertSpecificatType(paramDTO);
                specificatDefGroupMapper.batchInsertSpecificatDefGroup(newSpecificDefGroupList);
                specificatDefMapper.batchInsertSpecificatDef(specificDefList);
            } catch (Exception e) {
                specificTypeStatus.setRollbackOnly();
                log.error("【新增规格类型失败】----{}", ExceptionUtils.getStackTrace(e));
            }
            return 1;
        });

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public GeneralResult updateSpecificType(InsertSpecificTypeParamDTO paramDTO) {
        Long userId = paramDTO.getUserId();

        if (null == paramDTO.getId()) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }

        SpecificTypeDTO specificType = specificatTypeServiceMapper.getSpecificTypeById(paramDTO.getId());
        if (null == specificType) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }

        // 规格类型名称唯一性校验
        String specificName = specificatTypeServiceMapper.existsSpecificTypeByName(paramDTO.getSpecificatName());
        if (StringUtils.isNotBlank(specificName) && !specificName.equals(specificType.getSpecificatName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }

        /**
         * 请求入参自定义项分组、自定义项校验
         */
        List<SpecificDefGroupDTO> specificDefGroupList = checkSpecificationDef(paramDTO, 2);

        /**
         * 组装修改数据 -- 规格类型、规格自定义项分组、规则自定义项
         */
        // 规格类型数据
        paramDTO.setUpdatedBy(userId);
        paramDTO.setUpdatedTime(new Date());

        // 规格自定义项集合
        List<SpecificDefDTO> specificDefList = new ArrayList<>();

        // 规格自定义项分组数据
        specificDefGroupList.forEach(defGroup -> {
            defGroup.setUpdatedBy(userId);
            defGroup.setUpdatedTime(new Date());

            // 规格自定义项数据
            List<SpecificDefDTO> temp = defGroup.getGroupList();
            temp.forEach(t -> {
                t.setUpdatedBy(userId);
                t.setUpdatedTime(new Date());

                specificDefList.add(t);
            });
        });

        List<SpecificDefGroupDTO> newSpecificDefGroupList = specificDefGroupList;

        /**
         * 修改规格类型相关数据
         */
        transactionTemplate.execute(updateSpecificType -> {
            try {
                specificatTypeServiceMapper.updateSpecificatType(paramDTO);
                specificatDefGroupMapper.batchUpdateSpecificatDefGroup(newSpecificDefGroupList);
                specificatDefMapper.batchUpdateSpecificatDef(specificDefList);
            } catch (Exception e) {
                log.error("【修改规格类型失败】----{}", ExceptionUtils.getStackTrace(e));
                updateSpecificType.setRollbackOnly();
            }
            return 1;
        });

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public QuerySpecificTypeDetailResultDTO getSpecificTypeDetailById(IdEnter enter) {
        QuerySpecificTypeDetailResultDTO specificType = specificatTypeServiceMapper.getSpecificTypeDetailById(enter.getId());
        if (null == specificType) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }

        /**
         * 查询规格类型自定义项分组和自定义项信息
         */
        List<SpecificDefGroupDTO> specificDefGroupList = specificatDefGroupMapper.getSpecificDefGroupBySpecificId(enter.getId());
        specificType.setSpecificDefGroupList(specificDefGroupList);

        return specificType;
    }


    /**
     * @Author Aleks
     * @Description  校验自定义项的名称和value值
     * @Date  2020/10/10 14:31
     * @Param [lists]
     * @return
     **/
    public void checkDef(List<SpecificatDefEnter> lists){
        for (SpecificatDefEnter list : lists) {
            // 先校验名称：长度20位，不能有特殊字符
            if (Strings.isNullOrEmpty(list.getDefName())){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NAME_NOT_NULL.getMessage());
            }
            if (list.getDefName().length() > 50){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
            }
//            // 校验正则
//            Pattern p= Pattern.compile(RegexpConstant.specialCharacters);
//            Matcher m=p.matcher(list.getDefName());
//            if (!m.matches()){
//                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
//            }
//            // 再校验value值，长度10位，仅数值和小数点
            if (Objects.isNull(list.getDefValue())){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
//            String defValue = String.valueOf(list.getDefValue());
            if (list.getDefValue().length() > 50){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
        }
    }

    /**
     * 新增/修改规格类型请求参数相关校验
     * @param paramDTO
     * @param type 1新增 2修改
     */
    private List<SpecificDefGroupDTO> checkSpecificationDef(InsertSpecificTypeParamDTO paramDTO, Integer type) {
        List<SpecificDefDTO> specificDefList = new ArrayList<>();
        List<SpecificDefGroupDTO> specificDefGroupList = new ArrayList<>();

        /**
         * 检查规格分组是否存在
         */
        SpecificGroupDTO specificGroup = specificatGroupServiceMapper.getSpecifiGroupById(paramDTO.getGroupId());
        if (null != specificGroup) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }

        try {
            specificDefGroupList = JSONArray.parseArray(paramDTO.getSt(), SpecificDefGroupDTO.class);
        } catch (Exception e) {
            log.error(String.format("%s----参数数据格式有误", type == 1 ? "【新增规格类型失败】" : "【修改规格类型失败】"));
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        /**
         * 自定义项分组名称相关校验
         */
        specificDefGroupList.forEach(defGroup -> {
            if (StringUtils.isBlank(defGroup.getName())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_GROUP_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_GROUP_NAME_NOT_NULL.getMessage());
            }
            if (defGroup.getName().length() > 50) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_GROUP_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_GROUP_NAME_ILLEGAL.getMessage());
            }
            specificDefList.addAll(defGroup.getGroupList());
        });

        /**
         * 自定义项名称和值
         */
        specificDefList.forEach(def -> {
            if (StringUtils.isBlank(def.getDefName())){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NAME_NOT_NULL.getMessage());
            }
            if (def.getDefName().length() > 50){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
            }
            if (StringUtils.isBlank(def.getDefValue())){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
            if (def.getDefValue().length() > 50){
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
        });

        return specificDefGroupList;
    }


    /**
     * @Author Aleks
     * @Description  校验规格类型的名称是否重复
     * @Date  2020/10/9 10:34
     * @Param
     * @return
     **/
    public void checkSpecificatName(SpecificatTypeSaveOrEditEnter enter){
        if(Strings.isNullOrEmpty(enter.getSpecificatName())){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getMessage());
        }
        // 先对名称进行正则校验
        Pattern p= Pattern.compile(RegexpConstant.SPECIFICATNAME);
        Matcher m = p.matcher(enter.getSpecificatName());
        if (!m.matches()){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getMessage());
        }
        QueryWrapper<OpeSpecificatType>  qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_SPECIFICAT_NAME,enter.getSpecificatName());
        if(enter.getId() != null){
            // 这个时候是修改 需要排除本身去校验
            qw.ne(OpeSpecificatType.COL_ID,enter.getId());
        }
        int count = opeSpecificatTypeService.count(qw);
        if(count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }
    }


    /**
     * @Author Aleks
     * @Description  生成规格类型的编码 RTYYYYMMDD000
     * @Date  2020/9/29 11:39
     * @Param []
     * @return
     **/
    public String createTypeCode(){
        String code = "RT"+DateUtil.getSimpleDateStamp()+"000";
        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.orderByDesc(OpeSpecificatType.COL_SPECIFICAT_CODE);
        qw.last("limit 1");
        OpeSpecificatType specificatType = opeSpecificatTypeService.getOne(qw);
        if(specificatType != null && !Strings.isNullOrEmpty(specificatType.getSpecificatCode())){
            String oldCode = specificatType.getSpecificatCode();
            Integer i = Integer.parseInt(oldCode.substring(oldCode.length() - 3));
            i ++;
            code = "RT"+DateUtil.getSimpleDateStamp()+String.format("%3d", i).replace(" ", "0");
        }
        return code;
    }

}
