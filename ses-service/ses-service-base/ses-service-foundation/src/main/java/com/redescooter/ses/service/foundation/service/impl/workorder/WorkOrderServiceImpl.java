package com.redescooter.ses.service.foundation.service.impl.workorder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.workorder.*;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.workorder.WorkOrderService;
import com.redescooter.ses.api.foundation.vo.mail.MailContactUsMessageEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.WorkOrderMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrder;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrderLog;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaWorkOrderLogService;
import com.redescooter.ses.service.foundation.service.base.PlaWorkOrderService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.OrderNoGenerateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @ClassNameWorkOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/12/4 15:43
 * @Version V1.0
 **/
@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private PlaWorkOrderService plaWorkOrderService;

    @Autowired
    private PlaWorkOrderLogService plaWorkOrderLogService;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private IdAppService idAppService;

    /**
     * @Author Aleks
     * @Description  工单列表
     * @Date  2020/12/4 14:07
     * @Param [dto]
     * @return
     **/
    @Override
    public PageResult<WorkOrderListResult> workOrderList(WorkOrderListEnter enter) {
        int count = workOrderMapper.workOrderListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<WorkOrderListResult> list = workOrderMapper.workOrderList(enter);
        return PageResult.create(enter, count, list);
    }


    /**
     * @Author Aleks
     * @Description  工单新增
     * @Date  2020/12/4 14:56
     * @Param [dto]
     * @return
     **/
    @Override
    public GeneralResult workOrderSave(WorkOrderSaveOrUpdateEnter enter) {
        PlaWorkOrder workOrder = new PlaWorkOrder();
        BeanUtils.copyProperties(enter,workOrder);
        workOrder.setId(idAppService.getId(SequenceName.PLA_WORK_ORDER));
        workOrder.setOrderNo(createWorkOrderNo());
        workOrder.setWorkOrderStatus(1);
        workOrder.setCreatedBy(enter.getUserId());
        workOrder.setCreatedTime(new Date());
        workOrder.setUpdatedBy(enter.getUserId());
        workOrder.setUpdatedTime(new Date());
        plaWorkOrderService.saveOrUpdate(workOrder);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Author Aleks
     * @Description  工单编号规则 6位的递增流水 从000001开始
     * @Date  2020/12/4 18:04
     * @Param []
     * @return
     **/
    public String createWorkOrderNo(){
        String workOrderNo = "";
        // 判断有没有工单  没有的话 直接返回“000001”  有的话 需要进行自动递增
        QueryWrapper<PlaWorkOrder> qw = new QueryWrapper<>();
        qw.eq(PlaWorkOrder.COL_DR,0);
        qw.orderByDesc(PlaWorkOrder.COL_ORDER_NO);
        qw.last("limit 1");
        PlaWorkOrder workOrder = plaWorkOrderService.getOne(qw);
        if (workOrder == null){
            workOrderNo = "000001";
        }else {
            workOrderNo = OrderNoGenerateUtil.createWorkOrderNo(workOrder.getOrderNo());
        }
        return  workOrderNo;
    }

    /**
     * @Author Aleks
     * @Description  工单编辑
     * @Date  2020/12/4 15:46
     * @Param [dto]
     * @return
     **/
    @Override
    public GeneralResult workOrderUpdate(WorkOrderSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Author Aleks
     * @Description  工单删除
     * @Date  2020/12/4 15:46
     * @Param [dto]
     * @return
     **/
    @Override
    public GeneralResult workOrderDelete(IdEnter enter) {
        plaWorkOrderService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Author Aleks
     * @Description  工单详情
     * @Date  2020/12/4 18:16
     * @Param [dto]
     * @return
     **/
    @Override
    public WorkOrderDetailResult workOrderDetail(IdEnter enter) {
        WorkOrderDetailResult result = new WorkOrderDetailResult();
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null){
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        result = workOrderMapper.workOrderDetail(enter);
        // 找到工单的聊天记录
        List<WorkOrderLogResult> logs = workOrderMapper.workOrderLogs(enter.getId());
        if(CollectionUtils.isNotEmpty(logs)){
            for (WorkOrderLogResult log : logs) {
                log.setCreatedByName(workOrder.getContactEmail());
            }
        }
        result.setLogs(logs);
        return result;
    }


    /**
     * @Author Aleks
     * @Description  工单状态流转(只能往当前状态的后面的状态跳  随便跳)
     * @Date  2020/12/4 18:16
     * @Param [enter]
     * @return
     **/
    @Override
    public GeneralResult workOrderStatusFlow(StatusFlowEnter enter) {
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null){
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        workOrder.setWorkOrderStatus(enter.getWorkOrderStatus());
        plaWorkOrderService.updateById(workOrder);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @Author Aleks
     * @Description  工单回复
     * @Date  2020/12/4 18:16
     * @Param [enter]
     * @return
     **/
    @Override
    public GeneralResult workOrderReply(workOrderReplyEnter enter) {
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null){
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        PlaWorkOrderLog workOrderLog = new PlaWorkOrderLog();
        workOrderLog.setCreatedBy(enter.getUserId());
        workOrderLog.setCreatedTime(new Date());
        workOrderLog.setUpdatedBy(enter.getUserId());
        workOrderLog.setUpdatedTime(new Date());
        workOrderLog.setWorkOrderId(enter.getId());
        workOrderLog.setMessageType(enter.getMessageType());
        workOrderLog.setRemark(enter.getRemark());
        workOrderLog.setId(idAppService.getId(SequenceName.PLA_WORK_ORDER_LOG));
        workOrderLog.setDef1(enter.getIsPhotos()?enter.getRemark():null);
        plaWorkOrderLogService.saveOrUpdate(workOrderLog);
        // 邮件暂时不发
        MailContactUsMessageEnter sendEnter = new MailContactUsMessageEnter();
        sendEnter.setMessage(enter.getRemark());
        sendEnter.setName(workOrder.getContactEmail());
        sendEnter.setEvent(MailTemplateEventEnums.ROS_CONTACTUS_REPLY_MESSAGE.getEvent());
        sendEnter.setMailSystemId(SystemIDEnums.REDE_SES.getSystemId());
        sendEnter.setMailAppId(AppIDEnums.SES_ROS.getValue());
        sendEnter.setToMail(workOrder.getContactEmail());
        sendEnter.setUserRequestId(enter.getRequestId());
        sendEnter.setToUserId(enter.getUserId());
        mailMultiTaskService.contactUsReplyMessageEmail(sendEnter);

        return new GeneralResult(enter.getRequestId());
    }

}
