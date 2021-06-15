package com.redescooter.ses.web.ros.service.sim.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.sim.OpeSimInformationMapper;
import com.redescooter.ses.web.ros.dm.OpeSimInformation;
import com.redescooter.ses.web.ros.service.sim.OpeSimInformationService;
import com.redescooter.ses.web.ros.utils.HmacUtil;
import com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult;
import com.redescooter.ses.web.ros.vo.sim.SimCardListResult;
import com.redescooter.ses.web.ros.vo.sim.SimCardSessionsResult;
import com.redescooter.ses.web.ros.vo.sim.SimCardStatus;
import com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult;
import com.redescooter.ses.web.ros.vo.sim.SimDataResult;
import com.redescooter.ses.web.ros.vo.sim.SimEnter;
import com.redescooter.ses.web.ros.vo.sim.SimEssentialInformation;
import com.redescooter.ses.web.ros.vo.sim.SimInterfaceMethod;
import com.redescooter.ses.web.ros.vo.sim.SimResult;
import com.redescooter.ses.web.ros.vo.sim.SimTransactionRecordsResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * sim卡信息(OpeSimInformation)表服务实现类
 *
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
@Slf4j
@Service
public class OpeSimInformationServiceImpl extends ServiceImpl<OpeSimInformationMapper, OpeSimInformation> implements OpeSimInformationService {

    private String status = "?status_id=";

    /**
     * @Title: getSimInformation
     * @Description: // 获取基本信息
     * @Param: []
     * @Return: com.redescooter.ses.web.ros.vo.sim.SimEssentialInformation
     * @Date: 2021/5/27 1:39 下午
     * @Author: Charles
     */
    public SimEssentialInformation getSimInformation() {
        String simInformation = sendGetSimRequest(getReqMethod(null, null, null, null));
        if (StringUtils.isBlank(simInformation)) {
            return null;
        }
        SimEssentialInformation simEssentialInformation = JSONObject.parseObject(simInformation, SimEssentialInformation.class);
        return simEssentialInformation;
    }

    /**
     * @Title: getSimTransactionRecords
     * @Description: // 获取交易记录
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimTransactionRecords(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_BALANCE_PAYMENTS, null, null, getPage(simEnter)));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString(StringManaConstant.RESPONSE_DATA);
        int total = jsonObject.getInteger(StringManaConstant.RESPONSE_TOTAL);
        List<SimTransactionRecordsResult> list = JSONArray.parseArray(data, SimTransactionRecordsResult.class);
        return new SimDataResult(list, total);
    }

    /**
     * @Title: getSimCardList
     * @Description: // Sim卡 列表信息 状态 status_id=1
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimCardList(SimEnter simEnter) {
        String param = getParam(simEnter.getStatus(), simEnter.getIccid());
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, null, getPage(simEnter), param));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString(StringManaConstant.RESPONSE_DATA);
        int total = jsonObject.getInteger(StringManaConstant.RESPONSE_TOTAL);
        List<SimCardListResult> list = null;
        // 单一
        if (StringUtils.isNotBlank(simEnter.getIccid())) {
            QueryWrapper<OpeSimInformation> qwOpe = new QueryWrapper<>();
            qwOpe.eq(OpeSimInformation.COL_DR, Constant.DR_FALSE);
            qwOpe.select(OpeSimInformation.COL_TABLET_SN, OpeSimInformation.COL_SIM_ICCID);
            qwOpe.and(Wrapper -> Wrapper.eq(OpeSimInformation.COL_SIM_ICCID, simEnter.getIccid()).or().eq(OpeSimInformation.COL_TABLET_SN, simEnter.getIccid()));
            qwOpe.last("limit 1");
            OpeSimInformation simInformation = this.getOne(qwOpe);
            if (0 == total && null == simInformation) {
                return null;
            }
            if (0 != total) {
                list = JSONArray.parseArray(data, SimCardListResult.class);
                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }
                list.get(0).setSimCardStatus(getStatus(list.get(0).getStatus()));
                list.stream().forEach((SimCardListResult simResult) -> {
                    simResult.setTabledSn(null == simInformation ? "" : simInformation.getTabletSn());
                });
                return new SimDataResult(list, total);
            }
            if (0 == total && null != simInformation) {
                String param1 = getParam(simEnter.getStatus(), simInformation.getSimIccid());
                String result = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, null, "?", param1));
                if (StringUtils.isBlank(result)) {
                    return null;
                }
                JSONObject resultJson = JSONObject.parseObject(result);
                String resultTata = resultJson.getString("data");
                int resultTotal = resultJson.getInteger("total");
                list = JSONArray.parseArray(resultTata, SimCardListResult.class);
                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }
                list.get(0).setSimCardStatus(getStatus(list.get(0).getStatus()));
                list.stream().forEach((SimCardListResult simResult) -> {
                    simResult.setTabledSn(simInformation.getTabletSn());
                });
                return new SimDataResult(list, resultTotal);
            }
        }


        list = JSONArray.parseArray(data, SimCardListResult.class);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            SimCardListResult simCardListResult = list.get(i);
            simCardListResult.setSimCardStatus(getStatus(simCardListResult.getStatus()));
            QueryWrapper<OpeSimInformation> qw = new QueryWrapper<>();
            qw.eq(OpeSimInformation.COL_DR, Constant.DR_FALSE);
            qw.eq(OpeSimInformation.COL_SIM_ICCID, simCardListResult.getIccid());
            qw.select(OpeSimInformation.COL_TABLET_SN);
            qw.last("limit 1");
            OpeSimInformation information = this.getOne(qw);
            if (null == information) {
                continue;
            }
            simCardListResult.setTabledSn(information.getTabletSn());
        }
        return new SimDataResult(list, total);
    }

    private String getParam(String param1, String param2) {
        String status = StringUtils.isBlank(param1) ? null : "&status_id=" + param1;
        String iccid = StringUtils.isBlank(param2) ? null : "&common_filter=" + param2;
        String param = (null == status ? "" : status) + (null == iccid ? "" : iccid);
        return param;
    }

    /**
     * @Title: activationSim
     * @Description: // Sim卡 激活
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public boolean activationSim(SimEnter simEnter) {
        SimEssentialInformation simEssentialInformation = getSimInformation();
        if (StringManaConstant.entityIsNull(simEssentialInformation)) {
            return false;
        }
        // 余额不足要加提示
        // 账户余额不足，请充值后再来开启
        if (simEssentialInformation.isLowBalance()) {
            return false;
        }
        String body = sendPostSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_ACTIVATION_READY, null));
        if (StringUtils.isBlank(body)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ack = jsonObject.getString(StringManaConstant.RESPONSE_ACK);
        if (StringManaConstant.RESPONSE_STATUS_SUCESS.equals(ack)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: deactivatedSim
     * @Description: // Sim卡 停用
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response
     * @Date: 2021/5/28 9:00 上午
     * @Author: Charles
     */
    @Override
    public boolean deactivatedSim(SimEnter simEnter) {
        String body = sendPostSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_DEACTIVATE, null));
        if (StringUtils.isBlank(body)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ack = jsonObject.getString(StringManaConstant.RESPONSE_ACK);
        if (StringManaConstant.RESPONSE_STATUS_SUCESS.equals(ack)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: deactivationDate
     * @Description: // sim 卡的停用日期
     * @Param: [simEnter]
     * @Return: boolean
     * @Date: 2021/5/31 9:45 上午
     * @Author: Charles
     */
    private boolean deactivationDate(SimEnter simEnter) {
        String body = sendPutSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_DEACTIVATION_DATE, null));
        if (StringUtils.isBlank(body)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ack = jsonObject.getString("ack");
        if ("success".equals(ack)) {
            return true;
        }
        return false;
    }

    /**
     * @Title: getSimEssentialInformation
     * @Description: // 获取sim基本信息
     * @Param: []
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimResult>
     * @Date: 2021/5/28 9:40 上午
     * @Author: Charles
     */
    @Override
    public SimResult getSimEssentialInformation() {
        SimEssentialInformation simInformation = getSimInformation();
        if (StringManaConstant.entityIsNull(simInformation)) {
            return null;
        }
        // 已激活:1-Activated, 激活就绪:4-Activation Ready, 停用:2-Deactivated, 暂停:3-Suspended
        int activatedTotal = getTotalByStatus(status + 1);
        int activatedReadyTotal = getTotalByStatus(status + 4);
        int deactivatedTotal = getTotalByStatus(status + 2);
        int suspendedTotal = getTotalByStatus(status + 3);

        int total = activatedTotal + activatedReadyTotal + deactivatedTotal + suspendedTotal;

        SimResult simResult = new SimResult();
        simResult.setCurrentBalance(simInformation.getCurrentBalance());
        simResult.setSimCount(total);
        simResult.setActivatedCount(activatedTotal);
        simResult.setActivationReadyCount(activatedReadyTotal);
        simResult.setDeactivatedCount(deactivatedTotal);
        simResult.setSuspended(suspendedTotal);
        return simResult;
    }

    /**
     * @Title: getTotalByStatus
     * @Description: // 查询各状态的总数据量
     * @Param: [status]
     * @Return: int
     * @Date: 2021/5/28 9:23 上午
     * @Author: Charles
     */
    private int getTotalByStatus(String status) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, null, status, null));
        if (StringUtils.isBlank(body)) {
            return 0;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        int total = jsonObject.getInteger(StringManaConstant.RESPONSE_TOTAL);
        return total;
    }

    /**
     * @Title: getSimDetails
     * @Description: // Sim卡 详情
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimBaseCodeResult>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public SimBaseCodeResult getSimDetails(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_IOT_SIM_CARDS, "?" + HmacUtil.COMMON_FILTER + "=" + simEnter.getIccid(), null, null));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString(StringManaConstant.RESPONSE_DATA);
        List<SimBaseCodeResult> simBaseCodeResults = JSONArray.parseArray(data, SimBaseCodeResult.class);
        SimBaseCodeResult simBaseCodeResult = 0 == simBaseCodeResults.size() ? new SimBaseCodeResult() : simBaseCodeResults.get(0);
        String status = null == simBaseCodeResult ? "" : simBaseCodeResult.getStatus();
        simBaseCodeResult.setSimCardStatus(getStatus(status));
        QueryWrapper<OpeSimInformation> qw = new QueryWrapper<OpeSimInformation>();
        qw.eq(OpeSimInformation.COL_DR, Constant.DR_FALSE);
        qw.eq(OpeSimInformation.COL_SIM_ICCID, simEnter.getIccid());
        qw.last("limit 1");
        OpeSimInformation opeSimInformation = this.getOne(qw);
        simBaseCodeResult.setRsn(opeSimInformation == null ? "" : opeSimInformation.getRsn());
        simBaseCodeResult.setVin(opeSimInformation == null ? "" : opeSimInformation.getVin());
        simBaseCodeResult.setTabledSn(opeSimInformation == null ? "" : opeSimInformation.getTabletSn());
        simBaseCodeResult.setMacAddress(opeSimInformation == null ? "" : opeSimInformation.getBluetoothMacAddress());
        SimEnter enter = new SimEnter();
        enter.setIccid(simEnter.getIccid());
        enter.setPageNo(1);
        enter.setPageSize(1);
        SimDataResult simDataResult = getSimConnectRecord(enter);
        if (null != simDataResult && !CollectionUtils.isEmpty(simDataResult.getList())) {
            SimCardSessionsResult simCardSessionsResult = JSONObject.parseObject(JSON.toJSONString(simDataResult.getList().get(0)), SimCardSessionsResult.class);
            simBaseCodeResult.setDeactivationDate(simCardSessionsResult.getEndDate());
        }
        return simBaseCodeResult;
    }

    /**
     * @Title: getSimConnectRecord
     * @Description: // 获取连接记录 是否加时间查询
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.web.ros.vo.sim.SimDataResult>
     * @Date: 2021/5/28 8:58 上午
     * @Author: Charles
     */
    @Override
    public SimDataResult getSimConnectRecord(SimEnter simEnter) {
        String dailyStatisticsDate = StringUtils.isBlank(simEnter.getDailyStatisticsDate()) ? "" : "&billing_cycle=" + simEnter.getDailyStatisticsDate();
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_SESSIONS,
                getPage(simEnter) + dailyStatisticsDate));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString(StringManaConstant.RESPONSE_DATA);
        int total = jsonObject.getInteger(StringManaConstant.RESPONSE_TOTAL);
        List<SimCardSessionsResult> list = JSONArray.parseArray(data, SimCardSessionsResult.class);
        return new SimDataResult(list, total);
    }

    /**
     * @Title: getSimDailyStatistics
     * @Description: // Sim卡 日统计
     * @Param: [simEnter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<java.util.List < com.redescooter.ses.web.ros.vo.sim.SimDailyStatisticsResult>>
     * @Date: 2021/5/28 8:59 上午
     * @Author: Charles
     */
    @Override
    public List<SimDailyStatisticsResult> getSimDailyStatistics(SimEnter simEnter) {
        String body = sendGetSimRequest(getReqMethod(SimInterfaceMethod.SIM_METHOD_SIM_CARD, simEnter.getIccid(), SimInterfaceMethod.SIM_METHOD_DAILY_STATISTICS,
                StringUtils.isBlank(simEnter.getDailyStatisticsDate()) ? null : "?billing_cycle=" + simEnter.getDailyStatisticsDate()));
        if (StringUtils.isBlank(body)) {
            return null;
        }
        List<SimDailyStatisticsResult> list = JSONArray.parseArray(body, SimDailyStatisticsResult.class);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        list.stream().forEach((SimDailyStatisticsResult result) -> {
            result.setUsageMb(kbToMb(result.getUsage_kb()));
        });
        return list;
    }

    private String getPage(SimEnter simEnter) {
        return "?page=" + simEnter.getPageNo() + "&per_page=" + simEnter.getPageSize();
    }

    private String getReqMethod(String url1, String iccid, String url2, String page) {
        return (url1 == null ? "" : url1) + (iccid == null ? "" : iccid) + (url2 == null ? "" : url2) + (page == null ? "" : page);
    }

    private String sendGetSimRequest(String reqMethod) {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {
                return null;
            }
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + reqMethod)
                    .method("GET", null)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String body = response.body().string();
            log.info("sim 相应结果 {}", body);
            return body;
        } catch (Exception e) {
            log.error("sim {}接口请求异常", reqMethod);
        }
        return null;
    }


    private String sendPostSimRequest(String reqMethod) {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {
                return null;
            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, "{}");
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + reqMethod)
                    .method("POST", body)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String bodyJson = response.body().string();
            log.info("sim 相应结果 {}", bodyJson);
            return bodyJson;
        } catch (Exception e) {
            log.error("sim {}接口请求异常", reqMethod);
        }
        return null;
    }

    private String sendPutSimRequest(String reqMethod) {
        try {
            Map<String, String> map = HmacUtil.generateKey();
            if (null == map) {
                return null;
            }
            String utcPlusDays = DateUtil.getUTCPlusDays(1);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put(SimInterfaceMethod.SIM_METHOD_DEACTIVATION_DATE, utcPlusDays);
            String paramJson = JSON.toJSONString(paramMap);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, paramJson);
            Request request = new Request.Builder()
                    .url(HmacUtil.API_LINK + reqMethod)
                    .method("PUT", body)
                    .addHeader(HmacUtil.SIM_TIMESTAMP, map.get(HmacUtil.SIM_TIMESTAMP))
                    .addHeader(HmacUtil.SIM_HASH, map.get(HmacUtil.SIM_HASH))
                    .addHeader(HmacUtil.SIM_UUID, HmacUtil.USER_UUID)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String bodyJson = response.body().string();
            log.info("sim 相应结果 {}", bodyJson);
            return bodyJson;
        } catch (Exception e) {
            log.error("sim {}接口请求异常", reqMethod);
        }
        return null;
    }

    private static double kbToMb(String kb) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(Double.valueOf(kb) / 1024));
    }

    private int getStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return 0;
        }
        switch (status) {
            case SimCardStatus.SIM_CARD_STATUSES_ACTIVATED:
                return 1;
            case SimCardStatus.SIM_CARD_STATUSES_ACTIVATION_READY:
                return 4;
            case SimCardStatus.SIM_CARD_STATUSES_DEACTIVATED:
                return 2;
            case SimCardStatus.SIM_CARD_STATUSES_SUSPENDED:
                return 3;
        }
        return 0;
    }

}
