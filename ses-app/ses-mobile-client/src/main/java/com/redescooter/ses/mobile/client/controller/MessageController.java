package com.redescooter.ses.mobile.client.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MessageService;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;
import com.redescooter.ses.api.foundation.vo.message.ReadMessageEnter;
import com.redescooter.ses.api.foundation.vo.message.UnReadMessageCountResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 *  @author: alex
 *  @Date: 2020/2/11 14:05
 *  @version：V 1.2
 *  @Description: 消息模块
 */
@Slf4j
@Api(tags = {"消息模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/common/message", method = RequestMethod.POST)
public class MessageController {
    @Reference
    private MessageService messageService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "消息列表", response = MessageResult.class)
    public Response<PageResult<MessageResult>> list(@ModelAttribute @ApiParam("请求参数") MessageListEnter enter) {
        return new Response<>(messageService.messageList(enter));
    }

    @PostMapping(value = "/recent")
    @ApiOperation(value = "最近五条消息", response = MessageResult.class)
    public Response<PageResult<MessageResult>> recentMessages(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(messageService.recentMessages(enter));
    }

    @PostMapping(value = "/read")
    @ApiOperation(value = "读取消息", response = GeneralResult.class)
    public Response<GeneralResult> readMessage(@ModelAttribute @ApiParam("请求参数") ReadMessageEnter enter) {
        return new Response<>(messageService.readMessage(enter));
    }

    @PostMapping(value = "/unRead")
    @ApiOperation(value = "未读消息，只会返回最近的一条", response = UnReadMessageCountResult.class)
    public Response<UnReadMessageCountResult> unReadMessages(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(messageService.unReadMessages(enter));
    }
}
