package com.redescooter.ses.web.ros.service.delete.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.mobile.c.service.UserProfileProService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerContactMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.web.ros.dao.delete.DeleteMapper;
import com.redescooter.ses.web.ros.dm.OpeCarDistribute;
import com.redescooter.ses.web.ros.dm.OpeCarDistributeNode;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRelation;
import com.redescooter.ses.web.ros.dm.OpeCodebaseRsn;
import com.redescooter.ses.web.ros.dm.OpeCodebaseVin;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeProductPrice;
import com.redescooter.ses.web.ros.dm.OpeProductPriceHistory;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeSpecificatGroup;
import com.redescooter.ses.web.ros.dm.OpeWmsScooterStock;
import com.redescooter.ses.web.ros.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRelationService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.web.ros.service.base.OpeCodebaseVinService;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceService;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatGroupService;
import com.redescooter.ses.web.ros.service.base.OpeWmsStockSerialNumberService;
import com.redescooter.ses.web.ros.service.delete.DeleteService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/9 11:46
 */
@Service
@Slf4j
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionCombinBomService opeProductionCombinBomService;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Autowired
    private OpeCodebaseVinService opeCodebaseVinService;

    @Autowired
    private OpeCodebaseRelationService opeCodebaseRelationService;

    @Autowired
    private OpeWmsStockSerialNumberService opeWmsStockSerialNumberService;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @DubboReference
    private UserProfileProService userProfileProService;

    @Autowired
    private OpeCustomerContactMapper opeCustomerContactMapper;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeCustomerInquiryBMapper opeCustomerInquiryBMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private DeleteMapper deleteMapper;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductPriceService opeProductPriceService;

    @Autowired
    private OpeProductPriceHistoryService opeProductPriceHistoryService;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeSpecificatGroupService opeSpecificatGroupService;

    @Autowired
    private OpeColorService opeColorService;

    @DubboReference
    private UserBaseService userBaseService;

    @DubboReference
    private ScooterService scooterService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteCustomer(IdEnter idEnter) {
        OpeCustomer opeCustomer = opeCustomerMapper.selectById(idEnter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        // ??????con_user_profile?????????????????????con_user_scooter??????????????????
        deleteConUser(opeCustomer.getEmail());

        // ??????ope_customer
        deleteMapper.deleteCustomer(idEnter.getId());

        // ??????ope_customer_contact
        LambdaQueryWrapper<OpeCustomerContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCustomerContact::getCustomerId, idEnter.getId());
        wrapper.last("limit 1");
        OpeCustomerContact contact = opeCustomerContactMapper.selectOne(wrapper);
        if (contact != null) {
            deleteMapper.deleteCustomerContact(opeCustomer.getId());
        }

        // ??????ope_customer_inquiry???ope_customer_inquiry_b
        LambdaQueryWrapper<OpeCustomerInquiry> inquiryWrapper = new LambdaQueryWrapper<>();
        inquiryWrapper.eq(OpeCustomerInquiry::getCustomerId, idEnter.getId());
        inquiryWrapper.last("limit 1");
        OpeCustomerInquiry inquiry = opeCustomerInquiryMapper.selectOne(inquiryWrapper);
        if (inquiry != null) {
            deleteMapper.deleteCustomerInquiry(opeCustomer.getId());

            LambdaQueryWrapper<OpeCustomerInquiryB> inquiryBWrapper = new LambdaQueryWrapper<>();
            inquiryBWrapper.eq(OpeCustomerInquiryB::getInquiryId, inquiry.getId());
            inquiryBWrapper.last("limit 1");
            OpeCustomerInquiryB inquiryB = opeCustomerInquiryBMapper.selectOne(inquiryBWrapper);
            if (inquiryB != null) {
                deleteMapper.deleteCustomerInquiryB(inquiry.getId());
            }
        }

        //??????platform????????????pla_user???pla_user_node???pla_user_password???pla_user_permission
        deletePlaUser(opeCustomer.getEmail());
        return new GeneralResult(idEnter.getRequestId());
    }

    private Map<String, Long> check(String rsn) {
        Map<String, Long> map = Maps.newHashMapWithExpectedSize(2);
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRelationType, 1);
        qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        qw.last("limit 1");
        OpeWmsStockSerialNumber serialNumber = opeWmsStockSerialNumberService.getOne(qw);
        if (null == serialNumber) {
            log.info("rsn????????????????????????????????????,rsn???:[{}]", rsn);
        } else {
            Long relationId = serialNumber.getRelationId();
            OpeWmsScooterStock stock = opeWmsScooterStockMapper.selectById(relationId);
            if (null != stock) {
                Long groupId = stock.getGroupId();
                Long colorId = stock.getColorId();
                OpeSpecificatGroup group = opeSpecificatGroupService.getById(groupId);
                OpeColor color = opeColorService.getById(colorId);
                if (null == group) {
                    throw new SesWebRosException(ExceptionCodeEnums.GROUP_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_NOT_EXIST.getMessage());
                }
                if (null == color) {
                    throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
                }
                map.put("groupId", groupId);
                map.put("colorId", colorId);
            }
        }
        return map;
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteScooter(StringEnter enter) {
        log.info("??????????????????,??????????????????:[{}]", enter.getKeyword());
        String tabletSn = enter.getKeyword();

        // ??????scooter???
        String rsn = scooterService.deleteScooter(tabletSn);
        log.info("?????????rsn???:[{}]", rsn);
        if (StringUtils.isBlank(rsn)) {
            log.info("?????????rsn??????,????????????????????????");
        }

        /*Map<String, Long> map = check(rsn);
        log.info("?????????map???:[{}]", map);
        if (null != map && map.size() > 0) {
            Long groupId = map.get("groupId");
            Long colorId = map.get("colorId");

            LambdaQueryWrapper<OpeWmsScooterStock> stockWrapper = new LambdaQueryWrapper<>();
            stockWrapper.eq(OpeWmsScooterStock::getDr, Constant.DR_FALSE);
            stockWrapper.eq(OpeWmsScooterStock::getGroupId, groupId);
            stockWrapper.eq(OpeWmsScooterStock::getColorId, colorId);
            List<OpeWmsScooterStock> stockList = opeWmsScooterStockMapper.selectList(stockWrapper);
            if (CollectionUtils.isNotEmpty(stockList)) {
                for (OpeWmsScooterStock stock : stockList) {
                    Integer ableStockQty = stock.getAbleStockQty();
                    Integer usedStockQty = stock.getUsedStockQty();
                    if (0 < ableStockQty && 0 < usedStockQty) {
                        stock.setAbleStockQty(stock.getAbleStockQty() - 1);
                        stock.setUsedStockQty(stock.getUsedStockQty() - 1);
                        opeWmsScooterStockMapper.updateById(stock);
                    }
                }
            }
        }*/

        // ??????ope_wms_stock_serial_number
        LambdaQueryWrapper<OpeWmsStockSerialNumber> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        qw.eq(OpeWmsStockSerialNumber::getRsn, rsn);
        List<OpeWmsStockSerialNumber> list = opeWmsStockSerialNumberService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeWmsStockSerialNumber item : list) {
                deleteMapper.deleteWmsStockSerialNumber(item.getId());
            }
        }

        // ??????????????????ope_wms_stock_serial_number?????????,??????????????????
        LambdaQueryWrapper<OpeWmsStockSerialNumber> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(OpeWmsStockSerialNumber::getDr, Constant.DR_FALSE);
        checkWrapper.eq(OpeWmsStockSerialNumber::getDef3, tabletSn).or().eq(OpeWmsStockSerialNumber::getSn, tabletSn);
        List<OpeWmsStockSerialNumber> numberList = opeWmsStockSerialNumberService.list(checkWrapper);
        if (CollectionUtils.isNotEmpty(numberList)) {
            for (OpeWmsStockSerialNumber number : numberList) {
                deleteMapper.deleteWmsStockSerialNumber(number.getId());
            }
        }

        // ??????ope_codebase_rsn
        LambdaQueryWrapper<OpeCodebaseRsn> rsnWrapper = new LambdaQueryWrapper<>();
        rsnWrapper.eq(OpeCodebaseRsn::getDr, Constant.DR_FALSE);
        rsnWrapper.eq(OpeCodebaseRsn::getRsn, rsn);
        rsnWrapper.eq(OpeCodebaseRsn::getStatus, 2);
        rsnWrapper.last("limit 1");
        OpeCodebaseRsn rsnModel = opeCodebaseRsnService.getOne(rsnWrapper);
        if (null != rsnModel) {
            rsnModel.setStatus(1);
            opeCodebaseRsnService.updateById(rsnModel);
            log.info("??????????????????rsn????????????");
        }

        LambdaQueryWrapper<OpeCarDistribute> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OpeCarDistribute::getDr, Constant.DR_FALSE);
        lqw.eq(OpeCarDistribute::getRsn, rsn);
        lqw.eq(OpeCarDistribute::getTabletSn, tabletSn);
        lqw.last("limit 1");
        OpeCarDistribute distribute = opeCarDistributeMapper.selectOne(lqw);
        if (null != distribute) {
            String vinCode = distribute.getVinCode();
            if (StringUtils.isNotBlank(vinCode)) {
                log.info("vin?????????,???:[{}]", vinCode);

                // ??????ope_codebase_vin
                LambdaQueryWrapper<OpeCodebaseVin> vinWrapper = new LambdaQueryWrapper<>();
                vinWrapper.eq(OpeCodebaseVin::getDr, Constant.DR_FALSE);
                vinWrapper.eq(OpeCodebaseVin::getVin, vinCode);
                vinWrapper.eq(OpeCodebaseVin::getStatus, 2);
                vinWrapper.last("limit 1");
                OpeCodebaseVin vinModel = opeCodebaseVinService.getOne(vinWrapper);
                if (null != vinModel) {
                    vinModel.setStatus(1);
                    opeCodebaseVinService.updateById(vinModel);
                    log.info("??????????????????vin????????????");
                }

                // ??????ope_codebase_relation
                LambdaQueryWrapper<OpeCodebaseRelation> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeCodebaseRelation::getRsn, rsn);
                wrapper.eq(OpeCodebaseRelation::getVin, vinCode);
                wrapper.last("limit 1");
                OpeCodebaseRelation relation = opeCodebaseRelationService.getOne(wrapper);
                if (null != relation) {
                    deleteMapper.deleteCodebaseRelation(relation.getId());
                }
            }
        }

        // ??????ope_car_distribute
        LambdaQueryWrapper<OpeCarDistribute> distributeWrapper = new LambdaQueryWrapper<>();
        distributeWrapper.eq(OpeCarDistribute::getTabletSn, tabletSn);
        distributeWrapper.last("limit 1");
        OpeCarDistribute model = opeCarDistributeMapper.selectOne(distributeWrapper);
        if (model != null) {
            Long customerId = model.getCustomerId();
            deleteMapper.deleteCarDistribute(model.getId());

            // ??????ope_car_distribute_node
            LambdaQueryWrapper<OpeCarDistributeNode> nodeWrapper = new LambdaQueryWrapper<>();
            nodeWrapper.eq(OpeCarDistributeNode::getCustomerId, customerId);
            nodeWrapper.last("limit 1");
            OpeCarDistributeNode node = opeCarDistributeNodeMapper.selectOne(nodeWrapper);
            if (node != null) {
                deleteMapper.deleteCarDistributeNode(node.getId());
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deletePart(IdEnter enter) {
        OpeProductionParts parts = opeProductionPartsService.getById(enter.getId());
        if (null != parts) {
            // ??????ope_production_parts
            deleteMapper.deletePart(parts.getId());

            // ??????ope_product_price
            LambdaQueryWrapper<OpeProductPrice> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeProductPrice::getDr, Constant.DR_FALSE);
            qw.eq(OpeProductPrice::getProductPriceType, 1);
            qw.eq(OpeProductPrice::getProductId, parts.getId());
            qw.last("limit 1");
            OpeProductPrice price = opeProductPriceService.getOne(qw);
            if (null != price) {
                deleteMapper.deletePrice(price.getId());

                // ??????ope_product_price_history
                LambdaQueryWrapper<OpeProductPriceHistory> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeProductPriceHistory::getDr, Constant.DR_FALSE);
                lqw.eq(OpeProductPriceHistory::getProductPriceId, price.getId());
                lqw.last("limit 1");
                OpeProductPriceHistory history = opeProductPriceHistoryService.getOne(lqw);
                if (null != history) {
                    deleteMapper.deletePriceHistory(history.getId());
                }
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult deleteDeposit(GeneralEnter enter) {
        int i = deleteMapper.deleteDeposit();
        return new GeneralResult(enter.getRequestId());
    }


    public void deletePlaUser(String email) {
        userBaseService.deletePlaUser(email);
    }

    public void deleteConUser(String email) {
        userProfileProService.deleteUserProfile(email);
    }

    /**
     * ????????????bom
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteScooterBom(IdEnter enter) {
        OpeProductionScooterBom bom = opeProductionScooterBomService.getById(enter.getId());
        if (null != bom) {
            log.info("??????????????????bom");
            deleteMapper.deleteScooterBom(enter.getId());
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ???????????????bom
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteCombinBom(IdEnter enter) {
        OpeProductionCombinBom bom = opeProductionCombinBomService.getById(enter.getId());
        if (null != bom) {
            log.info("?????????????????????bom");
            deleteMapper.deleteCombinBom(enter.getId());
        }
        return new GeneralResult(enter.getRequestId());
    }

}
