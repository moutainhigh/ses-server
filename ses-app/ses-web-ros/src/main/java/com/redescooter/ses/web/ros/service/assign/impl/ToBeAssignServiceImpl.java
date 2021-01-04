package com.redescooter.ses.web.ros.service.assign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeColorMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.enums.assign.CustomerTypeEnum;
import com.redescooter.ses.web.ros.enums.assign.FactoryEnum;
import com.redescooter.ses.web.ros.enums.assign.FlagEnum;
import com.redescooter.ses.web.ros.enums.assign.IndustryTypeEnum;
import com.redescooter.ses.web.ros.enums.assign.NodeEnum;
import com.redescooter.ses.web.ros.enums.assign.ProductTypeEnum;
import com.redescooter.ses.web.ros.enums.assign.ScooterTypeEnum;
import com.redescooter.ses.web.ros.enums.assign.YearEnum;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailScooterInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNodeResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 车辆待分配ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 14:50
 */
@Service
public class ToBeAssignServiceImpl implements ToBeAssignService {

    private static final Logger logger = LoggerFactory.getLogger(ToBeAssignServiceImpl.class);

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeSaleScooterMapper opeSaleScooterMapper;

    @Autowired
    private OpeColorMapper opeColorMapper;

    @Autowired
    private OpeSpecificatTypeMapper opeSpecificatTypeMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * 待分配列表
     */
    @Override
    public PageResult<ToBeAssignListResult> getToBeAssignList(ToBeAssignListEnter enter) {
        logger.info("待分配列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        int count = opeCarDistributeExMapper.getToBeAssignListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ToBeAssignListResult> list = opeCarDistributeExMapper.getToBeAssignList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 待分配列表点击分配带出数据
     */
    @Override
    public ToBeAssignDetailResult getToBeAssignDetail(CustomerIdEnter enter) {
        logger.info("待分配列表点击分配带出数据的入参是:[{}]", enter);
        ToBeAssignDetailResult result = new ToBeAssignDetailResult();
        List<ToBeAssignDetailScooterInfoResult> scooterList = Lists.newArrayList();
        ToBeAssignDetailScooterInfoResult scooter = new ToBeAssignDetailScooterInfoResult();

        // 查询该客户在node表是否存在,如果不存在,添加一条数据
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(nodeWrapper);
        if (CollectionUtils.isEmpty(nodeList)) {
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(DelStatusEnum.VALID.getCode());
            node.setTenantId(enter.getTenantId());
            node.setUserId(enter.getUserId());
            node.setCustomerId(enter.getCustomerId());
            node.setNode(NodeEnum.VIN_CODE.getCode());
            node.setFlag(FlagEnum.NOT.getCode());
            node.setCreatedBy(enter.getUserId());
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);
        }

        // 客户信息
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getCustomerId());
        if (null == customerInfo) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        String customerType = customerInfo.getCustomerType();
        String industryType = customerInfo.getIndustryType();
        if (StringUtils.isNotBlank(customerType)) {
            customerInfo.setCustomerTypeMsg(CustomerTypeEnum.showMsg(customerType));
        }
        if (StringUtils.isNotBlank(industryType)) {
            customerInfo.setIndustryTypeMsg(IndustryTypeEnum.showMsg(industryType));
        }

        // 得到询价单的产品id
        LambdaQueryWrapper<OpeCustomerInquiry> opeCustomerInquiryWrapper = new LambdaQueryWrapper<>();
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getDr, DelStatusEnum.VALID.getCode());
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getCustomerId());
        opeCustomerInquiryWrapper.orderByDesc(OpeCustomerInquiry::getCreatedTime);
        List<OpeCustomerInquiry> list = opeCustomerInquiryMapper.selectList(opeCustomerInquiryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCustomerInquiry customerInquiry = list.get(0);
        Long productId = customerInquiry.getProductId();
        if (null == productId) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 拿着产品id去ope_sale_scooter查询
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        // 得到型号id和颜色id
        Long specificatId = opeSaleScooter.getSpecificatId();
        Long colorId = opeSaleScooter.getColorId();

        // 拿着型号id去ope_specificat_type查询
        String specificatName = getSpecificatNameById(specificatId);
        scooter.setSpecificatId(specificatId);
        scooter.setSpecificatName(specificatName);

        // 拿着颜色id去ope_color查询
        Map<String, String> map = getColorNameAndValueById(colorId);
        scooter.setColorName(map.get("colorName"));
        scooter.setColorValue(map.get("colorValue"));

        scooter.setTotalCount(customerInquiry.getScooterQuantity());
        scooter.setToBeAssignCount(customerInquiry.getScooterQuantity());
        scooterList.add(scooter);

        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 填写完座位数点击下一步
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToBeAssignNextStopResult getSeatNext(ToBeAssignSeatNextEnter enter) {
        logger.info("填写完座位数点击下一步的入参是:[{}]", enter);
        if (null == enter || CollectionUtils.isEmpty(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.SEAT_NOT_EMPTY.getCode(), ExceptionCodeEnums.SEAT_NOT_EMPTY.getMessage());
        }
        ToBeAssignNextStopResult result = new ToBeAssignNextStopResult();
        List<ToBeAssignNextStopDetailResult> scooterList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooter = new ToBeAssignNextStopDetailResult();

        ToBeAssignSeatNextDetailEnter detailEnter = enter.getList().get(0);
        Long specificatId = detailEnter.getSpecificatId();
        String specificatName = detailEnter.getSpecificatName();
        Integer seatNumber = detailEnter.getSeatNumber();

        // 生成VIN Code,只需车型名称和座位数量这两个变量
        String vinCode = generateVINCode(specificatName, seatNumber);
        logger.info("生成的VIN Code是:[{}]", vinCode);

        // 新增主表
        long id = idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE);
        OpeCarDistribute model = new OpeCarDistribute();
        model.setId(id);
        model.setDr(DelStatusEnum.VALID.getCode());
        model.setTenantId(enter.getTenantId());
        model.setUserId(enter.getUserId());
        model.setCustomerId(enter.getCustomerId());
        model.setSpecificatTypeId(specificatId);
        model.setSeatNumber(seatNumber);
        model.setVinCode(vinCode);
        model.setCreatedBy(enter.getUserId());
        model.setCreatedTime(new Date());
        opeCarDistributeMapper.insert(model);

        // node表node字段+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(NodeEnum.BIND_LICENSE_PLATE.getCode());
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);

        // 查询主表
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectById(id);
        scooter.setId(opeCarDistribute.getId());
        scooter.setSpecificatName(getSpecificatNameById(opeCarDistribute.getSpecificatTypeId()));
        scooter.setSeatNumber(opeCarDistribute.getSeatNumber());
        scooter.setVinCode(opeCarDistribute.getVinCode());

        scooterList.add(scooter);
        result.setList(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 填写完车牌点击下一步
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToBeAssignNextStopResult getLicensePlateNext(ToBeAssignLicensePlateNextEnter enter) {
        logger.info("填写完车牌点击下一步的入参是:[{}]", enter);
        if (null == enter || CollectionUtils.isEmpty(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.LICENSE_PLATE_NOT_EMPTY.getCode(), ExceptionCodeEnums.LICENSE_PLATE_NOT_EMPTY.getMessage());
        }
        ToBeAssignNextStopResult result = new ToBeAssignNextStopResult();
        List<ToBeAssignNextStopDetailResult> scooterList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooter = new ToBeAssignNextStopDetailResult();

        ToBeAssignLicensePlateNextDetailEnter detailEnter = enter.getList().get(0);
        Long id = detailEnter.getId();
        String licensePlate = detailEnter.getLicensePlate();

        // 修改主表
        OpeCarDistribute model = new OpeCarDistribute();
        model.setId(id);
        model.setLicensePlate(licensePlate);
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        opeCarDistributeMapper.updateById(model);

        // node表node字段+1
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(NodeEnum.BIND_RSN.getCode());
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);

        // 查询主表
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectById(id);
        scooter.setId(opeCarDistribute.getId());
        scooter.setSpecificatName(getSpecificatNameById(opeCarDistribute.getSpecificatTypeId()));
        scooter.setSeatNumber(opeCarDistribute.getSeatNumber());
        scooter.setVinCode(opeCarDistribute.getVinCode());
        scooter.setLicensePlate(opeCarDistribute.getLicensePlate());

        scooterList.add(scooter);
        result.setList(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 根据R.SN获得颜色
     */
    @Override
    public ToBeAssignColorResult getColorByRSN(StringEnter enter) {
        return null;
    }

    /**
     * 填写完R.SN并点击提交
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToBeAssignNextStopResult submit(ToBeAssignSubmitEnter enter) {
        logger.info("填写完R.SN并点击提交的入参是:[{}]", enter);
        if (null == enter || CollectionUtils.isEmpty(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.RSN_NOT_EMPTY.getCode(), ExceptionCodeEnums.RSN_NOT_EMPTY.getMessage());
        }
        ToBeAssignNextStopResult result = new ToBeAssignNextStopResult();
        List<ToBeAssignNextStopDetailResult> scooterList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooter = new ToBeAssignNextStopDetailResult();

        ToBeAssignSubmitDetailEnter detailEnter = enter.getList().get(0);
        Long id = detailEnter.getId();
        String rsn = detailEnter.getRsn();
        Long colorId = detailEnter.getColorId();

        // 此处调仓库管理接口,进行三层校验


        // 修改主表
        OpeCarDistribute model = new OpeCarDistribute();
        model.setId(id);
        model.setRsn(rsn);
        model.setColorId(colorId);
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        opeCarDistributeMapper.updateById(model);

        // node表node字段+1,flag标识改为已分配完
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        OpeCarDistributeNode node = new OpeCarDistributeNode();
        node.setNode(NodeEnum.FINISH.getCode());
        node.setFlag(FlagEnum.YES.getCode());
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        opeCarDistributeNodeMapper.update(node, nodeWrapper);

        // 查询主表
        OpeCarDistribute opeCarDistribute = opeCarDistributeMapper.selectById(id);
        scooter.setId(opeCarDistribute.getId());
        scooter.setSpecificatName(getSpecificatNameById(opeCarDistribute.getSpecificatTypeId()));
        scooter.setSeatNumber(opeCarDistribute.getSeatNumber());
        scooter.setVinCode(opeCarDistribute.getVinCode());
        scooter.setLicensePlate(opeCarDistribute.getLicensePlate());
        scooter.setRsn(opeCarDistribute.getRsn());
        Map<String, String> map = getColorNameAndValueById(opeCarDistribute.getColorId());
        scooter.setColorName(map.get("colorName"));
        scooter.setColorValue(map.get("colorValue"));

        scooterList.add(scooter);
        result.setList(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 查询客户走到哪个节点并带出数据
     */
    @Override
    public ToBeAssignNodeResult getNode(CustomerIdEnter enter) {
        logger.info("查询客户走到哪个节点并带出数据的入参是:[{}]", enter);
        ToBeAssignNodeResult result = new ToBeAssignNodeResult();
        List<ToBeAssignNextStopDetailResult> scooterList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooter = new ToBeAssignNextStopDetailResult();

        // 根据客户id查询node表
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getCustomerId());
        nodeWrapper.orderByDesc(OpeCarDistributeNode::getCreatedTime);
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(nodeWrapper);
        if (CollectionUtils.isEmpty(nodeList)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCarDistributeNode opeCarDistributeNode = nodeList.get(0);
        Integer node = opeCarDistributeNode.getNode();

        // 查询已分配的数据
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCarDistribute::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeCarDistribute::getCustomerId, enter.getCustomerId());
        wrapper.orderByDesc(OpeCarDistribute::getCreatedTime);
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            OpeCarDistribute model = list.get(0);
            scooter.setId(model.getId());
            scooter.setSpecificatName(getSpecificatNameById(model.getSpecificatTypeId()));
            scooter.setSeatNumber(model.getSeatNumber());
            scooter.setVinCode(model.getVinCode());
            scooter.setLicensePlate(model.getLicensePlate());
            scooter.setRsn(model.getRsn());
            Map<String, String> map = getColorNameAndValueById(model.getColorId());
            scooter.setColorName(map.get("colorName"));
            scooter.setColorValue(map.get("colorValue"));
        }

        scooterList.add(scooter);
        result.setNode(node);
        result.setList(scooterList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 根据型号id获取型号名称
     */
    public String getSpecificatNameById(Long specificatId) {
        if (null == specificatId) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.SPECIFICAT_ID_NOT_EMPTY.getMessage());
        }
        OpeSpecificatType specificatType = opeSpecificatTypeMapper.selectById(specificatId);
        if (null != specificatType) {
            String name = specificatType.getSpecificatName();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
    }

    /**
     * 根据颜色id获取颜色名称和色值
     */
    public Map<String, String> getColorNameAndValueById(Long colorId) {
        if (null == colorId) {
            throw new SesWebRosException(ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getCode(), ExceptionCodeEnums.COLOR_ID_NOT_EMPTY.getMessage());
        }
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        OpeColor color = opeColorMapper.selectById(colorId);
        if (null != color) {
            String colorName = color.getColorName();
            String colorValue = color.getColorValue();
            if (StringUtils.isNotBlank(colorName)) {
                map.put("colorName", colorName);
            }
            if (StringUtils.isNotBlank(colorValue)) {
                map.put("colorValue", colorValue);
            }
            return map;
        }
        throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
    }

    /**
     * 从指定数组中生成随机数
     */
    public String generateRangeRandom() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    /**
     * 生成VIN Code
     */
    public String generateVINCode(String specificatName, Integer seatNumber) {
        String msg = "VXS";
        StringBuffer result = new StringBuffer();

        // 世界工厂代码和车辆类型
        result.append(msg);
        result.append(ScooterTypeEnum.R2A.getCode());

        // 车型编号和座位数量
        String productType = ProductTypeEnum.showCode(specificatName);
        result.append(productType);
        result.append(seatNumber);

        // 指定随机数
        String random = generateRangeRandom();
        result.append(random);

        // 年份字母和工厂编号
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String value = YearEnum.showValue(year);
        result.append(value);
        result.append(FactoryEnum.AOGE.getCode());

        // 6位数递增序列号
        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 得到自增编号,从倒数第6位开始截取
            for (OpeCarDistribute o : list) {
                String vinCode = o.getVinCode();
                String sub = vinCode.substring(vinCode.length() - 6);
                codeList.add(Integer.valueOf(sub));
            }
            // 倒序排列
            codeList.sort(Comparator.reverseOrder());
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumIntegerDigits(6);
            nf.setMinimumIntegerDigits(6);
            String code = nf.format(new Double(codeList.get(0) + 1));
            result.append(code);
        } else {
            result.append("000001");
        }
        return result.toString();
    }

}
