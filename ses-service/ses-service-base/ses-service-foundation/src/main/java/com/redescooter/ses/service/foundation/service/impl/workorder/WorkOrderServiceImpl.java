package com.redescooter.ses.service.foundation.service.impl.workorder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.workorder.StatusFlowEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderDetailResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderLogResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderSaveOrUpdateEnter;
import com.redescooter.ses.api.common.vo.workorder.workOrderReplyEnter;
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
import com.redescooter.ses.tool.utils.SesStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassNameWorkOrderServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/12/4 15:43
 * @Version V1.0
 **/
@DubboService
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private PlaWorkOrderService plaWorkOrderService;

    @Autowired
    private PlaWorkOrderLogService plaWorkOrderLogService;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @return
     * @Author Aleks
     * @Description 工单列表
     * @Date 2020/12/4 14:07
     * @Param [dto]
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
     * @return
     * @Author Aleks
     * @Description 工单新增
     * @Date 2020/12/4 14:56
     * @Param [dto]
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult workOrderSave(WorkOrderSaveOrUpdateEnter enter) {
        SesStringUtils.objStringTrim(enter);
        // 校验字段的长度
        checkLength(enter);
        PlaWorkOrder workOrder = new PlaWorkOrder();
        BeanUtils.copyProperties(enter, workOrder);
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


    public void checkLength(WorkOrderSaveOrUpdateEnter enter) {
        if (Strings.isNullOrEmpty(enter.getTitle()) || enter.getTitle().length() > 100) {
            throw new FoundationException(ExceptionCodeEnums.TITLE_LENGTH_ERROR.getCode(), ExceptionCodeEnums.TITLE_LENGTH_ERROR.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getRemark()) && enter.getRemark().length() > 1000) {
            throw new FoundationException(ExceptionCodeEnums.CONTENT_LENGTH_ERROR.getCode(), ExceptionCodeEnums.CONTENT_LENGTH_ERROR.getMessage());
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description 工单编号规则 6位的递增流水 从000001开始
     * @Date 2020/12/4 18:04
     * @Param []
     **/
    public String createWorkOrderNo() {
        String workOrderNo = "";
        // 判断有没有工单  没有的话 直接返回“000001”  有的话 需要进行自动递增
        QueryWrapper<PlaWorkOrder> qw = new QueryWrapper<>();
        qw.eq(PlaWorkOrder.COL_DR, 0);
        qw.orderByDesc(PlaWorkOrder.COL_ORDER_NO);
        qw.last("limit 1");
        PlaWorkOrder workOrder = plaWorkOrderService.getOne(qw);
        if (workOrder == null) {
            workOrderNo = "000001";
        } else {
            workOrderNo = OrderNoGenerateUtil.createWorkOrderNo(workOrder.getOrderNo());
        }
        return workOrderNo;
    }

    /**
     * @return
     * @Author Aleks
     * @Description 工单编辑
     * @Date 2020/12/4 15:46
     * @Param [dto]
     **/
    @Override
    public GeneralResult workOrderUpdate(WorkOrderSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description 工单删除
     * @Date 2020/12/4 15:46
     * @Param [dto]
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult workOrderDelete(IdEnter enter) {
        plaWorkOrderService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description 工单详情
     * @Date 2020/12/4 18:16
     * @Param [dto]
     **/
    @Override
    public WorkOrderDetailResult workOrderDetail(IdEnter enter) {
        WorkOrderDetailResult result = new WorkOrderDetailResult();
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        result = workOrderMapper.workOrderDetail(enter);
        // 找到工单的聊天记录
        List<WorkOrderLogResult> logs = workOrderMapper.workOrderLogs(enter.getId());
        if (CollectionUtils.isNotEmpty(logs)) {
            for (WorkOrderLogResult log : logs) {
                log.setCreatedByName(workOrder.getContactEmail());
            }
        }
        result.setLogs(logs);
        return result;
    }


    /**
     * @return
     * @Author Aleks
     * @Description 工单状态流转(只能往当前状态的后面的状态跳 随便跳)
     * @Date 2020/12/4 18:16
     * @Param [enter]
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult workOrderStatusFlow(StatusFlowEnter enter) {
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        workOrder.setWorkOrderStatus(enter.getWorkOrderStatus());
        plaWorkOrderService.updateById(workOrder);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description 工单回复
     * @Date 2020/12/4 18:16
     * @Param [enter]
     **/
    @Override
    public GeneralResult workOrderReply(workOrderReplyEnter enter) {
        PlaWorkOrder workOrder = plaWorkOrderService.getById(enter.getId());
        if (workOrder == null) {
            throw new FoundationException(ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.GROUP_IS_NOT_EXIST.getMessage());
        }
        if (Strings.isNullOrEmpty(enter.getRemark()) || enter.getRemark().length() > 1000) {
            throw new FoundationException(ExceptionCodeEnums.CONTENT_LENGTH_ERROR.getCode(), ExceptionCodeEnums.CONTENT_LENGTH_ERROR.getMessage());
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
        workOrderLog.setDef1(enter.getUrls());
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
        if (!Strings.isNullOrEmpty(enter.getUrls())) {
            sendEnter.setImgList(Arrays.asList(enter.getUrls().split(",")));
        }
        try {
            mailMultiTaskService.contactUsReplyMessageEmail(sendEnter);
        } catch (Exception e) {

        }

        return new GeneralResult(enter.getRequestId());
    }

}
