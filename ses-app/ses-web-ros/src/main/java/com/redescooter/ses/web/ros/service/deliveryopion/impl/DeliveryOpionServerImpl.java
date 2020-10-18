package com.redescooter.ses.web.ros.service.deliveryopion.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeDeliveryOptionMapper;
import com.redescooter.ses.web.ros.dm.OpeDeliveryOption;
import com.redescooter.ses.web.ros.service.base.OpeDeliveryOptionService;
import com.redescooter.ses.web.ros.service.deliveryopion.DeliveryOpionServer;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionEditEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeliveryOpionServerImpl implements DeliveryOpionServer {

    @Autowired
    private OpeDeliveryOptionService deliveryOptionService;

    @Reference
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

        OpeDeliveryOption save = new OpeDeliveryOption();
        save.setId(idAppService.getId(SequenceName.OPE_DELIVERY_OPTION));
        save.setDr(Constant.DR_FALSE);
        save.setTenantId(new Long("0"));
        save.setUserId(new Long("0"));
        save.setStatus(0);
        save.setOptionCode(SesStringUtils.getlinkNo());
        save.setOptionNeme(enter.getOptionNeme());
        save.setPrice(enter.getPrice());
        save.setMemo(enter.getMemo());
        save.setCreatedBy(enter.getUserId());
        save.setCreatedTime(new Date());
        save.setUpdatedBy(enter.getUserId());
        save.setUpdatedTime(new Date());
        save.setDef1(enter.getDef1());

        deliveryOptionService.save(save);

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
        List<OpeDeliveryOption> list = deliveryOptionService.list(new LambdaQueryWrapper<OpeDeliveryOption>()
                .eq(OpeDeliveryOption::getDr, Constant.DR_FALSE));

        if (CollectionUtils.isEmpty(list)) {
            return results;
        } else {

            DeliveryOptionSaveResult optionSaveResult = null;
            for (OpeDeliveryOption option : list) {
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

        OpeDeliveryOption edit = new OpeDeliveryOption();
        edit.setId(id);
        edit.setOptionNeme(edit.getOptionNeme());
        edit.setPrice(edit.getPrice());
        edit.setMemo(enter.getMemo());
        edit.setDef1(enter.getDef1());
        deliveryOptionService.updateById(edit);

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
                .eq(OpeDeliveryOption::getId, id));

        if (deliveryOption == null) {
            return result;
        } else {
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
