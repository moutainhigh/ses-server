package com.redescooter.ses.api.proxy.service;

import com.redescooter.ses.api.common.vo.jiguang.PushJgResult;
import com.redescooter.ses.api.proxy.vo.jiguang.PushProxyEnter;

/**
 * description: PushProxyService 极光推送底层实现
 * author jerry.li
 * create: 2019-05-13 16:37
 */

public interface PushProxyService {


    /**
     * 广播 (所有平台，所有设备, 不支持附加信息)
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    PushJgResult pushAll(PushProxyEnter enter);

    /**
     * 指定或者多个用户进行推送 (一次推送最多 1000 个)
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    PushJgResult push(PushProxyEnter enter);

    /**
     * android通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter    推送内容
     * @param pushAims 别名推送
     * @return
     * @author jerry
     */
    PushJgResult pushAndroidOfAlias(PushProxyEnter enter, String... pushAims);

    /**
     * ios通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter     推送内容
     * @param registids 推送id
     * @return
     * @author jerry
     */
    PushJgResult pushIos(PushProxyEnter enter, String... registids);

    /**
     * IOS推送全推
     *
     * @param enter
     * @return
     */
    PushJgResult pushIosAll(PushProxyEnter enter);

    /**
     * android通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter     推送内容
     * @param registids 推送id：registration_id
     * @return
     * @author jerry
     */
    PushJgResult pushAndroid(PushProxyEnter enter, String... registids);

    /**
     * 安卓推送全推
     *
     * @param enter
     * @return
     */
    PushJgResult pushAndroidAll(PushProxyEnter enter);
}
