package com.redescooter.ses.web.ros.service.monday.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.MondayParameterName;
import com.redescooter.ses.web.ros.constant.MondayQueryGqlConstant;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayBookOrderColumnEnums;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayContantUsColumnEnums;
import com.redescooter.ses.web.ros.enums.datatype.BoardKindEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayColumnDateEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayColumnGeneralEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayColumnPhoneEnums;
import com.redescooter.ses.web.ros.enums.datatype.MondayCountryShortNameEnums;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationColumnEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationGroupEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MultipleWebhookEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationBoardEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayTagResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private MondayConfig mondayConfig;
    @Autowired
    private OpePartsProductService opePartsProductService;

    /**
     * 初始化单据模板
     * <p>
     * 注意：初始化模板主要逻辑如下 1、板子校验 没有就新建 2、分组校验 没有就新建 3、列 是否合法 不合法就新建 （列不可通过API 删除）
     */
    @PostConstruct
    @Override
    public void initializationMondaytemplate() {
        // 初始化预订单模板
        for (MondayBookOrderColumnEnums item : MondayBookOrderColumnEnums.values()) {
            bookOrderMap.put(item.getTitle(), item.getId());
        }
        checkBookOrderBoardColumn(mondayConfig.getOrderFormBoardName());
        // 初始化联系我们
        for (MondayContantUsColumnEnums item : MondayContantUsColumnEnums.values()) {
            contantUsMap.put(item.getTitle(), item.getId());
        }
        checkCountantUsBoardColumn(mondayConfig.getContactUsBoardName());

    }

    @Transactional
    @Override
    public MondayCreateResult websiteContantUs(OpeCustomerInquiry enter) {

        // 查看 板子是否存在
        MondayBoardResult mondayBoardResult = getBoardByBoardName(mondayConfig.getContactUsBoardName());

        // 校验分组是否存在
        MondayCreateResult groupResult =
            getMondayGroupByBoardId(mondayBoardResult.getId(), mondayConfig.getContactUsGroupName());

        // 替换语句中的id 参数
        String gql = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
            .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardResult.getId())
            .replace(MondayParameterName.CREATE_BELONG_GROUP, groupResult.getId())
            .replace(MondayParameterName.CREATE_ITEM_NAME,
                new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
            .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildContantUsSingle(enter));
        // 数据插入
        return mutationData(gql).getData().getCreate_item();
    }

    /**
     * 官网预订单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public MondayCreateResult websiteBookOrder(OpeCustomerInquiry enter) {
        // 查看 板子是否存在
        MondayBoardResult mondayBoardResult = getBoardByBoardName(mondayConfig.getOrderFormBoardName());

        // 校验分组是否存在
        MondayCreateResult groupResult =
            getMondayGroupByBoardId(mondayBoardResult.getId(), mondayConfig.getOrderFormGroupName());

        // 替换语句中的id 参数
        String gql = MondayQueryGqlConstant.MUTATION_ITEM_COLUMN_DATA
            .replace(MondayParameterName.BOARD_PARAMETER, mondayBoardResult.getId())
            .replace(MondayParameterName.CREATE_BELONG_GROUP, groupResult.getId())
            .replace(MondayParameterName.CREATE_ITEM_NAME,
                new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
            .replace(MondayParameterName.CREATE_COLUMN_VALUES, buildContantUsSingle(enter));

        // 数据插入
        return mutationData(gql).getData().getCreate_item();
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
    @Transactional
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
        String graphGql = MondayQueryGqlConstant.QUERY_GROUP.replace(MondayParameterName.BOARD_PARAMETER, boardId);

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
    @Transactional
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
        return mutationData(graphGql).getData().getCreate_item();
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
    @Transactional
    @Override
    public MondayGeneralResult mutationData(String gql) {
        String mondayJson = getMondayData(gql, HttpMethod.POST);

        log.info("执行gql 返回值的json数据-------{}", mondayJson);
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);

        if (mondayGeneralResult.getErrors() != null) {
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

        // 将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, httpHeaders);
        // 执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response =
            restTemplate.exchange(mondayConfig.getUrl(), method, requestEntity, String.class);
        return response.getBody();
    }

    /**
     * 联系我们 数据封装
     *
     * @param enter
     * @return
     */
    private String buildContantUsSingle(OpeCustomerInquiry enter) {

        Map<String, Object> tagList = new HashMap();
        Integer tagId = null;
        if (StringUtils.isNotEmpty(enter.getDef2())) {
            // 查询tag 标签
            List<MondayTagResult> mondayTagResults = queryTagList();
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(mondayTagResults)) {
                tagId = mondayTagResults.stream().filter(item -> StringUtils.equals(item.getName(), enter.getDef2()))
                    .findFirst().map(MondayTagResult::getId).orElse(null);
            }
            if (!(tagId == null) && !(tagId == 0)) {
                tagList.put(MondayColumnGeneralEnums.TAG_IDS.getTitle(), Lists.newArrayList(tagId));
            }
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
        // columnValue.put(MondayContantUsColumnEnums.ID_CP.getId(), CollectionUtils.isEmpty(tagList) == true ? null :
        // tagList);
        // columnValue.put(MondayContantUsColumnEnums.RESP.getId(), null);
        // 时间
        columnValue.put(MondayContantUsColumnEnums.FIRST_CONTACT.getId(), dateMap);
        columnValue.put(MondayContantUsColumnEnums.LAST_CONTACTED.getId(), null);
        columnValue.put(MondayContantUsColumnEnums.NEXT_CONTACT.getId(), null);
        // columnValue.put(MondayContantUsColumnEnums.TYPE.getId(), null);
        // columnValue.put(MondayContantUsColumnEnums.CONVERSION.getId(), null);
        columnValue.put(MondayContantUsColumnEnums.PRENOM.getId(), enter.getFirstName());
        columnValue.put(MondayContantUsColumnEnums.NOM.getId(), enter.getLastName());
        // 电话
        columnValue.put(MondayContantUsColumnEnums.TEL.getId(),
            CollectionUtils.isEmpty(phoneMap) == true ? null : phoneMap);
        columnValue.put(MondayContantUsColumnEnums.EMAIL.getId(), enter.getEmail());
        columnValue.put(MondayContantUsColumnEnums.VILLE.getId(), null);
        columnValue.put(MondayContantUsColumnEnums.CODE_POSTAL.getId(), enter.getDef2());
        columnValue.put(MondayContantUsColumnEnums.VOTRE_MESSAGE.getId(), enter.getRemarks());
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
    private String buildBookOrderSingle(OpeCustomerInquiry enter) {

        // 查询产品信息
        OpePartsProduct opePartsProduct = opePartsProductService.getById(enter.getProductId());
        if (opePartsProduct == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        Map<String, Object> tagList = new HashMap();
        Integer tagId = null;
        if (StringUtils.isNotEmpty(enter.getDef2())) {
            // 查询tag 标签
            List<MondayTagResult> mondayTagResults = queryTagList();
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(mondayTagResults)) {
                tagId = mondayTagResults.stream().filter(item -> StringUtils.equals(item.getName(), enter.getDef2()))
                    .findFirst().map(MondayTagResult::getId).orElse(null);
            }
            if (!(tagId == null) && !(tagId == 0)) {
                tagList.put("tag_ids", Lists.newArrayList(tagId));
            }
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
        // columnValue.put(MondayBookOrderColumnEnums.ID_CP.getId(), CollectionUtils.isEmpty(tagList) == true ? null :
        // tagList);
        // columnValue.put(MondayBookOrderColumnEnums.RESP.getId(), null);
        // 时间
        columnValue.put(MondayBookOrderColumnEnums.FIRST_CONTACT.getId(), dateMap);
        columnValue.put(MondayBookOrderColumnEnums.LAST_CONTACTED.getId(), null);
        columnValue.put(MondayBookOrderColumnEnums.NEXT_CONTACT.getId(), null);
        // columnValue.put(MondayBookOrderColumnEnums.TYPE.getId(), null);
        // columnValue.put(MondayBookOrderColumnEnums.CONVERSION.getId(), null);
        columnValue.put(MondayBookOrderColumnEnums.PRENOM.getId(), enter.getFirstName());
        columnValue.put(MondayBookOrderColumnEnums.NOM.getId(), enter.getLastName());
        // 电话
        columnValue.put(MondayBookOrderColumnEnums.TEL.getId(),
            CollectionUtils.isEmpty(phoneMap) == true ? null : phoneMap);
        columnValue.put(MondayBookOrderColumnEnums.EMAIL.getId(), enter.getEmail());
        columnValue.put(MondayBookOrderColumnEnums.VILLE.getId(), null);
        columnValue.put(MondayBookOrderColumnEnums.CODE_POSTAL.getId(), enter.getDef2());
        columnValue.put(MondayBookOrderColumnEnums.VOTRE_MESSAGE.getId(), enter.getRemarks());

        columnValue.put(MondayBookOrderColumnEnums.NB_SCOOTERS.getId(), enter.getScooterQuantity());

        columnValue.put(MondayBookOrderColumnEnums.MODEL.getId(),
            ProductModelEnums.getProductModelEnumsByValue(opePartsProduct.getModel()).getMessage());
        // columnValue.put(MondayBookOrderColumnEnums.COULEUR.getId(),
        // ProductColorEnums.getProductColorEnumsByValue(opePartsProduct.getColor()).getMessage());

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
    private MondayBoardResult getBoardByBoardName(String boardName) {
        List<MondayBoardResult> mondayBoardResults = queryBoard();

        MondayMutationBoardEnter mutationBoardEnter = null;
        if (CollectionUtil.isEmpty(mondayBoardResults)) {
            // 创建板子
            mutationBoardEnter = MondayMutationBoardEnter.builder().boardName(boardName)
                .workspaceId(Integer.valueOf(mondayConfig.getWorkspaceId()))
                .templateId(Integer.valueOf(mondayConfig.getTempleteId())).boardKind(BoardKindEnums.PUBLIC.getCode())
                .build();
        }
        MondayBoardResult mondayBoardResult = mondayBoardResults.stream()
            .filter(item -> StringUtils.equals(item.getName(), boardName)).findFirst().orElse(null);
        if (mondayBoardResult == null) {
            // 创建板子
            mutationBoardEnter = MondayMutationBoardEnter.builder().boardName(boardName)
                .workspaceId(Integer.valueOf(mondayConfig.getWorkspaceId()))
                .templateId(Integer.valueOf(mondayConfig.getTempleteId())).boardKind(BoardKindEnums.PUBLIC.getCode())
                .build();
        }
        if (mutationBoardEnter != null) {
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
        if (mondayGroupResult == null) {
            // 板子下村子分组 但是没有我们需要的指定分组
            mondayMutationGroupEnter = new MondayMutationGroupEnter();
            mondayMutationGroupEnter.setBoardId(Integer.valueOf(boardId));
            mondayMutationGroupEnter.setGroupName(groupName);
        }
        if (mondayMutationGroupEnter != null) {
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
    private void checkCountantUsBoardColumn(String boardName) {
        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName);

        List<MondayColumnResult> mondayColumnResults = queryColumnResult(mondayBoardResult.getId());

        // 放入插入的列集合
        List<MondayMutationColumnEnter> contantUsColumnList = new ArrayList<>();

        // 列无需校验时 更新模版集合
        if (!CollectionUtils.isEmpty(contantUsColumnList)) {
            mondayColumnResults.forEach(item -> {
                if (contantUsMap.containsKey(item.getTitle())) {
                    contantUsMap.put(item.getTitle(), item.getId());
                }
            });
        }

        if (!CollectionUtils.isEmpty(mondayColumnResults)) {
            for (MondayContantUsColumnEnums item : MondayContantUsColumnEnums.values()) {
                // 列有并且Id 一样 跳出循环
                if (contantUsMap.containsKey(item.getTitle())
                    && StringUtils.equals(contantUsMap.get(item.getTitle()), item.getId())) {
                    continue;
                }

                if (!contantUsMap.containsKey(item.getTitle())
                    || !StringUtils.equals(contantUsMap.get(item.getTitle()), item.getId())) {
                    contantUsColumnList
                        .add(MondayMutationColumnEnter.builder().boardId(Integer.valueOf(mondayBoardResult.getId()))
                            .title(item.getTitle()).columnType(item.getType()).defaults(null).build());
                }
            }

            // 列插入 并更新模版集合
            if (!CollectionUtils.isEmpty(contantUsColumnList)) {
                // 对Map 重新赋值
                List<MondayColumnResult> contantUsColumnResultList = multipleColumn(contantUsColumnList);

                if (!CollectionUtils.isEmpty(contantUsColumnResultList)) {
                    // 校验 列 是否符合标准 不符合新建
                    for (MondayColumnResult item : contantUsColumnResultList) {
                        if (contantUsMap.containsKey(item.getTitle())) {
                            if (!StringUtils.equals(contantUsMap.get(item.getTitle()), item.getId())) {
                                // 更新模板Map
                                contantUsMap.put(item.getTitle(), item.getId());
                            }
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
    private void checkBookOrderBoardColumn(String boardName) {
        // 板子校验
        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName);

        // 列校验
        List<MondayColumnResult> mondayColumnResults = queryColumnResult(mondayBoardResult.getId());

        // 放入插入的列集合
        List<MondayMutationColumnEnter> bookOrderColumnList = new ArrayList<>();

        // 列无需更新时 更新模版集合
        if (!CollectionUtils.isEmpty(mondayColumnResults)) {
            mondayColumnResults.forEach(item -> {
                if (bookOrderMap.containsKey(item.getTitle())) {
                    bookOrderMap.put(item.getTitle(), item.getId());
                }
            });
        }

        if (!CollectionUtils.isEmpty(bookOrderColumnList)) {
            for (MondayBookOrderColumnEnums item : MondayBookOrderColumnEnums.values()) {
                // 列有并且Id 一样 跳出循环
                if (bookOrderMap.containsKey(item.getTitle())
                    && StringUtils.equals(bookOrderMap.get(item.getTitle()), item.getId())) {
                    continue;
                }
                if (!bookOrderMap.containsKey(item.getTitle())
                    || !StringUtils.equals(bookOrderMap.get(item.getTitle()), item.getId())) {
                    bookOrderColumnList
                        .add(MondayMutationColumnEnter.builder().boardId(Integer.valueOf(mondayBoardResult.getId()))
                            .title(item.getTitle()).columnType(item.getType()).defaults(null).build());
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
                    if (bookOrderMap.containsKey(item.getTitle())) {
                        if (!StringUtils.equals(bookOrderMap.get(item.getTitle()), item.getId())) {
                            // 更新模板Map
                            bookOrderMap.put(item.getTitle(), item.getId());
                        }
                    }
                }

            }
        }
    }
}
