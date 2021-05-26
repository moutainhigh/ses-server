package com.redescooter.ses.web.ros.vo.sim;

/**
 * @ClassName SimContant
 * @Description sim 状态
 * @Author Charles
 * @Date 2021/05/26 21:28
 */
public interface SimCardStatus {

    /**
     * 已激活
     */
    String SIM_CARD_STATUSES_ACTIVATED = "Activated";

    /**
     * 激活就绪
     */
    String SIM_CARD_STATUSES_ACTIVATION_READY = "Activation Ready";

    /**
     * 停用
     */
    String SIM_CARD_STATUSES_DEACTIVATED = "Deactivated";

    /**
     * 暂停
     */
    String SIM_CARD_STATUSES_SUSPENDED = "Suspended";

}
