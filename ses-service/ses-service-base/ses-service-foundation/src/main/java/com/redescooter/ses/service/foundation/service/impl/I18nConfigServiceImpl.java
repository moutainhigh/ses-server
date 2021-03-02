package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.I18nConfigService;
import com.redescooter.ses.api.foundation.vo.message.DeleteI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigResult;
import com.redescooter.ses.api.foundation.vo.message.PageGetI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.SaveI18nConfigEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.I18nConfigServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaI18nConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * description: I18nConfigServiceImpl
 * author jerry.li
 * create: 2019-05-07 12:18
 */
@Slf4j
@DubboService
public class I18nConfigServiceImpl implements I18nConfigService {

    @Autowired
    private I18nConfigServiceMapper i18nConfigServiceMapper;

    @Autowired
    private PlaI18nConfigMapper i18nConfigMapper;

    @DubboReference
    private IdAppService idSerService;

    /**
     * 多维度 获取多语言配置i18n
     *
     * @param enter
     * @return
     */
    @Override
    public List<GetI18nConfigResult> getI18nConfig(GetI18nConfigEnter enter) {
        return i18nConfigServiceMapper.getI18nConfig(enter);
    }

    /**
     * 分页 多维度 获取多语言配置i18n
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<GetI18nConfigResult> getI18nConfigOfPage(PageGetI18nConfigEnter enter) {

        //非分组入参查询总数
        GetI18nConfigEnter enterRows = new GetI18nConfigEnter();
        BeanUtils.copyProperties(enter, enterRows);

        //查询所有数据的条数
        Integer totalRows = i18nConfigServiceMapper.getI18nConfigTotalRows(enterRows);

        if (totalRows == null) {
            return PageResult.createZeroRowResult(enter);
        }
        //查询当前页的数据
        List<GetI18nConfigResult> i18nConfigResultList = i18nConfigServiceMapper.getI18nConfigOfPage(enter);

        if (i18nConfigResultList.isEmpty()) {
            return PageResult.create(enter, totalRows, Collections.emptyList());
        }

        return PageResult.create(enter, totalRows, i18nConfigResultList);
    }

    /**
     * 删除多语言配置i18n
     *
     * @param deleteI18nConfigEnter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(DeleteI18nConfigEnter deleteI18nConfigEnter) {

        i18nConfigMapper.deleteById(deleteI18nConfigEnter.getId());

    }

    /**
     * 添加多语言配置i18n
     *
     * @param saveI18nConfigEnter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SaveI18nConfigEnter saveI18nConfigEnter) {
        PlaI18nConfig i18nConfigBase = new PlaI18nConfig();
        BeanUtils.copyProperties(saveI18nConfigEnter, i18nConfigBase);
        //做插入操作
        if ((saveI18nConfigEnter.getId() == null) || (saveI18nConfigEnter.getId() == 0)) {
            i18nConfigBase.setId(idSerService.getId(SequenceName.PLA_I18NCONFIG));
            i18nConfigBase.setDr(0);
            i18nConfigBase.setCreatedTime(new Date());
            i18nConfigBase.setDeleted(Boolean.FALSE);
            i18nConfigMapper.insert(i18nConfigBase);
        } else {
            //做更新操作
            i18nConfigBase.setUpdateTime(new Date());
            i18nConfigMapper.updateById(i18nConfigBase);
        }
    }
}
