package com.redescooter.ses.web.ros.service.monday.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.config.MondayConfig;
import com.redescooter.ses.web.ros.constant.MondayQueryGqlConstant;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.enums.MondayColumnDateEnums;
import com.redescooter.ses.web.ros.enums.MondayColumnEnums;
import com.redescooter.ses.web.ros.enums.MondayColumnPhoneEnums;
import com.redescooter.ses.web.ros.enums.MondayCountryShortNameEnums;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.MondayBoardResult;
import com.redescooter.ses.web.ros.vo.monday.MondayColumnResult;
import com.redescooter.ses.web.ros.vo.monday.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.monday.MondayDataResult;
import com.redescooter.ses.web.ros.vo.monday.MondayGeneralResult;
import com.redescooter.ses.web.ros.vo.monday.MondayGroupResult;
import com.redescooter.ses.web.ros.vo.monday.MondayMutationDataEnter;
import com.redescooter.ses.web.ros.vo.monday.MondayTagResult;
import lombok.extern.log4j.Log4j;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public MondayCreateResult websiteContantUs(OpeCustomerInquiry enter) {

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
        List<MondayGroupResult> mondayGroupResults = queryGroupByBoardId(mondayBoardResult.getId());
        if (CollectionUtils.isEmpty(mondayGroupResults)) {
            //创建
        }
        MondayGroupResult mondayGroupResult = mondayGroupResults.stream().filter(item -> StringUtils.equals(item.getTitle(), mondayConfig.getContactUsGroupName())).findFirst().orElse(null);
        if (mondayGroupResult == null) {
            //创建
        }
        //查询tag 标签
        List<MondayTagResult> mondayTagResults = queryTagList();
        Integer tagId = null;
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(mondayTagResults)) {
            tagId = mondayTagResults.stream().filter(item -> StringUtils.equals(item.getName(), enter.getDef2())).findFirst().map(MondayTagResult::getId).orElse(null);
        }


        int IdCp=0;
        for (MondayTagResult item : mondayTagResults) {
            if (StringUtils.equals(item.getName(), enter.getDef2())) {
                IdCp = item.getId();
                break;
            }
        }

        Map<String,String> phoneMap=new HashMap<>();

        phoneMap.put(MondayColumnPhoneEnums.phone.getPhoneTel(),enter.getTelephone());
        phoneMap.put(MondayColumnPhoneEnums.phone.getCountryShortName(), MondayCountryShortNameEnums.FRANCE.getValue());

        Map<String,String> dateMap=new HashMap<>();
        dateMap.put(MondayColumnDateEnums.DATE.getTitle(), DateUtil.getTimeStr(enter.getCreatedTime(),DateUtil.DEFAULT_DATE_FORMAT));
        dateMap.put(MondayColumnDateEnums.TIME.getTitle(), DateUtil.getTimeStr(enter.getCreatedTime(),DateUtil.DEFAULT_TIME_FORMAT));



        Map<String, Object>  columnValue= new HashMap<>();
        columnValue.put(MondayColumnEnums.ID_CP.getId(),IdCp);
        columnValue.put(MondayColumnEnums.RESP.getId(),null);
        //时间
        columnValue.put(MondayColumnEnums.FIRST_CONTACT.getId(),dateMap);
        columnValue.put(MondayColumnEnums.LAST_CONTACTED.getId(),null);
        columnValue.put(MondayColumnEnums.NEXT_CONTACT.getId(),null);
        columnValue.put(MondayColumnEnums.TYPE.getId(),null);
        columnValue.put(MondayColumnEnums.CONVERSION.getId(),null);
        columnValue.put(MondayColumnEnums.PRENOM.getId(),enter.getFirstName());
        columnValue.put(MondayColumnEnums.NOM.getId(),enter.getLastName());
        //电话
        columnValue.put(MondayColumnEnums.TEL.getId(),phoneMap);
        columnValue.put(MondayColumnEnums.EMAIL.getId(),enter.getEmail());
        columnValue.put(MondayColumnEnums.VILLE.getId(),null);
        columnValue.put(MondayColumnEnums.CODE_POSTAL.getId(),enter.getDef2());
        columnValue.put(MondayColumnEnums.VOTRE_MESSAGE.getId(),enter.getRemarks());

        String columnValues = StringEscapeUtils.escapeJson(new JSONObject(columnValue).toJSONString());
        System.out.println("----------------------"+columnValues+"-------------------------");

        //数据插入
//        MondayMutationDataEnter build = MondayMutationDataEnter
//                .builder()
//                .boardId(mondayBoardResult.getId())
//                .groupId(mondayGroupResult.getId())
//                .itemName(new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString())
//                .columnValues()
//                .build();
//        return mutationData(build);
        return null;
    }

    @Transactional
    @Override
    public List<MondayBoardResult> queryBoard() {

        System.out.println("--------------------------"+ MondayQueryGqlConstant.QUERY_BOARD+"--------------------------------");
        String mondayJson = getMondayData(MondayQueryGqlConstant.QUERY_BOARD, HttpMethod.POST);
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
    public List<MondayGroupResult> queryGroupByBoardId(String boardId) {

        //替换语句中的id 参数
        String graphGql = MondayQueryGqlConstant.QUERY_GROUP.replace(MondayQueryGqlConstant.BOARD_PARAMETER, boardId);

        System.out.println("--------------------------"+graphGql+"--------------------------------");

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

        log.info("--------------------------"+ MondayQueryGqlConstant.QUERY_TAGS+"--------------------------------");

        String mondayJson = getMondayData(MondayQueryGqlConstant.QUERY_TAGS, HttpMethod.POST);
        log.info("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);
        return mondayDataResult.getTags();
    }

    /**
     * 查询该板子所有列
     *
     * @return
     */
    @Override
    public List<MondayColumnResult> queryColumnResult(String boardId) {
        String graphGql = MondayQueryGqlConstant.QUERY_GROUP.replace(MondayQueryGqlConstant.QUERY_COLUMN, boardId);


        log.info("--------------------------"+ graphGql+"--------------------------------");
        String mondayJson = getMondayData(graphGql, HttpMethod.POST);
        log.info("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        List<MondayBoardResult> mondayBoardResultList = JSON.parseArray(mondayGeneralResult.getData(), MondayBoardResult.class);
        return mondayBoardResultList.get(0).getColumns();
    }

    /**
     * 数据保存
     *
     * @param enter
     * @return
     */
    @Override
    public MondayCreateResult mutationData(MondayMutationDataEnter enter) {
        //替换语句中的id 参数
        String graphGql = MondayQueryGqlConstant.MUTATION_DATA
                .replace(MondayQueryGqlConstant.BOARD_PARAMETER, enter.getBoardId())
                .replace(MondayQueryGqlConstant.CREATE_BELONG_GROUP, enter.getGroupId().toString())
                .replace(MondayQueryGqlConstant.CREATE_ITEM_NAME, enter.getItemName().toString())
                .replace(MondayQueryGqlConstant.CREATE_COLUMN_VALUES, enter.getColumnValues());

        System.out.println("--------------------------"+graphGql+"--------------------------------");


        String mondayJson = getMondayData(graphGql, HttpMethod.POST);
        System.out.println("---------------{" + mondayJson + "}---------");
        MondayGeneralResult mondayGeneralResult = JSON.parseObject(mondayJson, MondayGeneralResult.class);
        MondayDataResult mondayDataResult = JSON.parseObject(mondayGeneralResult.getData(), MondayDataResult.class);

        log.info("-----------------------------------数据插入成功-----------------------------------");
        return mondayDataResult.getCreate_item();
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
