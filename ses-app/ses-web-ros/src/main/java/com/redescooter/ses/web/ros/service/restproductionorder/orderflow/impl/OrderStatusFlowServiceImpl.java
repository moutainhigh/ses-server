package com.redescooter.ses.web.ros.service.restproductionorder.orderflow.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.restproductionorder.OrderStatusFlowServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.service.base.OpeOrderStatusFlowService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.restproductionorder.orderflow.OrderStatusFlowService;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.orderflow.OrderStatusFlowResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  @author: alex
 *  @Date: 2020/10/27 19:00
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Service
@Slf4j
public class OrderStatusFlowServiceImpl implements OrderStatusFlowService {

    @Autowired
    private OpeOrderStatusFlowService opeOrderStatusFlowService;

    @Autowired
    private OrderStatusFlowServiceMapper orderStatusFlowServiceMapper;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:05
     * @Param: enter
     * @Return: OrderStatusFlowResult
     * @desc: 订单节点
     * @param enter
     */
    @Override
    public List<OrderStatusFlowResult> listBybussId(IdEnter enter) {
        List<OrderStatusFlowResult> opeOrderStatusFlowList = orderStatusFlowServiceMapper.listBybussId(enter);

        if (CollectionUtils.isNotEmpty(opeOrderStatusFlowList)) {
            //创建人
            List<OpeSysStaff> createSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opeOrderStatusFlowList.stream().map(OrderStatusFlowResult::getCreateById).collect(Collectors.toSet())));

            //更新人
            List<OpeSysStaff> updateSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opeOrderStatusFlowList.stream().map(OrderStatusFlowResult::getCreateById).collect(Collectors.toSet())));

            if (CollectionUtils.isNotEmpty(createSysStaffList)) {
                opeOrderStatusFlowList.forEach(item -> {
                    createSysStaffList.forEach(staff -> {
                        item.setCreateById(staff.getId());
                        item.setCreateByFirstName(staff.getFirstName());
                        item.setCreateByLastName(staff.getLastName());
                    });
                });
            }
            if (CollectionUtils.isNotEmpty(updateSysStaffList)) {
                opeOrderStatusFlowList.forEach(item -> {
                    updateSysStaffList.forEach(staff -> {
                        item.setUpdateById(staff.getId());
                        item.setUpdateByFirstName(staff.getFirstName());
                        item.setUpdateByLastName(staff.getLastName());
                    });
                });
            }
        }
        return opeOrderStatusFlowList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:06
     * @Param:
     * @Return: OrderStatusFlowResult
     * @desc: 查询所有节点
     */
    @Override
    public List<OrderStatusFlowResult> list() {
        List<OrderStatusFlowResult> opeOrderStatusFlowList = orderStatusFlowServiceMapper.listBybussId(null);

        if (CollectionUtils.isNotEmpty(opeOrderStatusFlowList)) {
            //创建人
            List<OpeSysStaff> createSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opeOrderStatusFlowList.stream().map(OrderStatusFlowResult::getCreateById).collect(Collectors.toSet())));

            //更新人
            List<OpeSysStaff> updateSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opeOrderStatusFlowList.stream().map(OrderStatusFlowResult::getCreateById).collect(Collectors.toSet())));

            if (CollectionUtils.isNotEmpty(createSysStaffList)) {
                opeOrderStatusFlowList.forEach(item -> {
                    createSysStaffList.forEach(staff -> {
                        item.setCreateById(staff.getId());
                        item.setCreateByFirstName(staff.getFirstName());
                        item.setCreateByLastName(staff.getLastName());
                    });
                });
            }
            if (CollectionUtils.isNotEmpty(updateSysStaffList)) {
                opeOrderStatusFlowList.forEach(item -> {
                    updateSysStaffList.forEach(staff -> {
                        item.setUpdateById(staff.getId());
                        item.setUpdateByFirstName(staff.getFirstName());
                        item.setUpdateByLastName(staff.getLastName());
                    });
                });
            }
        }
        return opeOrderStatusFlowList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:07
     * @Param: enter
     * @Return: 订单节点详情
     * @desc: 订单节点详情
     * @param enter
     */
    @Override
    public OrderStatusFlowResult detail(IdEnter enter) {
        OrderStatusFlowResult opeOrderStatusFlow = orderStatusFlowServiceMapper.detail(enter);

        if (opeOrderStatusFlow != null) {
            //创建人
            OpeSysStaff createSysStaff = opeSysStaffService.getById(new LambdaQueryWrapper<OpeSysStaff>().eq(OpeSysStaff::getId, opeOrderStatusFlow.getCreateById()));
            //更新人
            OpeSysStaff updateSysStaff = opeSysStaffService.getById(new LambdaQueryWrapper<OpeSysStaff>().eq(OpeSysStaff::getId, opeOrderStatusFlow.getUpdateById()));
            opeOrderStatusFlow.setCreateById(createSysStaff.getId());
            opeOrderStatusFlow.setCreateByFirstName(createSysStaff.getFirstName());
            opeOrderStatusFlow.setCreateByLastName(createSysStaff.getLastName());
            opeOrderStatusFlow.setUpdateById(updateSysStaff.getId());
            opeOrderStatusFlow.setUpdateByFirstName(updateSysStaff.getFirstName());
            opeOrderStatusFlow.setUpdateByLastName(updateSysStaff.getLastName());
        }
        return opeOrderStatusFlow;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 19:10
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存节点
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(OrderStatusFlowEnter enter) {
        OpeOrderStatusFlow opeOrderStatusFlow = new OpeOrderStatusFlow();
        BeanUtils.copyProperties(enter, opeOrderStatusFlow);
        if (enter.getId() == null || enter.getId() == 0) {
            opeOrderStatusFlow.setId(idAppService.getId(SequenceName.OPE_ORDER_STATUS_FLOW));
            opeOrderStatusFlow.setDr(0);
            opeOrderStatusFlow.setCreatedBy(enter.getUserId());
            opeOrderStatusFlow.setCreatedTime(new Date());
        }
        opeOrderStatusFlow.setCreatedBy(enter.getUserId());
        opeOrderStatusFlow.setCreatedTime(new Date());
        opeOrderStatusFlow.setUpdatedBy(enter.getUserId());
        opeOrderStatusFlow.setUpdatedTime(new Date());
        opeOrderStatusFlowService.saveOrUpdate(opeOrderStatusFlow);
        return new GeneralResult(enter.getRequestId());
    }
}
