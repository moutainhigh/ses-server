package com.redescooter.ses.web.delivery.controller.common;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.MessageService;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;
import com.redescooter.ses.api.foundation.vo.message.ReadMessageEnter;
import com.redescooter.ses.api.foundation.vo.message.UnReadMessageCountResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName:MessageController
 * @description: MessageController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/11 10:43
 */
@Api(tags = {"公共消息"})
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
    @ApiOperation(value = "最近的消息", response = MessageResult.class)
    public Response<PageResult<MessageResult>> recentMessages(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(messageService.recentMessages(enter));
    }

    @PostMapping(value = "/read")
    @ApiOperation(value = "读取消息", response = GeneralResult.class)
    public Response<GeneralResult> readMessage(@ModelAttribute @ApiParam("请求参数") ReadMessageEnter enter) {
        return new Response<>(messageService.readMessage(enter));
    }

    @PostMapping(value = "/unReadCount")
    @ApiOperation(value = "未读消息统计", response = UnReadMessageCountResult.class)
    public Response<UnReadMessageCountResult> unReadMessages(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(messageService.unReadMessages(enter));
    }
}
