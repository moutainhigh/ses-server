package com.redescooter.ses.service.foundation.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.mesage.MesageTypeEnum;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PlatformTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.jiguang.PushJgResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.JpushUserService;
import com.redescooter.ses.api.foundation.service.MessageService;
import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.message.JpushUserResult;
import com.redescooter.ses.api.foundation.vo.message.MessagePushEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageSaveEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.proxy.service.PushProxyService;
import com.redescooter.ses.api.proxy.vo.jiguang.PushProxyEnter;
import com.redescooter.ses.service.common.i18n.I18nServiceMessage;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaPushResultMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaJpushUserService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * description: PushServiceImpl 根据业务不同进行调用
 * author: jerry.li
 * create: 2019-05-20 11:27
 */

@Slf4j
@DubboService
public class PushServiceImpl implements PushService {

    /**
     * 一次推送最大数量 (极光限制1000)
     */
    private static final int max_size = 800;

    @Autowired
    private JpushUserService jpushUserService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private PlaJpushUserService plaJpushUserService;
    //调用极光底层推送
    @DubboReference
    private PushProxyService pushProxyService;
    @Autowired
    private I18nServiceMessage i18nServiceMessage;
    @DubboReference
    private IdAppService idSerService;
    @Autowired
    private PlaPushResultMapper pushResultMapper;
    @Autowired
    private UserBaseService userBaseService;


    /**
     * 推送全部, 不支持附加信息
     *
     * @param enter
     * @return
     * @author jerry
     */
    @Override
    public List<PushJgResult> pushAll(MessagePushEnter enter) {

        PushProxyEnter pushProxyEnter = new PushProxyEnter();
        BeanUtils.copyProperties(enter, pushProxyEnter);

        PushJgResult pushJgResult = pushProxyService.pushAll(pushProxyEnter);

        List<PushJgResult> result = new ArrayList<>();
        result.add(pushJgResult);
        return result;
    }


    @Override
    public List<PushJgResult> push(MessagePushEnter enter) {
        List<PushJgResult> result = new ArrayList<>();
        //设备注册ID
        List<String> registidsList = null;

        //通过
        String[] userId = enter.getUserIds();
        //查询用户与极光关系
        List<JpushUserResult> jpushUserlist = jpushUserService.queryJGUserInfo(userId);

        if (null == jpushUserlist) {
            return result;
        } else {
            //由于每个用户平台的不确定性，故所有从
            for (JpushUserResult jpushUserResult : jpushUserlist) {
                registidsList = new ArrayList<>();
                registidsList.add(jpushUserResult.getRegistrationId());
                String[] registids = registidsList.toArray(new String[registidsList.size()]);

                PushProxyEnter pushProxyEnter = new PushProxyEnter();
                BeanUtils.copyProperties(enter, pushProxyEnter);
                //设置推送平台,添加没有平台类型时，设置暂无平台
                pushProxyEnter.setType(jpushUserResult.getPlatformType() == null ? PlatformTypeEnums.NONE.getValue() : jpushUserResult.getPlatformType());
                pushProxyEnter.setAppId(enter.getAppId());
                pushProxyEnter.setSystemId(enter.getSystemId());
                // 剔除无效registed
                registids = checkRegistids(registids);

                while (registids.length > max_size) {

                    //发送最大值之前的数据
                    String[] oloregistids = Arrays.copyOfRange(registids, 0, max_size);
                    // 每次推送max_size个
                    pushProxyEnter.setRegistids(oloregistids);

                    result.add(pushProxyService.push(pushProxyEnter));

                    //等待下次的推送
                    registids = Arrays.copyOfRange(registids, max_size, registids.length);
                }
                //次数限制
                pushProxyEnter.setRegistids(registids);
                result.add(pushProxyService.push(pushProxyEnter));
            }
            return result;
        }

    }

    /**
     * 消息定时推送到PC
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public List<PushJgResult> pushPC(MessagePushEnter enter) {
        // pc 端的消息推送 实质就是消息保存 消息已经在前面统一保存过了
        return null;
    }

    /**
     * 剔除无效registed
     *
     * @param registids
     * @return
     * @author jerry
     */
    private String[] checkRegistids(String[] registids) {
        List<String> regList = new ArrayList<String>(registids.length);
        for (String registid : registids) {
            if (registid != null && !"".equals(registid.trim())) {
                regList.add(registid);
            }
        }
        return regList.toArray(new String[0]);
    }


    /**
     * 推送消息
     *
     * @param str
     */
    @GlobalTransactional(rollbackFor = Exception.class)  
    @Override
    public void pushMessage(String str) {
        Map map = JSON.parseObject(str, Map.class);
        // 参数校验
        checkParameter(map);

        Map extras = new HashMap();
        extras.put("BizType", map.get("BizType"));
        extras.put("Id", map.get("Id"));
        extras.put("Type", map.get("Type"));
        extras.put("args", map.get("args"));
        extras.put("title", map.get("title"));
        extras.put("content", map.get("content"));
        extras.put("messagePriority", StringUtils.isEmpty(map.get("messagePriority").toString()) == true ? MessagePriorityEnums.NONE_REMIND.getValue() : map.get("messagePriority"));
        extras.put("mesageType", StringUtils.isEmpty(map.get("mesageType").toString()) == true ? MesageTypeEnum.NONE.getValue() : map.get("mesageType"));

        extras.put("bussinessStatus", map.get("bussinessStatus"));
        if (map.containsKey("TenantId")) {
            extras.put("TenantId", map.get("TenantId"));
        }

        GeneralEnter enter = JSON.parseObject(map.get("generalEnter").toString(), GeneralEnter.class);
        Locale locale = new Locale(enter.getLanguage(), enter.getCountry());

        String[] args = map.get("args").toString().split(",");
        String[] userIds = map.get("userIds").toString().split(",");

        MessagePushEnter push = MessagePushEnter.builder()
                .alert(i18nServiceMessage.getMessage(extras.get("content").toString(), args, locale))
                .title(i18nServiceMessage.getMessage(extras.get("title").toString(), args, locale))
                .userIds(userIds)
                .extras(extras)
                .createUser(Long.valueOf(map.get("createUser").toString()))
                .build();

        BeanUtils.copyProperties(enter, push);
        push.setAppId(map.get("appId").toString());
        push.setSystemId(map.get("systemId").toString());

        // 将推送的消息保存到数据库
        MessageSaveEnter messageSaveEnter = buildMessageSaveEnter(map, extras, push);
        messageService.save(messageSaveEnter);

        // 进行极光推送
        List<PushJgResult> pushJgResultList = null;

        //查询消息接受者客户端 （android-IOS，默认 android）
        PlaJpushUser plaJpushUser = plaJpushUserService.getOne(new LambdaQueryWrapper<PlaJpushUser>().eq(PlaJpushUser::getUserId, Long.valueOf(map.get("userIds").toString())));
        if (plaJpushUser != null && StringUtils.isNotBlank(plaJpushUser.getRegistrationId())) {
            map.put("pushType", plaJpushUser.getPlatformType());
        }
        if (Objects.isNull(map.get("pushType"))) {
            throw new FoundationException(ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getMessage());
        }

        switch (map.get("pushType").toString()) {
            case "ios":
                pushJgResultList = push(push);
                break;
            case "android":
                pushJgResultList = push(push);
                break;
            case "all":
                pushJgResultList = pushAll(push);
                break;
            case "pc":
                pushJgResultList = pushPC(push);
                break;
            default:
                log.info("极光未进行消息推送.");
                break;
        }
        // 极光推送结束将 推送结果保存到 数据库
        List<PlaPushResult> pushResults = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pushResults)) {
            pushJgResultList.forEach(item -> {
                PlaPushResult pushResult = new PlaPushResult();
                pushResult.setId(idSerService.getId(SequenceName.PLA_PUSHR_RESULT));
                pushResult.setMsgId(item.getMsg_id());
                pushResult.setErrorCode(item.getCode());
                pushResult.setSendNo(item.getSendno());
                pushResult.setErrorMessage(item.getMessage());
                pushResult.setStatusCode(item.getStatusCode());
                pushResult.setCreatedBy(enter.getUserId());
                pushResult.setCreatedTime(new Date());
                pushResults.add(pushResult);
            });
            pushResultMapper.batchInsert(pushResults);
        }

    }

    private MessageSaveEnter buildMessageSaveEnter(Map map, Map extras, MessagePushEnter push) {
        GeneralEnter generalEnter = JSON.parseObject(map.get("generalEnter").toString(), GeneralEnter.class);
        MessageSaveEnter messageSaveEnter = new MessageSaveEnter();
        BeanUtils.copyProperties(generalEnter, messageSaveEnter);
        messageSaveEnter.setBizId((String) extras.get("Id"));
        messageSaveEnter.setBizType((String) extras.get("BizType"));
        messageSaveEnter.setTitle((String) extras.get("title"));
        messageSaveEnter.setContent((String) extras.get("content"));
        messageSaveEnter.setBussinessStatus((String) extras.get("bussinessStatus"));
        messageSaveEnter.setMemo(StringUtils.isNotBlank(map.get("args").toString()) == true ? map.get("args").toString() : null);

        messageSaveEnter.setMessageType((String) extras.get("mesageType"));
        messageSaveEnter.setMessagePriority((String) extras.get("messagePriority"));


        if (StringUtils.equals(push.getAppId(), AppIDEnums.SAAS_REPAIR_WEB.getAppId())) {

            messageSaveEnter.setUserId(Long.parseLong(push.getUserIds()[0]));
            messageSaveEnter.setSystemId(push.getSystemId());
            messageSaveEnter.setAppId(push.getAppId());
            messageSaveEnter.setTenantId(Long.valueOf(push.getExtras().get("TenantId")));
        } else {
            Long userIds = Long.valueOf(map.get("userIds").toString());
            GeneralEnter belongUser = new GeneralEnter();
            belongUser.setUserId(userIds);
            QueryUserResult queryUserResult = userBaseService.queryUserById(belongUser);
            messageSaveEnter.setUserId(queryUserResult.getId());
            messageSaveEnter.setSystemId(queryUserResult.getSystemId());
            messageSaveEnter.setAppId(queryUserResult.getAppId());
            messageSaveEnter.setTenantId(queryUserResult.getTenantId());
        }
        return messageSaveEnter;
    }

    private void checkParameter(Map map) {
        // 验证传输字段必须有值
        map.keySet().forEach(item -> {
            if (StringUtils.isBlank(map.get(item).toString())) {
                log.info("========================={}==数据非法======{}==============", item, map.get(item));
                log.info("=========================调用失败==============");
                throw new FoundationException(ExceptionCodeEnums.MESSAGE_ABNORMAL_PARAMETER.getCode(), ExceptionCodeEnums.MESSAGE_ABNORMAL_PARAMETER.getMessage());
            }
        });

        //验证几个必传字段
        if (!map.containsKey("userIds")
                | !map.containsKey("generalEnter")
                || !map.containsKey("pushType")
                || !map.containsKey("Id")
                || !map.containsKey("title")
                || !map.containsKey("content")) {
            log.info("=========================调用失败==============");
            throw new FoundationException(ExceptionCodeEnums.MESSAGE_ABNORMAL_PARAMETER.getCode(), ExceptionCodeEnums.MESSAGE_ABNORMAL_PARAMETER.getMessage());
        }
    }
}
