package com.redescooter.ses.web.ros.service.specificat.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class SpecificatTypeServiceImpl implements SpecificatTypeService {

    @Autowired
    private OpeSpecificatTypeService opeSpecificatTypeService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private SpecificatDefService specificatDefService;

    @Autowired
    private SpecificatTypeServiceMapper specificatTypeServiceMapper;


    @Autowired
    private OpeSaleScooterService opeSaleScooterService;


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


    /**
     * @Author Aleks
     * @Description  校验自定义项的名称和value值
     * @Date  2020/10/10 14:31
     * @Param [lists]
     * @return
     **/
    public void
    checkDef(List<SpecificatDefEnter> lists){
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


    public static void main(String[] args) {
        String test ="^[0-9A-Z-]+$";
        String str = "xWSX-";
        Pattern p= Pattern.compile(test);
        Matcher m = p.matcher(str);
        if (!m.matches()){
            System.out.println("false");
        }else {
            System.out.println("true");
        }

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
}
