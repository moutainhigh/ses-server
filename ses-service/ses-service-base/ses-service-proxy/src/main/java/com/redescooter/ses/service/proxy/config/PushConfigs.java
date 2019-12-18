package com.redescooter.ses.service.proxy.config;


import cn.jpush.api.JPushClient;
import com.redescooter.ses.api.common.vo.base.AppInfoBase;
import com.redescooter.ses.api.proxy.vo.jiguang.PushProxyEnter;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jg-push") //接收application.yml中的system下面的属性
@Data
public class PushConfigs {

    private List<AppInfoBase> listDriver = new ArrayList<>(); //接收listDriver里面的属性值

    private JPushClient jPushClientOne;
    //多个APP应用
    private Map<String, JPushClient> jPushClientMap = new HashMap<>();

    private AppInfoBase appInfoBase = null;

    /**
     * 推送客户端
     *
     * @return
     */
    @PostConstruct
    public void initJPushClient() {
        listDriver.forEach(appInfoBaseOne -> {
            JPushClient client = new JPushClient(appInfoBaseOne.getSecret(), appInfoBaseOne.getAppkey());
            jPushClientMap.put(appInfoBaseOne.getName(), client);
        });
    }

    /**
     * 获取推送客户端
     *
     * @return
     */
    public JPushClient getJPushClient(PushProxyEnter enter) {

        //实例化客户端
        jPushClientOne = jPushClientMap.get(new StringBuffer()
                .append(enter.getSystemId())
                .append("-")
                .append(enter.getAppId()).toString());

        return jPushClientOne;

    }

    /**
     * 选择不同应用APP
     * 区分开发和线上环境
     *
     * @return
     */
    public boolean getApns(PushProxyEnter enter) {
        return getAppIfo(enter)==null? Boolean.FALSE :getAppIfo(enter).getApns();
    }


    /**
     * 获取确定推送模板APP，实例化APP
     * SystemId-AppId 作为APP的唯一性客户端名称进行区别,当参数为null时，默认去最后一个APP
     *
     * @param enter
     * @return
     */
    public AppInfoBase getAppIfo(PushProxyEnter enter) {

        listDriver.forEach(app -> {
            if (enter != null) {
                if (app.getName().equals(new StringBuffer()
                        .append(enter.getSystemId())
                        .append("-")
                        .append(enter.getAppId()).toString())) {
                    appInfoBase = new AppInfoBase();
                    BeanUtils.copyProperties(app, appInfoBase);
                }
            } else {
                //不报错，保证有可以用APP应用
                appInfoBase = new AppInfoBase();
                BeanUtils.copyProperties(app, appInfoBase);
            }
        });

        return appInfoBase;
    }
}
