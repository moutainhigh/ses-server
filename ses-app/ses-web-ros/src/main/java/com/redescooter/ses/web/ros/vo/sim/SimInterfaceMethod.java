package com.redescooter.ses.web.ros.vo.sim;

/**
 * @ClassName SimInterfaceMethod
 * @Description sim接口名字
 * @Author Charles
 * @Date 2021/05/26 22:13
 */
public interface SimInterfaceMethod {

    /**
     * 单卡追加
     */
    String SIM_METHOD_SIM_CARD = "sim_card/";

    /**
     * sim卡列表，包括数据使用量，费率计划，计费周期，当前计费周期的数据会话数
     */
    String SIM_METHOD_IOT_SIM_CARDS = "iot_sim_cards";

    /**
     * sim卡状态
     */
    String SIM_METHOD_SIM_CARD_FILTER_DATA = "sim_card_filter_data";

    /**
     * sim_card / {SIM_iccid} /
     * 立即激活sim卡并未其启用数据供应，必须有足够的账户余额才可以操作
     */
    String SIM_METHOD_ACTIVATION_READY = "/activation_ready";

    /**
     * sim_card / {SIM_iccid} /
     * 立即停用SIM卡并禁用其数据供应
     */
    String SIM_METHOD_DEACTIVATE = "/deactivate";

    /**
     * sim_card / {SIM_iccid} /
     * 用于更新SIM卡的停用日期
     */
    String SIM_METHOD_DEACTIVATION_DATE = "deactivation_date";

    /**
     * sim_card / {SIM_iccid} /
     * 接收当前计费周期中SIM卡数据会话的列表，包括以KB消耗的数据，记录会话的日期和时间，记录会话持续时间和国家/地区会话
     */
    String SIM_METHOD_SESSIONS = "/sessions";

    /**
     * sim_card / {SIM_iccid} /
     * 接收在当前计费周期中使用过的SIM卡的目的地（国家或地区）列表，以及每个此类国家/地区的MB数据使用量
     */
    String SIM_METHOD_DESTINATION_LOG = "/destination_log";

    /**
     * sim_card / {SIM_iccid} /
     * 接收当前计费周期中SIM卡每天使用数据的每一天的数据使用量
     */
    String SIM_METHOD_DAILY_STATISTICS = "/daily_statistics";

    /**
     * sim_card / {SIM_iccid} /
     * 接收每月的SIM使用摘要
     */
    String SIM_METHOD_USAGE_SUMMARY = "/usage_summary";

    /**
     * 指定主账号的子账户列表
     */
    String SIM_METHOD_METHOD_ACCOUNTS = "accounts";

    /**
     * 接收该账户当前可用的美元余额
     */
    String SIM_METHOD_CURRENT_BALANCE = "current_balance";

    /**
     * sim_card / {SIM_iccid} /
     * 接收SIM卡可用的账单周期的历史列表。这可以用于其他请求中的过滤目的
     */
    String SIM_METHOD_BILL_CYCLE_LIST = "/bill_cycle_list";

    /**
     * 接收帐户余额条目的汇总每日历史记录，包括累积的每日借方和贷方操作
     */
    String SIM_METHOD_BALANCE_HISTORY = "balance_history";

    /**
     * 接收所有已完成的余额补充付款的清单，包括已付金额，日期和时间，交易ID和使用的付款方式
     */
    String SIM_METHOD_BALANCE_PAYMENTS = "balance_payments";

    /**
     * 接收帐户每个国家/地区每天每张SIM卡的余额借项事件的详细列表
     */
    String SIM_METHOD_BALANCE_USAGES = "balance_usages";

    /**
     * 接收该帐户在每个国家/地区使用的每兆字节数据的最新数据价格
     */
    String SIM_METHOD_PRICES = "prices";
}
