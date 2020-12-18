package com.redescooter.ses.web.ros.service.distributor.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dao.distributor.OpeDistributorExMapper;
import com.redescooter.ses.web.ros.dao.distributor.OpeDistributorMapper;
import com.redescooter.ses.web.ros.dm.OpeDistributor;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.enums.distributor.DistributorTypeEnum;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.distributor.DistributorService;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorAddEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorCityAndCPSelectorEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorEnableOrDisableEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorListEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorNameEnter;
import com.redescooter.ses.web.ros.vo.distributor.enter.DistributorUpdateEnter;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorCityAndCPSelectorResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorDetailResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorListResult;
import com.redescooter.ses.web.ros.vo.distributor.result.DistributorSaleProductResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 经销商ServiceImpl
 * @Author Chris
 * @Date 2020/12/16 15:58
 */
@Service
public class DistributorServiceImpl extends ServiceImpl<OpeDistributorMapper, OpeDistributor> implements DistributorService {

    private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);

    @Autowired
    private OpeDistributorMapper opeDistributorMapper;

    @Autowired
    private OpeDistributorExMapper opeDistributorExMapper;

    @Autowired
    private OpeSpecificatTypeMapper opeSpecificatTypeMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * 门店列表
     */
    @Override
    public Response<PageResult<DistributorListResult>> getList(DistributorListEnter enter) {
        logger.info("门店列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);
        int count = opeDistributorExMapper.getListCount(enter);
        if (count == 0) {
            return new Response<>(PageResult.createZeroRowResult(enter));
        }
        List<DistributorListResult> list = opeDistributorExMapper.getList(enter);
        return new Response<>(PageResult.create(enter, count, list));
    }

    /**
     * 城市下拉框,邮政编码下拉框,城市和邮政编码联动
     * 若想得到城市下拉框,入参传1
     * 若想得到邮政编码下拉框,入参传2
     * 若想得到联动后的邮政编码下拉框,入参传城市名称
     */
    @Override
    public Response<DistributorCityAndCPSelectorResult> getCityAndCPSelector(DistributorCityAndCPSelectorEnter enter) {
        logger.info("城市和邮政编码联动的入参是:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getKey())) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }

        DistributorCityAndCPSelectorResult result = new DistributorCityAndCPSelectorResult();
        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeDistributor::getUserId, enter.getUserId());
        if (!"1".equals(enter.getKey()) && !"2".equals(enter.getKey())) {
            wrapper.eq(OpeDistributor::getCity, enter.getKey());
        }
        List<OpeDistributor> list = opeDistributorMapper.selectList(wrapper);
        List<String> collect = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            if ("1".equals(enter.getKey())) {
                 collect = list.stream().map(o -> o.getCity()).distinct().collect(Collectors.toList());
            } else {
                 collect = list.stream().map(o -> o.getCp()).distinct().collect(Collectors.toList());
            }
        }
        result.setList(collect);
        return new Response<>(result);
    }

    /**
     * 启用/停用门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<GeneralResult> enableOrDisable(DistributorEnableOrDisableEnter enter) {
        logger.info("启用/停用门店的入参是:[{}]", enter);
        OpeDistributor model = new OpeDistributor();
        model.setId(enter.getId());
        model.setStatus(enter.getStatus());
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        int i = opeDistributorMapper.updateById(model);
        if (i > 0) {
            return new Response<>();
        }
        throw new SesWebRosException(ExceptionCodeEnums.UPDATE_FAIL.getCode(), ExceptionCodeEnums.UPDATE_FAIL.getMessage());
    }

    /**
     * 新增门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<GeneralResult> add(DistributorAddEnter enter) {
        logger.info("新增门店的入参是:[{}]", enter);
        if (enter.getEmail().indexOf("@") == -1) {
            throw new SesWebRosException(ExceptionCodeEnums.STORE_EMAIL_MUST_CHAR.getCode(), ExceptionCodeEnums.STORE_EMAIL_MUST_CHAR.getMessage());
        }

        String splitChar = ",";
        OpeDistributor model = new OpeDistributor();
        model.setId(idAppService.getId(SequenceName.OPE_DISTRIBUTOR));
        model.setDr(DelStatusEnum.VALID.getCode());
        model.setTenantId(enter.getTenantId());
        model.setUserId(enter.getUserId());
        model.setStatus(StatusEnum.DISABLE.getCode());
        model.setCode(getCode(enter));
        model.setName(enter.getName());
        model.setLogoUrl(enter.getLogoUrl());
        model.setCountryCode(enter.getCountryCode());
        model.setTel(enter.getTel());
        model.setEmail(enter.getEmail());
        model.setAddress(enter.getAddress());
        model.setLongitude(enter.getLongitude());
        model.setLatitude(enter.getLatitude());
        model.setCp(enter.getCp());
        model.setCity(enter.getCity());
        model.setArea(enter.getArea());
        model.setContractUrl(enter.getContractUrl());
        model.setNote(enter.getNote());

        String type = "";
        List<String> list = Lists.newArrayList();
        String[] splits = enter.getType().split(splitChar);
        Arrays.stream(splits).forEach(o -> list.add(o));
        type = list.stream().collect(Collectors.joining(splitChar));
        model.setType(type);

        if (StringUtils.isNotBlank(type)) {
            if (type.contains(DistributorTypeEnum.SALE.getCode())) {
                model.setSaleProduct(enter.getSaleProduct());
            }
        }
        model.setCreatedBy(enter.getUserId());
        model.setCreatedTime(new Date());
        int i = opeDistributorMapper.insert(model);
        if (i > 0) {
            return new Response<>();
        }
        throw new SesWebRosException(ExceptionCodeEnums.INSERT_FAIL.getCode(), ExceptionCodeEnums.INSERT_FAIL.getMessage());
    }

    /**
     * 编辑门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<GeneralResult> update(DistributorUpdateEnter enter) {
        logger.info("编辑门店的入参是:[{}]", enter);
        OpeDistributor model = new OpeDistributor();
        BeanUtils.copyProperties(enter, model);
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        int i = opeDistributorMapper.updateById(model);
        if (i > 0) {
            return new Response<>();
        }
        throw new SesWebRosException(ExceptionCodeEnums.UPDATE_FAIL.getCode(), ExceptionCodeEnums.UPDATE_FAIL.getMessage());
    }

    /**
     * 删除门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<GeneralResult> delete(IdEnter enter) {
        logger.info("删除门店的入参是:[{}]", enter);
        OpeDistributor distributor = opeDistributorMapper.selectById(enter.getId());
        if (StatusEnum.ENABLE.getCode().equals(distributor.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.ENABLE_STORE_NOT_DELETE.getCode(), ExceptionCodeEnums.ENABLE_STORE_NOT_DELETE.getMessage());
        }

        OpeDistributor model = new OpeDistributor();
        model.setId(enter.getId());
        model.setDr(DelStatusEnum.INVALID.getCode());
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        int i = opeDistributorMapper.updateById(model);
        if (i > 0) {
            return new Response<>();
        }
        throw new SesWebRosException(ExceptionCodeEnums.UPDATE_FAIL.getCode(), ExceptionCodeEnums.UPDATE_FAIL.getMessage());
    }

    /**
     * 门店详情
     */
    @Override
    public Response<DistributorDetailResult> getDetail(IdEnter enter) {
        logger.info("门店详情的入参是:[{}]", enter);
        OpeDistributor model = opeDistributorMapper.selectById(enter.getId());
        DistributorDetailResult result = new DistributorDetailResult();
        BeanUtils.copyProperties(model, result);

        String splitChar = ",";
        String[] strings = result.getType().split(splitChar);
        List<String> list = Lists.newArrayList();
        for (String s : strings) {
            String msg = DistributorTypeEnum.showMsg(s);
            list.add(msg);
        }
        String collect = list.stream().collect(Collectors.joining(splitChar));
        result.setTypeMsg(collect);
        result.setStatusMsg(StatusEnum.showMsg(result.getStatus()));
        if (StringUtils.isBlank(result.getNote())) {
            result.setNote("-");
        }
        return new Response<>(result);
    }

    /**
     * 门店名称
     * 如果传递-1,则查询所有门店名称
     * 如果传递-1之外的其他id,则查询除了此门店外的其他所有门店名称
     */
    @Override
    public Response<DistributorCityAndCPSelectorResult> getNameList(DistributorNameEnter enter) {
        DistributorCityAndCPSelectorResult result = new DistributorCityAndCPSelectorResult();
        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeDistributor::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeDistributor::getUserId, enter.getUserId());
        if (!"-1".equals(enter.getKey())) {
            wrapper.ne(OpeDistributor::getId, enter.getKey());
        }
        List<OpeDistributor> list = opeDistributorMapper.selectList(wrapper);
        List<String> nameList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            nameList = list.stream().map(o -> o.getName()).collect(Collectors.toList());
        }
        result.setList(nameList);
        return new Response<>(result);
    }

    /**
     * 门店类型选择销售,可售卖产品的数据来源
     */
    @Override
    public Response<List<DistributorSaleProductResult>> getSaleProduct(GeneralEnter enter) {
        List<DistributorSaleProductResult> result = Lists.newLinkedList();
        LambdaQueryWrapper<OpeSpecificatType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSpecificatType::getDr, DelStatusEnum.VALID.getCode());
        List<OpeSpecificatType> list = opeSpecificatTypeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeSpecificatType o : list) {
                DistributorSaleProductResult model = new DistributorSaleProductResult();
                model.setId(o.getId());
                model.setSpecificationName(o.getSpecificatName());
                result.add(model);
            }
        }
        return new Response<>(result);
    }

    /**
     * 获得门店编号
     */
    public String getCode(GeneralEnter enter) {
        String msg = "R";
        StringBuffer result = new StringBuffer();
        result.append(msg);

        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeDistributor::getUserId, enter.getUserId());
        List<OpeDistributor> list = opeDistributorMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 得到自增编号,从第2位开始截取
            list.forEach(o -> codeList.add(Integer.valueOf(o.getCode().substring(2))));
            // 倒序排列
            codeList.sort(Comparator.reverseOrder());
            int code = codeList.get(0) + 1;
            result.append(code);
        } else {
            result.append("0001");
        }
        return result.toString();
    }

}
