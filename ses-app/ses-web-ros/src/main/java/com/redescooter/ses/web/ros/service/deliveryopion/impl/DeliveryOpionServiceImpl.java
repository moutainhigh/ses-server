package com.redescooter.ses.web.ros.service.deliveryopion.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeDeliveryOption;
import com.redescooter.ses.web.ros.service.base.OpeDeliveryOptionService;
import com.redescooter.ses.web.ros.service.deliveryopion.DeliveryOpionService;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionEditEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeliveryOpionServiceImpl implements DeliveryOpionService {

    @Autowired
    private OpeDeliveryOptionService deliveryOptionService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 保存取货配置
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(DeliveryOptionSaveEnter enter) {

        OpeDeliveryOption saveVO = new OpeDeliveryOption();
        saveVO.setId(idAppService.getId(SequenceName.OPE_DELIVERY_OPTION));
        saveVO.setDr(Constant.DR_FALSE);
        saveVO.setTenantId(new Long("0"));
        saveVO.setUserId(new Long("0"));
        saveVO.setStatus(0);
        saveVO.setOptionCode(SesStringUtils.getlinkNo());
        saveVO.setOptionNeme(enter.getOptionNeme());
        saveVO.setPrice(enter.getPrice());
        saveVO.setMemo(enter.getMemo());
        saveVO.setCreatedBy(enter.getUserId());
        saveVO.setCreatedTime(new Date());
        saveVO.setUpdatedBy(enter.getUserId());
        saveVO.setUpdatedTime(new Date());
        saveVO.setDef1(enter.getDef1());

        deliveryOptionService.save(saveVO);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 查询取货配置列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeliveryOptionSaveResult> list(GeneralEnter enter) {
        List<DeliveryOptionSaveResult> results = new ArrayList<>();
        DeliveryOptionSaveResult optionSaveResult = null;

        List<OpeDeliveryOption> lists = deliveryOptionService.list(new LambdaQueryWrapper<OpeDeliveryOption>()
                .eq(OpeDeliveryOption::getDr, Constant.DR_FALSE));

        if (!CollectionUtils.isEmpty(lists)) {

            for (OpeDeliveryOption option : lists) {
                optionSaveResult = new DeliveryOptionSaveResult();
                optionSaveResult.setId(option.getId());
                optionSaveResult.setStatus(option.getStatus());
                optionSaveResult.setOptionNeme(option.getOptionNeme());
                optionSaveResult.setPrice(option.getPrice());
                optionSaveResult.setCreatedTime(option.getCreatedTime());
                if (StringUtils.isNotEmpty(option.getMemo())) {
                    optionSaveResult.setMemo(option.getMemo());
                }
                if (StringUtils.isNotEmpty(option.getDef1())) {
                    optionSaveResult.setDef1(option.getDef1());
                }
                results.add(optionSaveResult);
            }
        }

        return results;
    }

    /**
     * 编辑取货配置
     *
     * @param enter
     * @param id
     * @return
     */
    @Transactional
    @Override
    public GeneralResult edit(DeliveryOptionEditEnter enter, Long id) {

        OpeDeliveryOption editVO = new OpeDeliveryOption();
        editVO.setId(id);
        editVO.setOptionNeme(enter.getOptionNeme());
        editVO.setPrice(enter.getPrice());
        editVO.setMemo(enter.getMemo());
        editVO.setDef1(enter.getDef1());
        editVO.setUpdatedBy(enter.getUserId());
        editVO.setUpdatedTime(new Date());
        deliveryOptionService.updateById(editVO);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取取货配置详情
     *
     * @param enter
     * @param id
     * @return
     */
    @Override
    public DeliveryOptionSaveResult details(GeneralEnter enter, Long id) {
        DeliveryOptionSaveResult result = new DeliveryOptionSaveResult();

        OpeDeliveryOption deliveryOption = deliveryOptionService.getOne(new LambdaQueryWrapper<OpeDeliveryOption>()
                .eq(OpeDeliveryOption::getDr, Constant.DR_FALSE)
                .eq(OpeDeliveryOption::getId, id)
                .last("limit 1"));

        if (deliveryOption != null) {
            result.setId(deliveryOption.getId());
            result.setStatus(deliveryOption.getStatus());
            result.setOptionNeme(deliveryOption.getOptionNeme());
            result.setPrice(deliveryOption.getPrice());
            result.setMemo(deliveryOption.getMemo() == null ? "" : deliveryOption.getMemo());
            result.setDef1(deliveryOption.getDef1() == null ? "" : deliveryOption.getDef1());
            result.setCreatedTime(deliveryOption.getCreatedTime());
            result.setRequestId(enter.getRequestId());
        }

        return result;
    }
}
