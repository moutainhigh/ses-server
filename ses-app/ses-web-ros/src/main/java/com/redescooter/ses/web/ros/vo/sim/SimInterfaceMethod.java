package com.redescooter.ses.web.ros.vo.sim;

/**
 * @ClassName SimInterfaceMethod
 * @Description sim接口名字
 * @Author Charles
 * @Date 2021/05/26 22:13
 */
public interface SimInterfaceMethod {

    String SIM_METHOD_IOT_SIM_CARDS = "iot_sim_cards";

    String SIM_METHOD_SIM_CARD_FILTER_DATA = "sim_card_filter_data";

    String SIM_METHOD_ACTIVATION_READY = "activation_ready";

    String SIM_METHOD_DEACTIVATE = "deactivate";

    String SIM_METHOD_accounts = "accounts";

    String SIM_METHOD_CURRENT_BALANCE = "current_balance";

    String SIM_METHOD_BALANCE_HISTORY = "balance_history";

    String SIM_METHOD_balance_payments = "balance_payments";

    String SIM_METHOD_balance_usages = "balance_usages";

    String SIM_METHOD_PRICES = "prices";
}
