//package com.redescooter.ses.web.ros.service.restproductionorder.assembly.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
//import com.redescooter.ses.api.common.vo.base.GeneralResult;
//import com.redescooter.ses.api.common.vo.base.IdEnter;
//import com.redescooter.ses.api.common.vo.base.PageResult;
//import com.redescooter.ses.web.ros.dao.restproductionorder.ProductionAssemblyOrderServiceMapper;
//import com.redescooter.ses.web.ros.exception.ExceptionCode;
//import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
//import com.redescooter.ses.web.ros.exception.SesWebRosException;
//import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
//import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
//import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
//import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
//import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.ListByBussIdEnter;
//import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import sun.tools.jstat.Literal;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @ClassName:ProductionAssemblyOrderService
// * @description: ProductionAssemblyOrderService
// * @author: Alex
// * @Version：1.3
// * @create: 2020/11/12 11:53
// */
//public class ProductionAssemblyOrderServiceImpl implements ProductionAssemblyOrderService {
//
//    @Autowired
//    private ProductionAssemblyOrderServiceMapper productionAssemblyOrderServiceMapper;
//
//    @Autowired
//    private ProductionOrderTraceService productionOrderTraceService;
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/10 2:34 下午
//     * @Param: enter
//     * @Return: ProductionAssemblyOrderListResult
//     * @desc: 列表
//     * @param enter
//     */
//    @Override
//    public PageResult<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter) {
//       int count=productionAssemblyOrderServiceMapper.listCount(enter);
//       if (count==0){
//           return PageResult.createZeroRowResult(enter);
//       }
//       return PageResult.create(enter,count,productionAssemblyOrderServiceMapper.list(enter));
//    }
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/11 2:45 下午
//     * @Param: AssemblyOrderDetailEnter
//     * @Return: ProductionAssemblyOrderDetailResult
//     * @desc: 详情
//     * @param enter
//     */
//    @Override
//    public ProductionAssemblyOrderDetailResult detail(AssemblyOrderDetailEnter enter) {
//        ProductionAssemblyOrderDetailResult  detail=productionAssemblyOrderServiceMapper.detail(enter);
//        if (detail==null){
//            throw new SesWebRosException(ExceptionCodeEnums.ORDER_NOT_EXIST.getCode(),ExceptionCodeEnums.ORDER_NOT_EXIST.getMessage());
//        }
//        //detail.setProductList(this.detailProductList(enter));
//        detail.setOperatingDynamicsList(productionOrderTraceService.listByBussId(new ListByBussIdEnter(enter.getId(), OrderTypeEnums.FACTORY_PURCHAS.getValue())));
//        detail.setAssociatedOrderResultList(this.associatedOrder(enter));
//        return detail;
//    }
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/11 4:18 下午
//     * @Param: enter
//     * @Return: List<PurchasDetailProductListResult>
//     * @desc: 产品详情
//     * @param enter
//     */
//    @Override
//    public List<PurchasDetailProductListResult> detailProductList(AssemblyOrderDetailEnter enter) {
//        return null;
//    }
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/11 5:02 下午
//     * @Param: enter
//     * @Return: List<AssociatedOrderResult>
//     * @desc: 关联订单
//     * @param enter
//     */
//    @Override
//    public List<AssociatedOrderResult> associatedOrder(AssemblyOrderDetailEnter enter) {
//        return null;
//    }
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/11 2:47 下午
//     * @Param: enter
//     * @Return:
//     * @desc:
//     * @param enter
//     */
//    @Override
//    public List<PurchasDetailProductListResult> productPartDetail(AssemblyOrderDetailEnter enter) {
//        return null;
//    }
//
//    /**
//     * @Description
//     * @Author: alex
//     * @Date: 2020/11/11 3:10 下午
//     * @Param: enter
//     * @Return: GeneralResult
//     * @desc: 保存组装单
//     * @param enter
//     */
//    @Transactional
//    @Override
//    public GeneralResult save(SaveAssemblyOrderEnter enter) {
//        List<SaveAssemblyProductEnter> productEnterList = new ArrayList<>();
//        try {
//            productEnterList.addAll(JSON.parseArray(enter.getSt(),SaveAssemblyProductEnter.class));
//        }catch (Exception e) {
//            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
//        }
//        if (CollectionUtils.isEmpty(productEnterList)){
//            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
//        }
//
//        Long
//        return null;
//    }
//}
