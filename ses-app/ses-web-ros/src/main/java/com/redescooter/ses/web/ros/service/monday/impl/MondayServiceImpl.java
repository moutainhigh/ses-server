package com.redescooter.ses.web.ros.service.monday.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.MondayParameterName;
import com.redescooter.ses.web.ros.constant.MondayQueryGqlConstant;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayBookOrderColumnEnums;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayContantUsColumnEnums;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayWebsiteSubscriptionEmailEnums;
import com.redescooter.ses.web.ros.enums.datatype.BoardKindEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayColumnDateEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayColumnPhoneEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayCountryShortNameEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayBookOrderEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayGeneralEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationBoardEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationColumnEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationGroupEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MultipleWebhookEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayDataResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayTagResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MondayServiceImpl
 * @description: MondayServiceImpl
 * @author: Alex @Version：1.3
 * @create: 2020/07/09 16:46
 */
@Service
@Slf4j
public class MondayServiceImpl implements MondayService {
    // 预订单板子模板
    private static Map<String, String> bookOrderMap = new HashMap<>();
    // 联系我们板子模板
    private static Map<String, String> contantUsMap = new HashMap<>();
    // 订阅的邮件板子模板
    private static Map<String, String> subscribeEmailMap = new HashMap<>();
    // 预订单板子模板
    private static Map<String, String> bookOrderBackMap = new HashMap<>();
    // 联系我们板子模板
    private static Map<String, String> contantUsBackMap = new HashMap<>();
    // 订阅的邮件板子模板
    private static Map<String, String> subscribeEmailBackMap = new HashMap<>();

    @Autowired
    private MondayConfig mondayConfig;

    @Autowired
    private OpePartsProductService opePartsProductService;

    /**
     * 初始化集合
     */
    @Override
    public void initializationMondayBoardMap() {
        //非生产环境 允许加载 模版数据 单不加载正式模版对数据
        if (!mondayConfig.getLoadTemplate()) {
            if (CollectionUtils.isEmpty(bookOrderMap) || CollectionUtils.isEmpty(contantUsMap) || CollectionUtils.isEmpty(subscribeEmailMap)) {

                log.info("--------------模版集合为空,发起初始化-----------------");
                //正式模版初始化
                initBoardMapHandler();
            }
        }
    }

    /**
     * 初始化单据模板
     * <p>
     * 注意：初始化模板主要逻辑如下 1、板子校验 没有就新建 2、列 是否合法 不合法就新建 （列不可通过API 删除）
     */
    @PostConstruct
    @Override
    public void initializationMondaytemplate() {
        if (mondayConfig.getLoadTemplate()) {
            //正式模版初始化
            initBoardMapHandler();
            // 备份模版初始化
            initializationBackMondaytemplate();
        } else {
            log.info("-----------------------------其他环境跳过Monday模版加载------------------------------------------");
        }
    }

    private void initBoardMapHandler() {
        log.info("-----------------------------初始化正式数据Monday模板------------------------------------------");

        // 初始化预订单模板
        for (MondayBookOrderColumnEnums item : MondayBookOrderColumnEnums.values()) {
            bookOrderMap.put(item.getTitle(), item.getId());
        }
        checkBookOrderBoardColumn(mondayConfig.getOrderFormBoardName(), bookOrderMap,
                mondayConfig.getWorkspaceId());
        // 初始化联系我们
        for (MondayContantUsColumnEnums item : MondayContantUsColumnEnums.values()) {
            contantUsMap.put(item.getTitle(), item.getId());
        }
        checkCountantUsBoardColumn(mondayConfig.getContactUsBoardName(), contantUsMap,
                mondayConfig.getWorkspaceId());
        // 订阅邮件 模板初始化
        for (MondayWebsiteSubscriptionEmailEnums item : MondayWebsiteSubscriptionEmailEnums.values()) {
            subscribeEmailMap.put(item.getTitle(), item.getId());
        }
        checkSubscribeEmailoardColumn(mondayConfig.getSubEmailBoardName(), subscribeEmailMap,
                mondayConfig.getWorkspaceId());
        log.info("-----------------------------初始化Monday模板结束------------------------------------------");
    }

    /**
     * 初始化备份模版
     */
    @Override
    public void initializationBackMondaytemplate() {

        log.info("-----------------------------初始化备份数据Monday模板------------------------------------------");
        // 初始化预订单模板
        for (MondayBookOrderColumnEnums item : MondayBookOrderColumnEnums.values()) {
            bookOrderBackMap.put(item.getTitle(), item.getId());
        }
        checkBookOrderBoardColumn(mondayConfig.getOrderFormBoardBackName(), bookOrderBackMap,
                mondayConfig.getWorkspaceBackId());
        // 初始化联系我们
        for (MondayContantUsColumnEnums item : MondayContantUsColumnEnums.values()) {
            contantUsBackMap.put(item.getTitle(), item.getId());
        }
        checkCountantUsBoardColumn(mondayConfig.getContactUsBoardBackName(), contantUsBackMap,
                mondayConfig.getWorkspaceBackId());
        // 订阅邮件 模板初始化
        for (MondayWebsiteSubscriptionEmailEnums item : MondayWebsiteSubscriptionEmailEnums.values()) {
            subscribeEmailBackMap.put(item.getTitle(), item.getId());
        }
        checkSubscribeEmailoardColumn(mondayConfig.getSubEmailBoardBackName(), subscribeEmailBackMap,
                mondayConfig.getWorkspaceBackId());
        log.info("-----------------------------初始化备份Monday模板结束------------------------------------------");
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public MondayCreateResult websiteContantUs(MondayGeneralEnter enter) {
//        if (!mondayConfig.getLoadTemplate()) {
//            return new MondayCreateResult();
//        }
        initializationMondayBoardMap();
        // 正式数据
        // 查看 板子是否存在
        MondayBoardResult mondayBoardResult =
                getBoardByBoardName(mondayConfig.getContactUsBoardName(), mondayConfig.getWorkspaceId());
        // 校验分组是否存在
        MondayCreateResult groupResult =
                getMondayGroupByBoardId(mondayBoardResult.getId(), mondayConfig.getContactUsGroupName());
        // 替换语句中的id 参数
        String gql = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardResult.getId())
                .replace(MondayParameterName.CREATE_BELONG_GROUP, groupResult.getId())
                .replace(MondayParameterName.CREATE_ITEM_NAME,
                        new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
                .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildParameterJson(enter, contantUsMap));
        // 数据插入
        MondayDataResult result = mutationData(gql).getData();

        if (mondayConfig.getLoadTemplate()) {
            // 备份数据
            MondayBoardResult mondayBoardBackResult =
                    getBoardByBoardName(mondayConfig.getContactUsBoardBackName(), mondayConfig.getWorkspaceBackId());
            // 校验分组是否存在
            MondayCreateResult groupBackResult =
                    getMondayGroupByBoardId(mondayBoardBackResult.getId(), mondayConfig.getContactUsGroupName());
            // 替换语句中的id 参数
            String gqlBack = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                    .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardBackResult.getId())
                    .replace(MondayParameterName.CREATE_BELONG_GROUP, groupBackResult.getId())
                    .replace(MondayParameterName.CREATE_ITEM_NAME,
                            new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
                    .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildParameterJson(enter, contantUsBackMap));
            // 数据插入
            mutationData(gqlBack).getData();
        }
        return result != null ? result.getCreate_item() : null;
    }

    /**
     * 官网预订单
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public MondayCreateResult websiteBookOrder(MondayGeneralEnter<MondayBookOrderEnter> enter) {
//        if (!mondayConfig.getLoadTemplate()) {
//            return new MondayCreateResult();
//        }
        initializationMondayBoardMap();
        // 查看 板子是否存在
        MondayBoardResult mondayBoardResult =
                getBoardByBoardName(mondayConfig.getOrderFormBoardName(), mondayConfig.getWorkspaceId());
        // 校验分组是否存在
        MondayCreateResult groupResult =
                getMondayGroupByBoardId(mondayBoardResult.getId(), mondayConfig.getOrderFormGroupName());

        // 替换语句中的id 参数
        String gql = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardResult.getId())
                .replace(MondayParameterName.CREATE_BELONG_GROUP, groupResult.getId())
                .replace(MondayParameterName.CREATE_ITEM_NAME,
                        new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
                .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildParameterJson(enter, bookOrderMap));
        // 数据插入
        MondayDataResult result = mutationData(gql).getData();

        if (mondayConfig.getLoadTemplate()) {
            // 备份数据
            // 查看 板子是否存在
            MondayBoardResult mondayBoardBackResult =
                    getBoardByBoardName(mondayConfig.getOrderFormBoardBackName(), mondayConfig.getWorkspaceBackId());
            // 校验分组是否存在
            MondayCreateResult groupBackResult =
                    getMondayGroupByBoardId(mondayBoardBackResult.getId(), mondayConfig.getOrderFormGroupName());

            // 替换语句中的id 参数
            String gqlBack = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                    .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardBackResult.getId())
                    .replace(MondayParameterName.CREATE_BELONG_GROUP, groupBackResult.getId())
                    .replace(MondayParameterName.CREATE_ITEM_NAME,
                            new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
                    .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildParameterJson(enter, bookOrderBackMap));
            // 数据插入
            mutationData(gqlBack).getData();
        }
        return result != null ? result.getCreate_item() : null;
    }

    /**
     * 官网订阅邮件
     *
     * @param email
     * @return
     */
    @Override
    public MondayCreateResult websiteSubscriptionEmail(String email) {
//        if (!mondayConfig.getLoadTemplate()) {
//            return new MondayCreateResult();
//        }
        initializationMondayBoardMap();
        // 查看 板子是否存在
        MondayBoardResult mondayBoardResult =
                getBoardByBoardName(mondayConfig.getSubEmailBoardName(), mondayConfig.getWorkspaceId());

        // 校验分组是否存在
        MondayCreateResult groupResult =
                getMondayGroupByBoardId(mondayBoardResult.getId(), mondayConfig.getSubEmailGroupName());
        // 替换语句中的id 参数
        String gql = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardResult.getId())
                .replace(MondayParameterName.CREATE_BELONG_GROUP, groupResult.getId())
                .replace(MondayParameterName.CREATE_ITEM_NAME, email.split("@")[0])
                .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildSubEmailSingle(email, subscribeEmailMap));
        // 数据插入
        MondayDataResult result = mutationData(gql).getData();

        if (mondayConfig.getLoadTemplate()) {
            // 备份数据
            // 查看 板子是否存在
            MondayBoardResult mondayBoardBackResult =
                    getBoardByBoardName(mondayConfig.getSubEmailBoardBackName(), mondayConfig.getWorkspaceBackId());

            // 校验分组是否存在
            MondayCreateResult groupBackResult =
                    getMondayGroupByBoardId(mondayBoardBackResult.getId(), mondayConfig.getSubEmailGroupName());
            // 替换语句中的id 参数
            String gqlBack = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
                    .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardBackResult.getId())
                    .replace(MondayParameterName.CREATE_BELONG_GROUP, groupBackResult.getId())
                    .replace(MondayParameterName.CREATE_ITEM_NAME, email.split("@")[0])
                    .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildSubEmailSingle(email, subscribeEmailBackMap));
            // 数据插入
            mutationData(gqlBack).getData();
        }
        return result != null ? result.getCreate_item() : null;
    }

    @Override
    public List<MondayBoardResult> queryBoard() {

        log.info("查询板子执行gql----{}", MondayQueryGqlConstant.QUERY_BOARD);

        MondayGeneralResult mondayGeneralResult = mutationData(MondayQueryGqlConstant.QUERY_BOARD);

        return mondayGeneralResult.getData().getBoards();
    }

    /**
     * 查询该板子下的分组
     *
     * @param boardId
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public List<MondayGroupResult> queryGroupByBoardId(String boardId) {

        // 替换语句中的id 参数
        String graphGql = MondayQueryGqlConstant.QUERY_GROUP.replace(MondayParameterName.BOARD_PARAMETER, boardId);

        log.info("查询指定板子下的分组------{}", graphGql);

        MondayGeneralResult mondayGeneralResult = mutationData(graphGql);

        List<MondayGroupResult> groupList = mondayGeneralResult.getData().getBoards().get(0).getGroups();

        return groupList;
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public List<MondayTagResult> queryTagList() {

        log.info("查询所有标签-------{}", MondayQueryGqlConstant.QUERY_TAGS);

        MondayGeneralResult mondayGeneralResult = mutationData(MondayQueryGqlConstant.QUERY_TAGS);

        return mondayGeneralResult.getData().getTags();
    }

    /**
     * 查询该板子所有列
     *
     * @return
     */
    @Override
    public List<MondayColumnResult> queryColumnResult(String boardId) {
        String graphGql = MondayQueryGqlConstant.QUERY_COLUMN.replace(MondayParameterName.BOARD_PARAMETER, boardId);

        log.info("查询指定板子下的所有列---------------{}", graphGql);

        MondayGeneralResult mondayGeneralResult = mutationData(graphGql);

        return mondayGeneralResult.getData().getBoards().get(0).getColumns();
    }

    /**
     * 插入板子
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public MondayCreateResult mutationBoard(MondayMutationBoardEnter enter) {
        String graphGql =
                MondayQueryGqlConstant.MUTATION_BOARD.replace(MondayParameterName.BOARD_NAME, enter.getBoardName())
                        .replace(MondayParameterName.BOARD_KIND, enter.getBoardKind())
                        .replace(MondayParameterName.BOARD_WORDSPACE, String.valueOf(enter.getWorkspaceId()))
                        .replace(MondayParameterName.BOARD_TEMPLETE, String.valueOf(enter.getTemplateId()));
        log.info("插入板子执行gql------{}", graphGql);
        return MondayCreateResult.builder().id(mutationData(graphGql).getData().getCreate_board().getId()).build();
    }

    /**
     * 插入分组
     *
     * @param enter
     * @return
     */
    @Override
    public MondayCreateResult mutationGroup(MondayMutationGroupEnter enter) {
        String graphGql = MondayQueryGqlConstant.MUTATION_GROUP
                .replace(MondayParameterName.GROUP_BOARD_ID, String.valueOf(enter.getBoardId()))
                .replace(MondayParameterName.GROUP_BOARD_NAME, enter.getGroupName());
        log.info("插入分组执行的gql----{}", graphGql);
        return MondayCreateResult.builder().id(mutationData(graphGql).getData().getCreate_group().getId()).build();
    }

    /**
     * 创建列
     *
     * @param enter
     * @return
     */
    @Override
    public List<MondayColumnResult> multipleColumn(List<MondayMutationColumnEnter> enter) {
        List<MondayColumnResult> result = new ArrayList<>();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(enter)) {
            enter.forEach(item -> {
                String graphGql = MondayQueryGqlConstant.MUTATION_COLUMN
                        .replace(MondayParameterName.COLUMN_BOARD_ID, String.valueOf(item.getBoardId()))
                        .replace(MondayParameterName.COLUMN_TITLE, item.getTitle())
                        .replace(MondayParameterName.COLUMN_TYPE, item.getColumnType())
                        .replace(MondayParameterName.COLUMN_DEFAULTS,
                                StringUtils.isEmpty(item.getDefaults()) == true ? "{}" : item.getDefaults());
                log.info("插列执行的gql----{}", graphGql);
                MondayGeneralResult mondayGeneralResult = mutationData(graphGql);

                log.info("----------------------插入成功 ----->{}------------------",
                        mondayGeneralResult.getData().getCreate_column());
                result.add(mondayGeneralResult.getData().getCreate_column());
            });
        }
        return result;
    }

    /**
     * 创建一个钩子
     *
     * @param enter
     */
    @Override
    public void multipleWebhook(MultipleWebhookEnter enter) {
        String graphGql = MondayQueryGqlConstant.MUTATION_CREATE_WEBHOOK
                .replace(MondayParameterName.BOARD_PARAMETER, String.valueOf(enter.getBoardId()))
                .replace(MondayParameterName.WEBHOOK_URL, enter.getUrl())
                .replace(MondayParameterName.WEBHOOK_CONFIG, enter.getConfig())
                .replace(MondayParameterName.WEBHOOK_EVENT, enter.getEventType().toString());
        log.info("插入分组执行的gql----{}", graphGql);
        mutationData(graphGql);
    }

    /**
     * 执行gql
     *
     * @param gql
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public MondayGeneralResult mutationData(String gql) {
        log.info("-----------执行gpl语句{}--------------", gql);
        String mondayJson = getMondayData(gql, HttpMethod.POST);

        log.info("执行gql 返回值的json数据-------{}", mondayJson);
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);

        if (StringManaConstant.entityIsNotNull(mondayGeneralResult.getErrors())) {
            throw new RuntimeException();
        }
        return mondayGeneralResult;
    }

    private String getMondayData(String querySdl, HttpMethod method) {
        // 定义restTemplate 模板
        RestTemplate restTemplate = new RestTemplate();
        // 定义 httpHeaders 请求
        HttpHeaders httpHeaders = new HttpHeaders();
        // 以表单的方式提交
        httpHeaders.setContentType(mondayConfig.getMediaType());
        httpHeaders.add(MondayParameterName.AUTHORIZATION, mondayConfig.getAuthorization());

        // 请求体提交参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(mondayConfig.getParamQuery(), querySdl);
        map.add(mondayConfig.getParamVariables(), null);

        log.info("----------执行查询SQL{}----------", querySdl.toString());
        // 将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, httpHeaders);
        // 执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = null;
        try {
            response =
                    restTemplate.exchange(mondayConfig.getUrl(), method, requestEntity, String.class);
        } catch (Exception e) {
            throw new SesWebRosException();
        }
        return response.getBody();
    }

    /**
     * 联系我们 数据封装
     *
     * @param enter
     * @return
     */
    private String buildParameterJson(MondayGeneralEnter<MondayBookOrderEnter> enter,
                                      Map<String, String> parameterMap) {

        // 电话集合
        Map<String, String> phoneMap = new HashMap<>();
        if (StringUtils.isNotEmpty(enter.getTelephone())) {
            phoneMap.put(MondayColumnPhoneEnums.phone.getPhoneTel(),
                    org.springframework.util.StringUtils.isEmpty(enter.getTelephone()) == true ? null
                            : enter.getTelephone());
            phoneMap.put(MondayColumnPhoneEnums.phone.getCountryShortName(),
                    MondayCountryShortNameEnums.FRANCE.getValue());
        }

        // 时间集合
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put(MondayColumnDateEnums.DATE.getTitle(),
                DateUtil.getTimeStr(enter.getCreatedTime(), DateUtil.DEFAULT_DATE_FORMAT));
        dateMap.put(MondayColumnDateEnums.TIME.getTitle(),
                DateUtil.getTimeStr(enter.getCreatedTime(), DateUtil.DEFAULT_TIME_FORMAT));

        Map<String, Object> columnValue = new HashMap<>();
        // 时间
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.FIRST_CONTACT.getTitle()), dateMap);
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.LAST_CONTACTED.getTitle()), null);
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.NEXT_CONTACT.getTitle()), null);
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.PRENOM.getTitle()), enter.getFirstName());
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.NOM.getTitle()), enter.getLastName());
        // 电话
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.TEL.getTitle()),
                CollectionUtils.isEmpty(phoneMap) == true ? null : phoneMap);
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.EMAIL.getTitle()), enter.getEmail());
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.VILLE.getTitle()), enter.getCity());
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.CODE_POSTAL.getTitle()), enter.getDistant());
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.VOTRE_MESSAGE.getTitle()), enter.getRemarks());
        columnValue.put(parameterMap.get(MondayContantUsColumnEnums.ADDRESSE.getTitle()), enter.getAddress());

        if (enter.getT() instanceof MondayBookOrderEnter) {
            columnValue.put(parameterMap.get(MondayBookOrderColumnEnums.NB_SCOOTERS.getTitle()),
                    String.valueOf(enter.getT().getQty()));
            columnValue.put(parameterMap.get(MondayBookOrderColumnEnums.MODEL.getTitle()),
                    enter.getT().getProducModeltName());
            columnValue.put(parameterMap.get(MondayBookOrderColumnEnums.COULEUR.getTitle()),
                    enter.getT().getProductColor());
            columnValue.put(parameterMap.get(MondayBookOrderColumnEnums.QUANTITÉ_DE_BATTERIE.getTitle()),
                    String.valueOf(enter.getT().getBatteryQty()));
        }

        // 转json 并转义
        String columnValues = StringEscapeUtils.escapeJson(new JSONObject(columnValue).toJSONString());
        log.info("----------------------" + columnValues + "-------------------------");

        return columnValues;
    }

    /**
     * 联系我们 数据封装
     *
     * @param enter
     * @return
     */
    /*private String buildBookOrderSingle(MondayBookOrderEnter enter) {

        // 查询产品信息
        OpePartsProduct opePartsProduct = opePartsProductService.getById(enter.getProductId());
        if (opePartsProduct == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        // 电话集合
        Map<String, String> phoneMap = new HashMap<>();

        if (StringUtils.isNotEmpty(enter.getTelephone())) {
            phoneMap.put(MondayColumnPhoneEnums.phone.getPhoneTel(),
                    org.springframework.util.StringUtils.isEmpty(enter.getTelephone()) == true ? null
                            : enter.getTelephone());
            phoneMap.put(MondayColumnPhoneEnums.phone.getCountryShortName(),
                    MondayCountryShortNameEnums.FRANCE.getValue());
        }

        // 时间集合
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put(MondayColumnDateEnums.DATE.getTitle(),
                DateUtil.getTimeStr(enter.getCreatedTime(), DateUtil.DEFAULT_DATE_FORMAT));
        dateMap.put(MondayColumnDateEnums.TIME.getTitle(),
                DateUtil.getTimeStr(enter.getCreatedTime(), DateUtil.DEFAULT_TIME_FORMAT));

        Map<String, Object> columnValue = new HashMap<>();
        // 时间
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.FIRST_CONTACT.getTitle()), dateMap);
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.LAST_CONTACTED.getTitle()), null);
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.LAST_CONTACTED.getTitle()), null);
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.PRENOM.getTitle()), enter.getFirstName());
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.NOM.getTitle()), enter.getLastName());
        // 电话
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.TEL.getTitle()),
                CollectionUtils.isEmpty(phoneMap) == true ? null : phoneMap);
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.EMAIL.getTitle()), enter.getEmail());
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.VILLE.getTitle()), null);
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.CODE_POSTAL.getTitle()), enter.getDef2());
        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.VOTRE_MESSAGE.getTitle()), enter.getRemarks());

        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.NB_SCOOTERS.getTitle()), enter.getScooterQuantity());

        columnValue.put(bookOrderMap.get(MondayBookOrderColumnEnums.MODEL.getTitle()),
                ProductModelEnums.getProductModelEnumsByValue(opePartsProduct.getModel()).getMessage());

        String columnValues = StringEscapeUtils.escapeJson(new JSONObject(columnValue).toJSONString());
        log.info("----------------------" + columnValues + "-------------------------");
        return columnValues;
    }*/

    /**
     * 订阅邮件 模板参数封装
     *
     * @param email
     * @return
     */
    private String buildSubEmailSingle(String email, Map<String, String> parameterMap) {
        Map<String, Object> columnValue = new HashMap<>();

        columnValue.put(parameterMap.get(MondayWebsiteSubscriptionEmailEnums.COURRIER.getTitle()), email);
        String columnValues = StringEscapeUtils.escapeJson(new JSONObject(columnValue).toJSONString());
        log.info("----------------------" + columnValues + "-------------------------");
        return columnValues;
    }

    /**
     * 获取指定模板
     *
     * @param boardName
     * @return
     */
    private MondayBoardResult getBoardByBoardName(String boardName, String workspaceId) {
        List<MondayBoardResult> mondayBoardResults = queryBoard();

        MondayMutationBoardEnter mutationBoardEnter = null;
        if (CollectionUtil.isEmpty(mondayBoardResults)) {
            // 创建板子
            mutationBoardEnter = MondayMutationBoardEnter.builder().boardName(boardName)
                    .workspaceId(Integer.valueOf(workspaceId)).templateId(Integer.valueOf(mondayConfig.getTempleteId()))
                    .boardKind(BoardKindEnums.PUBLIC.getCode()).build();
        }
        MondayBoardResult mondayBoardResult = mondayBoardResults.stream()
                .filter(item -> StringUtils.equals(item.getName(), boardName)).findFirst().orElse(null);
        if (StringManaConstant.entityIsNull(mondayBoardResult)) {
            // 创建板子
            mutationBoardEnter = MondayMutationBoardEnter.builder().boardName(boardName)
                    .workspaceId(Integer.valueOf(workspaceId)).templateId(Integer.valueOf(mondayConfig.getTempleteId()))
                    .boardKind(BoardKindEnums.PUBLIC.getCode()).build();
        }
        if (StringManaConstant.entityIsNotNull(mutationBoardEnter)) {
            MondayCreateResult mondayCreateResult = mutationBoard(mutationBoardEnter);
            return MondayBoardResult.builder().id(mondayCreateResult.getId()).build();
        }

        return mondayBoardResult;
    }

    /**
     * 获取指定板子下的指定分组
     *
     * @param boardId
     * @param groupName
     * @return
     */
    private MondayCreateResult getMondayGroupByBoardId(String boardId, String groupName) {
        MondayMutationGroupEnter mondayMutationGroupEnter = null;
        // 数据插入
        List<MondayGroupResult> mondayGroupResults = queryGroupByBoardId(boardId);
        if (CollectionUtils.isEmpty(mondayGroupResults)) {
            // 该板子没有任分组时 创建指定分组
            mondayMutationGroupEnter = new MondayMutationGroupEnter();
            mondayMutationGroupEnter.setBoardId(Integer.valueOf(boardId));
            mondayMutationGroupEnter.setGroupName(groupName);
        }
        MondayGroupResult mondayGroupResult = mondayGroupResults.stream()
                .filter(item -> StringUtils.equals(item.getTitle(), groupName)).findFirst().orElse(null);
        if (StringManaConstant.entityIsNull(mondayGroupResult)) {
            // 板子下村子分组 但是没有我们需要的指定分组
            mondayMutationGroupEnter = new MondayMutationGroupEnter();
            mondayMutationGroupEnter.setBoardId(Integer.valueOf(boardId));
            mondayMutationGroupEnter.setGroupName(groupName);
        }
        if (StringManaConstant.entityIsNotNull(mondayMutationGroupEnter)) {
            return mutationGroup(mondayMutationGroupEnter);
        }
        MondayCreateResult mondayCreateResult = new MondayCreateResult();
        mondayCreateResult.setId(mondayGroupResult.getId());
        return mondayCreateResult;
    }

    /**
     * 校验联系我们列名
     *
     * @param boardName
     */
    private void checkCountantUsBoardColumn(String boardName, Map<String, String> parameterMap, String workSpaceId) {
        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName, workSpaceId);

        List<MondayColumnResult> mondayColumnResults = queryColumnResult(mondayBoardResult.getId());

        // 放入插入的列集合
        List<MondayMutationColumnEnter> contantUsColumnList = new ArrayList<>();

        // 过滤更新
        if (!CollectionUtils.isEmpty(mondayColumnResults)) {
            // 覆盖map中的初始Id
            mondayColumnResults.forEach(item -> {
                if (parameterMap.containsKey(item.getTitle())) {
                    parameterMap.put(item.getTitle(), item.getId());
                }
            });

            for (String map : parameterMap.keySet()) {
                if (StringUtils.isBlank(map)) {
                    continue;
                }
                MondayColumnResult mondayColumnResult = mondayColumnResults.stream()
                        .filter(item -> StringUtils.equals(item.getTitle(), map)).findFirst().orElse(null);
                if (StringManaConstant.entityIsNull(mondayColumnResult) || !mondayColumnResult.getId().equals(parameterMap.get(map))) {
                    contantUsColumnList.add(MondayMutationColumnEnter.builder()
                            .boardId(Integer.valueOf(mondayBoardResult.getId())).title(map)
                            .columnType(MondayContantUsColumnEnums.getEnumsTypeByTitle(map)).defaults(null).build());
                }

            }
        }

        // 列插入 并更新模版集合
        if (!CollectionUtils.isEmpty(contantUsColumnList)) {
            // 对Map 重新赋值
            List<MondayColumnResult> contantUsColumnResultList = multipleColumn(contantUsColumnList);

            if (!CollectionUtils.isEmpty(contantUsColumnResultList)) {
                // 校验 列 是否符合标准 不符合新建
                for (MondayColumnResult item : contantUsColumnResultList) {
                    if (parameterMap.containsKey(item.getTitle())) {
                        if (!StringUtils.equals(parameterMap.get(item.getTitle()), item.getId())) {
                            // 更新模板Map
                            parameterMap.put(item.getTitle(), item.getId());
                        }
                    }
                }

            }
        }
    }

    /**
     * 校验模板列表名
     *
     * @param boardName
     */
    private void checkBookOrderBoardColumn(String boardName, Map<String, String> parameterMap, String workSpaceId) {
        // 板子校验
        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName, workSpaceId);

        // 列校验
        List<MondayColumnResult> mondayColumnResults = queryColumnResult(mondayBoardResult.getId());

        // 放入插入的列集合
        List<MondayMutationColumnEnter> bookOrderColumnList = new ArrayList<>();

        // 过滤更新
        if (!CollectionUtils.isEmpty(mondayColumnResults)) {
            // 覆盖map中的初始Id
            mondayColumnResults.forEach(item -> {
                if (parameterMap.containsKey(item.getTitle())) {
                    parameterMap.put(item.getTitle(), item.getId());
                }
            });

            for (String map : parameterMap.keySet()) {
                if (StringUtils.isBlank(map)) {
                    continue;
                }
                MondayColumnResult mondayColumnResult = mondayColumnResults.stream()
                        .filter(item -> StringUtils.equals(item.getTitle(), map)).findFirst().orElse(null);
                if (StringManaConstant.entityIsNull(mondayColumnResult) || !mondayColumnResult.getId().equals(parameterMap.get(map))) {
                    bookOrderColumnList.add(MondayMutationColumnEnter.builder()
                            .boardId(Integer.valueOf(mondayBoardResult.getId())).title(map)
                            .columnType(MondayBookOrderColumnEnums.getEnumsTypeByTitle(map)).defaults(null).build());
                }

            }
        }

        // 更新模版集合
        if (!CollectionUtils.isEmpty(bookOrderColumnList)) {
            // 对Map 重新赋值
            List<MondayColumnResult> contantUsColumnResultList = multipleColumn(bookOrderColumnList);

            if (!CollectionUtils.isEmpty(contantUsColumnResultList)) {
                // 校验 列 是否符合标准 不符合新建
                for (MondayColumnResult item : contantUsColumnResultList) {
                    if (parameterMap.containsKey(item.getTitle())) {
                        if (!StringUtils.equals(parameterMap.get(item.getTitle()), item.getId())) {
                            // 更新模板Map
                            parameterMap.put(item.getTitle(), item.getId());
                        }
                    }
                }

            }
        }
    }

    /**
     * 订阅邮件 模板更新
     *
     * @param boardName
     */
    private void checkSubscribeEmailoardColumn(String boardName, Map<String, String> parameterMap, String workSpaceId) {
        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName, workSpaceId);

        List<MondayColumnResult> mondayColumnResults = queryColumnResult(mondayBoardResult.getId());

        // 放入插入的列集合
        List<MondayMutationColumnEnter> subEmailColumnList = new ArrayList<>();

        // 过滤更新
        if (!CollectionUtils.isEmpty(mondayColumnResults)) {
            // 覆盖map中的初始Id
            mondayColumnResults.forEach(item -> {
                if (parameterMap.containsKey(item.getTitle())) {
                    parameterMap.put(item.getTitle(), item.getId());
                }
            });

            parameterMap.keySet().forEach(map -> {
                MondayColumnResult mondayColumnResult = mondayColumnResults.stream()
                        .filter(item -> StringUtils.equals(item.getTitle(), map)).findFirst().orElse(null);
                if (StringManaConstant.entityIsNull(mondayColumnResult) || !mondayColumnResult.getId().equals(parameterMap.get(map))) {
                    subEmailColumnList
                            .add(MondayMutationColumnEnter.builder().boardId(Integer.valueOf(mondayBoardResult.getId()))
                                    .title(map).columnType(MondayWebsiteSubscriptionEmailEnums.getEnumsTypeByTitle(map))
                                    .defaults(null).build());
                }

            });
        }

        // 列插入 并更新模版集合
        if (!CollectionUtils.isEmpty(subEmailColumnList)) {
            // 对Map 重新赋值
            List<MondayColumnResult> subEmailColumnResultList = multipleColumn(subEmailColumnList);

            if (!CollectionUtils.isEmpty(subEmailColumnResultList)) {
                // 校验 列 是否符合标准 不符合新建
                for (MondayColumnResult item : subEmailColumnResultList) {
                    if (parameterMap.containsKey(item.getTitle())) {
                        if (!StringUtils.equals(parameterMap.get(item.getTitle()), item.getId())) {
                            // 更新模板Map
                            parameterMap.put(item.getTitle(), item.getId());
                        }
                    }
                }

            }
        }
    }
}
