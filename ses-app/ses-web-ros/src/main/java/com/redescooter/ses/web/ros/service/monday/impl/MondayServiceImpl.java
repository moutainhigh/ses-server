package com.redescooter.ses.web.ros.service.monday.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.web.ros.config.MondayConfigYml;
import com.redescooter.ses.web.ros.constant.MondayParameterName;
import com.redescooter.ses.web.ros.constant.MondayQueryGqlConstant;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dm.MondayConfig;
import com.redescooter.ses.web.ros.enums.columntemplate.MondayRedeEnums;
import com.redescooter.ses.web.ros.enums.datatype.BoardKindEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.MondayConfigService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationBoardEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationColumnEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayMutationGroupEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MultipleWebhookEnter;
import com.redescooter.ses.web.ros.vo.monday.result.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayData;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.result.MondayItem;
import com.redescooter.ses.web.ros.vo.monday.result.MondayTagResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * cusotmer send to fr
     */
    private static Map<String, String> redeCustomerMap = MondayRedeEnums.getMondayRedeTitle();

    @Autowired
    private MondayConfigYml mondayConfig;

    @Autowired
    private MondayConfigService mondayConfigService;

    /**
     * 获取monday
     * @return
     */
    public MondayConfig getMondayConfig() {
        LambdaQueryWrapper<MondayConfig> qw = new LambdaQueryWrapper<>();
        qw.eq(MondayConfig::getDr, Constant.DR_FALSE);
        qw.orderByDesc(MondayConfig::getCreatedTime);
        qw.last("limit 1");
        return mondayConfigService.getOne(qw);
    }

    /**
     * 初始化集合
     */
    @Override
    public void initializationMondayBoardMap() {
        //非生产环境 允许加载 模版数据 单不加载正式模版对数据
        if (!mondayConfig.getLoadTemplate()) {
            if (CollectionUtils.isEmpty(redeCustomerMap)) {
                log.info("--------------模版集合为空,发起初始化-----------------");
                // 正式模版初始化
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
    @GlobalTransactional
    @Override
    public void initializationMondaytemplate() {
        if (mondayConfig.getLoadTemplate()) {
            //正式模版初始化
            initBoardMapHandler();
        } else {
            log.info("-----------------------------其他环境跳过Monday模版加载------------------------------------------");
        }
    }

    private void initBoardMapHandler() {
        log.info("-----------------------------初始化正式数据Monday模板------------------------------------------");
        checkCustomerEnumsColumn(this.mondayConfig.getCustomerBoardName(), redeCustomerMap, this.mondayConfig.getWorkspaceId(), mondayConfig.getAuthorization());
        log.info("-----------------------------初始化Monday模板结束------------------------------------------");
    }

    /**
     * 初始化备份模版
     */
    @Override
    public void initializationBackMondaytemplate() {
        log.info("-----------------------------初始化备份数据Monday模板------------------------------------------");
        checkCustomerEnumsColumn(mondayConfig.getCustomerBoardName(), redeCustomerMap, mondayConfig.getWorkspaceId(), mondayConfig.getAuthorization());
        log.info("-----------------------------初始化备份Monday模板结束------------------------------------------");
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
                MondayQueryGqlConstant.MUTATION_BOARD_NEW.replace(MondayParameterName.BOARD_NAME, enter.getBoardName())
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
        String groupName = queryGroup(String.valueOf(enter.getBoardId()), enter.getGroupName());
        if (StringUtils.isNotBlank(groupName)) {
            return MondayCreateResult.builder().id(groupName).build();
        }
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
        if (!CollectionUtils.isEmpty(enter)) {
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

        log.info("----------执行查询SQL{}----------", querySdl);
        // 将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, httpHeaders);
        // 执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(mondayConfig.getUrl(), method, requestEntity, String.class);
        } catch (Exception e) {
            throw new SesWebRosException();
        }
        return response.getBody();
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
                    .workspaceId(Integer.valueOf(workspaceId))//.templateId(Integer.valueOf(mondayConfig.getTempleteId()))
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

    /*——----------------------------------------------------------------------------------------------*/

    /**
     * 用户
     * @param boardName
     * @param parameterMap
     * @param workSpaceId
     */
    private void checkCustomerEnumsColumn(String boardName, Map<String, String> parameterMap, String workSpaceId, String authorization) {
        String boardId = mondayConfigService.queryBoardName(boardName, workSpaceId, authorization);
        if (StringUtils.isNotBlank(boardId)) {
            return;
        }
        MondayMutationBoardEnter mutationBoardEnter = MondayMutationBoardEnter.builder().boardName(boardName)
                .workspaceId(Integer.valueOf(workSpaceId))
                .boardKind(BoardKindEnums.PUBLIC.getCode()).build();
        if (StringManaConstant.entityIsNotNull(mutationBoardEnter)) {
            MondayCreateResult mondayCreateResult = mutationBoard(mutationBoardEnter);
            boardId = mondayCreateResult.getId();
        }
        // 板子校验
/*        MondayBoardResult mondayBoardResult = getBoardByBoardName(boardName, workSpaceId);
        log.info("板子id {}", mondayBoardResult.getId());
        String boardId = mondayBoardResult.getId();*/
        // 列校验
        List<MondayColumnResult> mondayColumnResults = queryColumnResult(boardId);

        // 放入插入的列集合
        List<MondayMutationColumnEnter> bookOrderColumnList = new ArrayList<>();

        if (CollectionUtils.isEmpty(mondayColumnResults)) {
            List<MondayMutationColumnEnter> columnEnterList = MondayRedeEnums.init(Integer.parseInt(boardId));
            List<MondayColumnResult> results = multipleColumn(columnEnterList);
            MondayMutationGroupEnter redeGroup = new MondayMutationGroupEnter();
            redeGroup.setBoardId(Integer.parseInt(boardId));
            redeGroup.setGroupName(mondayConfig.getCustomerGroupName());
            MondayCreateResult mondayCreateResult1 = mutationGroup(redeGroup);
            mondayConfigService.saveMondayConfig(boardName, boardId, mondayConfig.getCustomerGroupName(), workSpaceId, mondayConfig.getAuthorization());
            //delelteGroup(mondayBoardResult.getId(), "group title");
            return;
        }

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
                if (null == mondayColumnResult || !mondayColumnResult.getId().equals(parameterMap.get(map))) {
                    bookOrderColumnList.add(MondayMutationColumnEnter.builder()
                            .boardId(Integer.valueOf(boardId)).title(map)
                            .columnType(MondayRedeEnums.getEnumsTypeByTitle(map)).defaults(null).build());
                }

            }
        }

        // 更新模版集合
        if (!CollectionUtils.isEmpty(bookOrderColumnList)) {
            List<MondayMutationColumnEnter> columnEnterList = MondayRedeEnums.init(Integer.parseInt(boardId));
            // 对Map 重新赋值
            List<MondayColumnResult> contantUsColumnResultList = null;
            if (bookOrderColumnList.size() == columnEnterList.size()) {
                contantUsColumnResultList = multipleColumn(columnEnterList);
            }
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
        MondayMutationGroupEnter redeGroup = new MondayMutationGroupEnter();
        redeGroup.setBoardId(Integer.parseInt(boardId));
        redeGroup.setGroupName(mondayConfig.getCustomerGroupName());
        MondayCreateResult mondayCreateResult1 = mutationGroup(redeGroup);
        mondayConfigService.saveMondayConfig(boardName, boardId, mondayConfig.getCustomerGroupName(), workSpaceId, mondayConfig.getAuthorization());
    }

    /**
     * @Title: queryColumns
     * @Description: // 查询列
     * @Param: [boardId]
     * @Return: java.util.List<com.redescooter.ses.web.ros.vo.monday.result.MondayColumnResult>
     * @Date: 2021/6/22 6:35 下午
     * @Author: Charles
     */
    public List<MondayColumnResult> queryColumns(String boardId) {
        String graphGql = MondayQueryGqlConstant.QUERY_BOARDS_COLUMNS.replace(MondayParameterName.COLUMN_BOARD_ID, boardId);
        MondayGeneralResult mondayGeneralResult = mutationData(graphGql);
        List<MondayColumnResult> columns = mondayGeneralResult.getData().getBoards().get(0).getColumns();
        return columns;
    }

    /**
     * 存在返回true，否则为false
     * @param boardId
     * @param groupName
     * @return
     */
    public String queryGroup(String boardId, String groupName) {
        String graphGql = MondayQueryGqlConstant.QUERY_BOARDS_GROUPS
                .replace(MondayParameterName.COLUMN_BOARD_ID, boardId)
                .replace(MondayParameterName.GROUP_BOARD_NAME, groupName);
        MondayGeneralResult mondayGeneralResult = mutationData(graphGql);
        List<MondayGroupResult> groups = mondayGeneralResult.getData().getBoards().get(0).getGroups();
        if (CollectionUtils.isEmpty(groups)) {
            return null;
        }
        return groups.get(0).getTitle();
    }

    /**
     * @Title: batchItemData
     * @Description: // 行列填充数据
     * @Param: [list, boardId, itemId]
     * @Return: void
     * @Date: 2021/6/22 6:40 下午
     * @Author: Charles
     */
    @Override
    public void batchItemData(List<MondayData> list, String boardId, String itemId) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (MondayData data : list) {
            String graphGql = MondayQueryGqlConstant.MUTATION_INSERT_ITEM_DATA
                    .replace(MondayParameterName.COLUMN_BOARD_ID, boardId)
                    .replace(MondayParameterName.ITEM_ID, itemId)
                    .replace(MondayParameterName.COLUMN_TITLE, JSON.toJSONString(data.getTitle()))
                    .replace(MondayParameterName.CREATE_COLUMN_VALUES, JSON.toJSONString(data.getValue()));
            MondayGeneralResult mondayGeneralResult = mutationData(graphGql);
            System.out.println(mondayGeneralResult);

        }
    }

    /**
     * @Title: insertItem
     * @Description: // 加行
     * @Param: [item]
     * @Return: java.lang.String
     * @Date: 2021/6/22 6:41 下午
     * @Author: Charles
     */
    @Override
    public String insertItem(MondayItem item) {
        String graphGql = MondayQueryGqlConstant.MUTATION_INSERT_ITEM
                .replace(MondayParameterName.COLUMN_BOARD_ID, item.getBoardId())
                .replace(MondayParameterName.GROUP_BOARD_NAME, item.getGroupId())
                .replace(MondayParameterName.CREATE_ITEM_NAME, item.getItemName());
        MondayGeneralResult mondayGeneralResult = mutationData(graphGql);
        String create_item_id = mondayGeneralResult.getData().getCreate_item().getId();
        System.out.println(create_item_id);
        return create_item_id;
    }

    /**
     * @Title: delelteGroup
     * @Description: // 删除
     * @Param: [boardId, groupName]
     * @Return: void
     * @Date: 2021/6/22 6:44 下午
     * @Author: Charles
     */
    public void delelteGroup(String boardId, String groupName) {
        String gql = MondayQueryGqlConstant.DELETE_GROUP.replace(MondayParameterName.COLUMN_BOARD_ID, boardId)
                .replace(MondayParameterName.GROUP_BOARD_NAME, groupName);
        MondayGeneralResult mondayGeneralResult = mutationData(gql);
        System.out.println(mondayGeneralResult);
    }

    /**
     * 存在返回false， 不存在返回true
     * @param boardName
     * @return
     */
    @Override
    public String existBoardName(String boardName) {
        if (StringUtils.isBlank(boardName)) {
            return null;
        }

        List<MondayBoardResult> result = queryBoard();
        if (CollectionUtils.isEmpty(result)) {
            MondayMutationBoardEnter enter = new MondayMutationBoardEnter();
            enter.setBoardName(boardName);
            enter.setBoardKind(BoardKindEnums.PUBLIC.getCode());
            MondayCreateResult mondayCreateResult = mutationBoard(enter);
            return mondayCreateResult.getId();
        }
        for (MondayBoardResult res : result) {
            if (StringUtils.equals(boardName, res.getName())) {
                return res.getId();
            }
        }
        MondayMutationBoardEnter enter = new MondayMutationBoardEnter();
        enter.setBoardName(boardName);
        enter.setBoardKind(BoardKindEnums.PUBLIC.getCode());
        MondayCreateResult mondayCreateResult = mutationBoard(enter);
        return mondayCreateResult.getId();
    }
}
