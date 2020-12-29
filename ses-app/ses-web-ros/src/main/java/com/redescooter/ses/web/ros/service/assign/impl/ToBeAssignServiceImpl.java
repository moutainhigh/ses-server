package com.redescooter.ses.web.ros.service.assign.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.vo.base.IdEnter;
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
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextDetailEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailScooterInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;
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
    public ToBeAssignDetailResult getToBeAssignDetail(IdEnter enter) {
        ToBeAssignDetailResult result = new ToBeAssignDetailResult();
        List<ToBeAssignDetailScooterInfoResult> scooterInfoList = Lists.newArrayList();
        ToBeAssignDetailScooterInfoResult scooterInfo = new ToBeAssignDetailScooterInfoResult();

        // 查询该客户在node表是否存在,如果不存在,添加一条数据
        LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
        nodeWrapper.eq(OpeCarDistributeNode::getDr, DelStatusEnum.VALID.getCode());
        nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, enter.getId());
        List<OpeCarDistributeNode> nodeList = opeCarDistributeNodeMapper.selectList(nodeWrapper);
        if (CollectionUtils.isEmpty(nodeList)) {
            OpeCarDistributeNode node = new OpeCarDistributeNode();
            node.setId(idAppService.getId(SequenceName.OPE_CAR_DISTRIBUTE_NODE));
            node.setDr(DelStatusEnum.VALID.getCode());
            node.setTenantId(enter.getTenantId());
            node.setUserId(enter.getUserId());
            node.setCustomerId(enter.getId());
            node.setNode(NodeEnum.VIN_CODE.getCode());
            node.setFlag(FlagEnum.NOT.getCode());
            node.setCreatedBy(enter.getUserId());
            node.setCreatedTime(new Date());
            opeCarDistributeNodeMapper.insert(node);
        }

        // 客户信息
        ToBeAssignDetailCustomerInfoResult customerInfo = opeCarDistributeExMapper.getCustomerInfo(enter.getId());
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
        opeCustomerInquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, enter.getId());
        List<OpeCustomerInquiry> list = opeCustomerInquiryMapper.selectList(opeCustomerInquiryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        OpeCustomerInquiry customerInquiry = list.get(0);
        Long productId = customerInquiry.getProductId();
        if (null == productId) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 拿着productId去ope_sale_scooter查询
        OpeSaleScooter opeSaleScooter = opeSaleScooterMapper.selectById(productId);
        if (null == opeSaleScooter) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Long specificatId = opeSaleScooter.getSpecificatId();
        Long colorId = opeSaleScooter.getColorId();

        // 拿着specificatId去ope_specificat_type查询
        OpeSpecificatType opeSpecificatType = opeSpecificatTypeMapper.selectById(specificatId);
        if (null == opeSpecificatType) {
            throw new SesWebRosException(ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.SPECIFICAT_TYPE_NOT_EXIST.getMessage());
        }
        String specificatName = opeSpecificatType.getSpecificatName();
        scooterInfo.setSpecificatId(specificatId);
        scooterInfo.setSpecificatName(specificatName);

        // 拿着colorId去ope_color查询
        if (null != colorId) {
            OpeColor opeColor = opeColorMapper.selectById(colorId);
            scooterInfo.setColorName(opeColor.getColorName());
            scooterInfo.setColorValue(opeColor.getColorValue());
        }

        scooterInfo.setTotalCount(customerInquiry.getScooterQuantity());
        scooterInfo.setToBeAssignCount(customerInquiry.getScooterQuantity());
        scooterInfo.setRequestId(enter.getRequestId());
        scooterInfoList.add(scooterInfo);

        result.setCustomerInfo(customerInfo);
        result.setScooterInfo(scooterInfoList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 填写完座位数点击下一步
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToBeAssignNextStopResult getSeatNext(ToBeAssignSeatNextEnter enter) {
        if (CollectionUtils.isEmpty(enter.getList())) {
            throw new SesWebRosException(ExceptionCodeEnums.SEAT_NOT_EMPTY.getCode(), ExceptionCodeEnums.SEAT_NOT_EMPTY.getMessage());
        }
        ToBeAssignNextStopResult result = new ToBeAssignNextStopResult();
        List<ToBeAssignNextStopDetailResult> scooterList = Lists.newArrayList();
        ToBeAssignNextStopDetailResult scooter = new ToBeAssignNextStopDetailResult();

        ToBeAssignSeatNextDetailEnter detailEnter = enter.getList().get(0);
        Long specificatId = detailEnter.getSpecificatId();
        String specificatName = detailEnter.getSpecificatName();
        Integer seatNumber = detailEnter.getSeatNumber();

        // 生成VIN Code
        String vinCode = generateVINCode(specificatName, seatNumber);

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
        scooter.setSpecificatName(opeSpecificatTypeMapper.selectById(opeCarDistribute.getSpecificatTypeId()).getSpecificatName());
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
    public ToBeAssignNextStopResult getLicensePlateNext(ToBeAssignLicensePlateNextEnter enter) {
        return null;
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
    public ToBeAssignNextStopResult submit(ToBeAssignSubmitEnter enter) {
        return null;
    }

    /**
     * 从指定数组中生成随机数
     */
    public String generateRangeRandom() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        int index = (int) (Math.random() * array.length);
        String random = array[index];
        return random;
    }

    /**
     * 生成VIN Code
     */
    public String generateVINCode(String productName, Integer seatNumber) {
        String msg = "VXS";
        StringBuffer result = new StringBuffer();

        // 世界工厂代码和车辆类型
        result.append(msg);
        result.append(ScooterTypeEnum.R2A.getCode());

        // 车型编号和座位数量
        String productType = ProductTypeEnum.showCode(productName);
        result.append(productType);
        result.append(seatNumber);

        // 指定随机数
        String random = generateRangeRandom();
        result.append(random);

        // 年份字母和工厂编号
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        result.append(year);
        result.append(FactoryEnum.AOGE.getCode());

        // 6位数递增序列号
        List<Integer> codeList = Lists.newArrayList();
        LambdaQueryWrapper<OpeCarDistribute> wrapper = new LambdaQueryWrapper<>();
        List<OpeCarDistribute> list = opeCarDistributeMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 得到自增编号,从第2位开始截取
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
