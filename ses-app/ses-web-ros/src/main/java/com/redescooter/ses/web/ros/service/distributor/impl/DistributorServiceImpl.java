package com.redescooter.ses.web.ros.service.distributor.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
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
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
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
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Autowired
    private StaffServiceMapper staffServiceMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Reference
    private IdAppService idAppService;

    /**
     * 门店列表
     */
    @Override
    public Response<PageResult<DistributorListResult>> getList(DistributorListEnter enter) {
        logger.info("门店列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        Set<Long> deptIds =  new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + enter.getUserId();
        // 通过这个来判断是不是管理员账号，默认为是管理员
        boolean flag = true;
        if (jedisCluster.exists(key)){
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if(!Strings.isNullOrEmpty(ids)){
                for (String s : ids.split(",")) {
                    deptIds.add(Long.parseLong(s));
                }
            }
        }

        int count = opeDistributorExMapper.getListCount(enter,flag?null:deptIds);
        if (count == 0) {
            return new Response<>(PageResult.createZeroRowResult(enter));
        }
        List<DistributorListResult> list = opeDistributorExMapper.getList(enter,flag?null:deptIds);
        return new Response<>(PageResult.create(enter, count, list));
    }

    /**
     * 城市下拉框,城市和邮政编码联动
     * 城市下拉框传1,城市和邮编联动传3
     */
    @Override
    public Response<DistributorCityAndCPSelectorResult> getCityAndCPSelector(DistributorCityAndCPSelectorEnter enter) {
        logger.info("城市和邮政编码联动的入参是:[{}]", enter);
        if (null == enter || StringUtils.isBlank(enter.getKey())) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }

        DistributorCityAndCPSelectorResult result = new DistributorCityAndCPSelectorResult();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        if ("3".equals(enter.getKey())) {
            wrapper.eq(OpeDistributor::getCity, enter.getCity());
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

        if (StringUtils.isNotBlank(enter.getKeyword())) {
            // 对list进行模糊搜索
            List<String> tempList = Lists.newLinkedList();
            Pattern pattern = Pattern.compile(enter.getKeyword());
            for (int i = 0; i < collect.size(); i++) {
                String name = collect.get(i);
                Matcher matcher = pattern.matcher(name);
                if (matcher.find()) {
                    tempList.add(name);
                }
            }

            for (String s : tempList) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("name", s);
                resultList.add(map);
            }
        } else {
            for (String s : collect) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("name", s);
                resultList.add(map);
            }
        }
        result.setList(resultList);
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
        model.setDeptId(enter.getOpeDeptId());
        model.setTenantId(enter.getTenantId());
        model.setUserId(enter.getUserId());
        model.setStatus(StatusEnum.DISABLE.getCode());
        model.setCode(getCode());
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
        boolean b = this.removeById(enter.getId());
        if (b) {
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
        List<String> typeList = Lists.newArrayList();
        for (String s : strings) {
            String msg = DistributorTypeEnum.showMsg(s);
            typeList.add(msg);
        }
        String typeMsg = typeList.stream().collect(Collectors.joining(splitChar));
        result.setTypeMsg(typeMsg);

        if (StringUtils.isNotBlank(result.getSaleProduct())) {
            String[] splits = result.getSaleProduct().split(splitChar);
            List<String> saleProductList = Lists.newArrayList();
            for (String s : splits) {
                OpeSpecificatType opeSpecificatType = opeSpecificatTypeMapper.selectById(s);
                if (null != opeSpecificatType) {
                    String specificationName = opeSpecificatType.getSpecificatName();
                    saleProductList.add(specificationName);
                }
            }
            String saleProductMsg = saleProductList.stream().collect(Collectors.joining(splitChar));
            result.setSaleProductMsg(saleProductMsg);
        }
        result.setStatusMsg(StatusEnum.showMsg(result.getStatus()));
        if (StringUtils.isBlank(result.getNote())) {
            result.setNote("-");
        }
        return new Response<>(result);
    }

    /**
     * 门店名称
     * 新增时传递1,修改时传递2
     */
    @Override
    public Response<BooleanResult> getNameList(DistributorNameEnter enter) {
        BooleanResult result = new BooleanResult();
        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeDistributor::getDr, DelStatusEnum.VALID.getCode());
        if ("2".equals(enter.getKey())) {
            wrapper.ne(OpeDistributor::getId, enter.getId());
        }
        List<OpeDistributor> list = opeDistributorMapper.selectList(wrapper);
        List<String> nameList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            nameList = list.stream().map(o -> o.getName()).collect(Collectors.toList());
        }
        // 如果包含门店名称,返回false
        if (nameList.contains(enter.getKeyword())) {
            result.setSuccess(Boolean.FALSE);
        } else {
            result.setSuccess(Boolean.TRUE);
        }
        return new Response<>(result);
    }

    /**
     * 门店类型选择销售,可售卖产品的数据来源
     */
    @Override
    public Response<List<DistributorSaleProductResult>> getSaleProduct(GeneralEnter enter) {
        List<DistributorSaleProductResult> list = opeDistributorExMapper.getSaleProduct();
        list = CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list;
        return new Response<>(list);
    }

    /**
     * 获得门店编号
     */
    public String getCode() {
        String msg = "R";
        StringBuffer result = new StringBuffer();
        result.append(msg);

        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeDistributor> wrapper = new LambdaQueryWrapper<>();
        List<OpeDistributor> list = opeDistributorMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 得到自增编号,从第2位开始截取
            for (OpeDistributor o : list) {
                String sub = o.getCode().substring(1);
                codeList.add(Integer.valueOf(sub));
            }
            // 倒序排列
            codeList.sort(Comparator.reverseOrder());
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(false);
            numberFormat.setMaximumIntegerDigits(4);
            numberFormat.setMinimumIntegerDigits(4);
            String code = numberFormat.format(new Double(codeList.get(0) + 1));
            result.append(code);
        } else {
            result.append("0001");
        }
        return result.toString();
    }

}
