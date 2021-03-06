package com.redescooter.ses.web.ros.service.specificat.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.specification.QuerySpecificTypeDetailResultDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefGroupDTO;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatDefEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatDefResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeDetailResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListEnter;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeListResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeSaveOrEditEnter;
import com.redescooter.ses.web.ros.vo.specificat.dto.InsertSpecificTypeParamDTO;
import com.redescooter.ses.web.ros.vo.specificat.dto.SpecificGroupDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @DubboReference
    private IdAppService idAppService;
    @Autowired
    private SpecificatDefService specificatDefService;
    @Autowired
    private SpecificatTypeServiceMapper specificatTypeServiceMapper;
    @Autowired
    private OpeSaleScooterService opeSaleScooterService;
    @Autowired
    private SpecificatDefGroupMapper specificatDefGroupMapper;
    @Autowired
    private SpecificatDefMapper specificatDefMapper;
    @Autowired
    private SpecificatGroupServiceMapper specificatGroupServiceMapper;


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatTypeSave(SpecificatTypeSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getSt())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NOT_NULL.getMessage());
        }
        List<SpecificatDefEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), SpecificatDefEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (8 < enters.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NUM_ERROR.getCode(), ExceptionCodeEnums.DEF_NUM_ERROR.getMessage());
        }
        // ????????????????????????
        checkSpecificatName(enter);
        // ??????????????????????????????value
        checkDef(enters);
        OpeSpecificatType specificatType = new OpeSpecificatType();
        specificatType.setGroupId(enter.getGroupId());
        specificatType.setSpecificatName(enter.getSpecificatName());
        specificatType.setDr(0);
        // ????????????
        String code = createTypeCode();
        specificatType.setSpecificatCode(code);
        specificatType.setCreatedBy(enter.getUserId());
        specificatType.setCreatedTime(new Date());
        specificatType.setUpdatedBy(enter.getUserId());
        specificatType.setUpdatedTime(new Date());
        specificatType.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_TYPE));
        opeSpecificatTypeService.saveOrUpdate(specificatType);
        // ?????????????????????
        for (SpecificatDefEnter defEnter : enters) {
            defEnter.setSpecificatId(specificatType.getId());
        }
        specificatDefService.saveSpecificatDef(enters, enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatTypeDelete(IdEnter enter) {
        // todo ?????????????????????????????????
        // 2020 10 16 ?????????????????????????????????????????????????????????????????????
        QueryWrapper<OpeSaleScooter> qw = new QueryWrapper<>();
        qw.eq(OpeSaleScooter.COL_SPECIFICAT_ID, enter.getId());
        int count = opeSaleScooterService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_IS_USED.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_IS_USED.getMessage());
        }
        // ??????????????????
        opeSpecificatTypeService.removeById(enter.getId());
        // ??????????????????????????????
        specificatDefGroupMapper.deleteSpecificDefGroupBySpecificId(enter.getId());
        // ???????????????????????????
        specificatDefService.deleSpecificatDef(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult specificatTypeEdit(SpecificatTypeSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getSt())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NOT_NULL.getMessage());
        }
        List<SpecificatDefEnter> enters = new ArrayList<>();
        try {
            enters = JSONArray.parseArray(enter.getSt(), SpecificatDefEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (8 < enters.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DEF_NUM_ERROR.getCode(), ExceptionCodeEnums.DEF_NUM_ERROR.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(specificatType)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        checkSpecificatName(enter);
        // ??????????????????????????????value
        checkDef(enters);
        specificatType.setSpecificatName(enter.getSpecificatName());
        specificatType.setGroupId(enter.getGroupId());
        specificatType.setUpdatedBy(enter.getUserId());
        specificatType.setUpdatedTime(new Date());
        opeSpecificatTypeService.updateById(specificatType);
        // ???????????????????????? ????????????????????? ?????????
        specificatDefService.deleSpecificatDef(specificatType.getId());
        for (SpecificatDefEnter defEnter : enters) {
            defEnter.setSpecificatId(specificatType.getId());
        }
        specificatDefService.saveSpecificatDef(enters, enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public PageResult<SpecificatTypeListResult> specificatTypeList(SpecificatTypeListEnter enter) {
        // ?????????
        SesStringUtils.objStringTrim(enter);
        int num = specificatTypeServiceMapper.listNum(enter);
        if (NumberUtil.eqZero(num)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SpecificatTypeListResult> list = specificatTypeServiceMapper.specificatTypeList(enter);
        return PageResult.create(enter, num, list);
    }


    @Override
    public SpecificatTypeDetailResult specificatTypeDetail(IdEnter enter) {
        SpecificatTypeDetailResult result = new SpecificatTypeDetailResult();
        OpeSpecificatType specificatType = opeSpecificatTypeService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(specificatType)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        result = specificatTypeServiceMapper.specificatTypeDetail(enter.getId());
        // ?????????????????????????????????
        List<OpeSpecificatDef> defs = specificatDefService.getDef(enter.getId());
        List<SpecificatDefResult> resultDefs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(defs)) {
            for (OpeSpecificatDef def : defs) {
                SpecificatDefResult specificatDefEnter = new SpecificatDefResult();
                BeanUtils.copyProperties(def, specificatDefEnter);
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
        if (Strings.isNullOrEmpty(enter.getSpecificatName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getMessage());
        }
        // ??????????????????????????????
//        Pattern p= Pattern.compile(RegexpConstant.SPECIFICATNAME);
//        Matcher m = p.matcher(enter.getSpecificatName());
//        if (!m.matches()){
//            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getMessage());
//        }
        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_SPECIFICAT_NAME, enter.getSpecificatName());
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            // ????????????????????? ???????????????????????????
            qw.ne(OpeSpecificatType.COL_ID, enter.getId());
        }
        int count = opeSpecificatTypeService.count(qw);
        if (0 < count) {
            // ??????????????????false
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult insertSpecificType(InsertSpecificTypeParamDTO paramDTO) {
        Long userId = paramDTO.getUserId();

        /**
         * ?????????????????????????????????
         */
        String specificName = specificatTypeServiceMapper.existsSpecificTypeByName(paramDTO.getSpecificatName());
        if (StringUtils.isNotBlank(specificName)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }

        // ??????????????????
        List<SpecificDefGroupDTO> specificDefGroupList = checkSpecificationDef(paramDTO, 1);

        /**
         * ?????????????????? -- ????????????????????????????????????????????????????????????
         */
        // ??????????????????
        Long id = idAppService.getId(SequenceName.OPE_SPECIFICAT_TYPE);
        paramDTO.setId(id);
        paramDTO.setCode(createTypeCode());
        paramDTO.setCreatedBy(userId);
        paramDTO.setCreatedTime(new Date());
        paramDTO.setUpdatedBy(userId);
        paramDTO.setUpdatedTime(new Date());

        // ????????????????????????
        List<SpecificDefDTO> specificDefList = new ArrayList<>();

        // ??????????????????????????????
        specificDefGroupList.forEach(defGroup -> {
            Long defGroupId = idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF_GROUP);
            defGroup.setId(defGroupId);
            defGroup.setSpecificatId(id);
            defGroup.setCreatedBy(userId);
            defGroup.setCreatedTime(new Date());
            defGroup.setUpdatedBy(userId);
            defGroup.setUpdatedTime(new Date());

            // ????????????????????????
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
         * -??????????????????????????????
         */
        try {
            specificatTypeServiceMapper.insertSpecificatType(paramDTO);
            specificatDefGroupMapper.batchInsertSpecificatDefGroup(newSpecificDefGroupList);
            specificatDefMapper.batchInsertSpecificatDef(specificDefList);
        } catch (Exception e) {
            log.error("??????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateSpecificType(InsertSpecificTypeParamDTO paramDTO) {
        Long userId = paramDTO.getUserId();

        if (StringManaConstant.entityIsNull(paramDTO.getId())) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }

        SpecificTypeDTO specificType = specificatTypeServiceMapper.getSpecificTypeById(paramDTO.getId());
        if (StringManaConstant.entityIsNull(specificType)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }

        // ?????????????????????????????????
        String specificName = specificatTypeServiceMapper.existsSpecificTypeByName(paramDTO.getSpecificatName());
        if (StringUtils.isNotBlank(specificName) && !specificName.equals(specificType.getSpecificatName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }

        /**
         * ???????????????????????????????????????????????????
         */
        List<SpecificDefGroupDTO> specificDefGroupList = checkSpecificationDef(paramDTO, 2);
        List<SpecificDefDTO> specificDefList = new ArrayList<>();

        specificDefGroupList.forEach(defGroup -> {
            Long defGroupId = idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF_GROUP);
            defGroup.setId(defGroupId);
            defGroup.setSpecificatId(paramDTO.getId());
            defGroup.setCreatedBy(userId);
            defGroup.setCreatedTime(new Date());
            defGroup.setUpdatedBy(userId);
            defGroup.setUpdatedTime(new Date());

            // ????????????????????????
            defGroup.getGroupList().forEach(def -> {
                def.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF));
                def.setSpecificatId(paramDTO.getId());
                def.setSpecificDefGroupId(defGroupId);
                def.setCreatedBy(userId);
                def.setCreatedTime(new Date());
                def.setUpdatedBy(userId);
                def.setUpdatedTime(new Date());

                specificDefList.add(def);
            });
        });

        /**
         * ??????????????????????????????
         */
        try {
            specificatTypeServiceMapper.updateSpecificatType(paramDTO);

            // ????????????????????????, ????????????????????????????????????
            specificatDefGroupMapper.deleteSpecificDefGroupBySpecificId(paramDTO.getId());
            specificatDefMapper.deleteSpecificatDefById(paramDTO.getId());

            specificatDefGroupMapper.batchInsertSpecificatDefGroup(specificDefGroupList);
            specificatDefMapper.batchInsertSpecificatDef(specificDefList);
        } catch (Exception e) {
            log.error("??????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }
        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public QuerySpecificTypeDetailResultDTO getSpecificTypeDetailById(IdEnter enter) {
        QuerySpecificTypeDetailResultDTO specificType = specificatTypeServiceMapper.getSpecificTypeDetailById(enter.getId());
        if (StringManaConstant.entityIsNull(specificType)) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }

        /**
         * ?????????????????????????????????????????????????????????
         */
        List<SpecificDefGroupDTO> specificDefGroupList = specificatDefGroupMapper.getSpecificDefGroupBySpecificId(enter.getId());
        specificType.setSpecificDefGroupList(specificDefGroupList);

        return specificType;
    }


    /**
     * @return
     * @Author Aleks
     * @Description ??????????????????????????????value???
     * @Date 2020/10/10 14:31
     * @Param [lists]
     **/
    public void checkDef(List<SpecificatDefEnter> lists) {
        for (SpecificatDefEnter list : lists) {
            // ????????????????????????20???????????????????????????
            if (Strings.isNullOrEmpty(list.getDefName())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NAME_NOT_NULL.getMessage());
            }
            if (50 < list.getDefName().length()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
            }
//            // ????????????
//            Pattern p= Pattern.compile(RegexpConstant.specialCharacters);
//            Matcher m=p.matcher(list.getDefName());
//            if (!m.matches()){
//                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
//            }
//            // ?????????value????????????10???????????????????????????
            if (Objects.isNull(list.getDefValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
//            String defValue = String.valueOf(list.getDefValue());
            if (50 < list.getDefValue().length()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
        }
    }

    /**
     * ??????/??????????????????????????????????????????
     *
     * @param paramDTO
     * @param type     1?????? 2??????
     */
    private List<SpecificDefGroupDTO> checkSpecificationDef(InsertSpecificTypeParamDTO paramDTO, Integer type) {
        List<SpecificDefDTO> specificDefList = new ArrayList<>();
        List<SpecificDefGroupDTO> specificDefGroupList = new ArrayList<>();

        /**
         * ??????????????????????????????
         */
        SpecificGroupDTO specificGroup = specificatGroupServiceMapper.getSpecifiGroupById(paramDTO.getGroupId());
        if (StringManaConstant.entityIsNull(specificGroup)) {
            throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
        }

        try {
            specificDefGroupList = JSONArray.parseArray(paramDTO.getSt(), SpecificDefGroupDTO.class);
        } catch (Exception e) {
            log.error(String.format("%s----????????????????????????", type == 1 ? "??????????????????????????????" : "??????????????????????????????"));
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        /**
         * ????????????????????????????????????
         */
        specificDefGroupList.forEach(defGroup -> {
            if (StringUtils.isBlank(defGroup.getName())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_GROUP_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_GROUP_NAME_NOT_NULL.getMessage());
            }
            if (50 < defGroup.getName().length()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_GROUP_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_GROUP_NAME_ILLEGAL.getMessage());
            }
            specificDefList.addAll(defGroup.getGroupList());
        });

        /**
         * ????????????????????????
         */
        specificDefList.forEach(def -> {
            if (StringUtils.isBlank(def.getDefName())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.DEF_NAME_NOT_NULL.getMessage());
            }
            if (50 < def.getDefName().length()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_NAME_ILLEGAL.getMessage());
            }
            if (StringUtils.isBlank(def.getDefValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
            if (50 < def.getDefValue().length()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getCode(), ExceptionCodeEnums.DEF_VALUE_ILLEGAL.getMessage());
            }
        });

        return specificDefGroupList;
    }


    /**
     * @return
     * @Author Aleks
     * @Description ???????????????????????????????????????
     * @Date 2020/10/9 10:34
     * @Param
     **/
    public void checkSpecificatName(SpecificatTypeSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getSpecificatName())) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_NOT_NULL.getMessage());
        }
        // ??????????????????????????????
        Pattern p = Pattern.compile(RegexpConstant.SPECIFICATNAME);
        Matcher m = p.matcher(enter.getSpecificatName());
        if (!m.matches()) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_ILLEGAL.getMessage());
        }
        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatType.COL_SPECIFICAT_NAME, enter.getSpecificatName());
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            // ????????????????????? ???????????????????????????
            qw.ne(OpeSpecificatType.COL_ID, enter.getId());
        }
        int count = opeSpecificatTypeService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NAME_EXIST.getMessage());
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description ??????????????????????????? RTYYYYMMDD000
     * @Date 2020/9/29 11:39
     * @Param []
     **/
    public String createTypeCode() {
        String code = "RT" + DateUtil.getSimpleDateStamp() + "000";
        QueryWrapper<OpeSpecificatType> qw = new QueryWrapper<>();
        qw.orderByDesc(OpeSpecificatType.COL_SPECIFICAT_CODE);
        qw.last("limit 1");
        OpeSpecificatType specificatType = opeSpecificatTypeService.getOne(qw);
        if (StringManaConstant.entityIsNotNull(specificatType) && !Strings.isNullOrEmpty(specificatType.getSpecificatCode())) {
            String oldCode = specificatType.getSpecificatCode();
            Integer i = Integer.parseInt(oldCode.substring(oldCode.length() - 3));
            i++;
            code = "RT" + DateUtil.getSimpleDateStamp() + String.format("%3d", i).replace(" ", "0");
        }
        return code;
    }

}
