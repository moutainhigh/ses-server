package com.redescooter.ses.web.ros.service.monday.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.MondayQueryConstant;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.MondayDataResult;
import com.redescooter.ses.web.ros.vo.monday.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.MondayMutationDataEnter;
import com.redescooter.ses.web.ros.vo.monday.MondayTagResult;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;

/**
 * @ClassName:MondayServiceImpl
 * @description: MondayServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:46
 */
@Service
@Log4j
public class MondayServiceImpl implements MondayService {

    @Autowired
    private MondayConfig mondayConfig;

    @Override
    public void push(OpeCustomer enter) {

        //查看 板子是否存在
        List<MondayBoardResult> mondayBoardResults = queryBoard();
        if (CollectionUtil.isEmpty(mondayBoardResults)) {
            //创建
        }
        MondayBoardResult mondayBoardResult = mondayBoardResults.stream().filter(item -> StringUtils.equals(item.getName(), mondayConfig.getContactUsBoardName())).findFirst().orElse(null);
        if (mondayBoardResult == null) {
            //创建
        }

        //数据插入
        List<MondayGroupResult> mondayGroupResults = queryGroup(mondayBoardResults.get(0).getId());
        if (CollectionUtils.isEmpty(mondayGroupResults)) {
            //创建
        }
        MondayGroupResult mondayGroupResult = mondayGroupResults.stream().filter(item -> StringUtils.equals(item.getTitle(), mondayConfig.getContactUsBoardName())).findFirst().orElse(null);
        if (mondayGroupResult == null) {
            //创建
        }
        //查询tag 标签
        List<MondayTagResult> mondayTagResults = queryTagList();
        Integer tagId = null;
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(mondayTagResults)) {
            tagId = mondayTagResults.stream().filter(item -> StringUtils.equals(item.getName(), enter.getDef2())).findFirst().map(MondayTagResult::getId).orElse(null);
        }

        //数据插入
//        MondayMutationDataEnter
//                .builder()
//                .boardId()
//                .groupId()
//                .build();

    }

    @Transactional
    @Override
    public List<MondayBoardResult> queryBoard() {
        String mondayJson = getMondayData(MondayQueryConstant.QUERY_BOARD, HttpMethod.POST);
        System.out.println("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);
        return mondayDataResult.getBoards();
    }

    /**
     * 查询该板子下的分组
     *
     * @param boardId
     * @return
     */
    @Transactional
    @Override
    public List<MondayGroupResult> queryGroup(String boardId) {

        //替换语句中的id 参数
        String graphGql = MondayQueryConstant.QUERY_GROUP.replace(MondayQueryConstant.BOARD_PARAMETER, boardId);

        String mondayJson = getMondayData(graphGql, HttpMethod.POST);
        System.out.println("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);
        //暂时返回0表
        return mondayDataResult.getBoards().get(0).getGroups();
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public List<MondayTagResult> queryTagList() {
        String mondayJson = getMondayData(MondayQueryConstant.QUERY_TAGS, HttpMethod.POST);
        System.out.println("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);
        return mondayDataResult.getTags();
    }

    /**
     * 数据保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult mutationData(MondayMutationDataEnter enter) {
        //替换语句中的id 参数
        String graphGql = MondayQueryConstant.MUTATION_DATA
                .replace(MondayQueryConstant.BOARD_PARAMETER, enter.getBoardId())
                .replace(MondayQueryConstant.CREATE_BELONG_GROUP, enter.getGroupId())
                .replace(MondayQueryConstant.CREATE_ITEM_NAME, enter.getItemName())
                .replace(MondayQueryConstant.CREATE_COLUMN_VALUES, enter.getColumnValues());


        String mondayJson = getMondayData(graphGql, HttpMethod.POST);
        System.out.println("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);
        System.out.println(mondayDataResult.getCreate_item());
        return null;
    }

    private String getMondayData(String querySdl, HttpMethod method) {
        //定义restTemplate 模板
        RestTemplate restTemplate = new RestTemplate();
        //定义 httpHeaders 请求
        HttpHeaders httpHeaders = new HttpHeaders();
        // 以表单的方式提交
        httpHeaders.setContentType(mondayConfig.getMediaType());
        httpHeaders.add("authorization", mondayConfig.getAuthorization());

        // 请求体提交参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(mondayConfig.getParamQuery(), querySdl);
        map.add(mondayConfig.getParamVariables(), null);

        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, httpHeaders);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = restTemplate.exchange(mondayConfig.getUrl(), method, requestEntity, String.class);
        //没有请求成功抛出异常
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }
        return response.getBody();
    }

}
