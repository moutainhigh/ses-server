package com.redescooter.ses.web.ros.service.specificat.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatDefService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatDefService;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatDefEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassNameSpecificatDefServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:50
 * @Version V1.0
 **/
@Service
public class SpecificatDefServiceImpl implements SpecificatDefService {

    @Autowired
    private OpeSpecificatDefService opeSpecificatDefService;

    @Reference
    private IdAppService idAppService;

    @Override
    public void saveSpecificatDef(List<SpecificatDefEnter> defEnterList,Long userId) {
        List<OpeSpecificatDef> list = new ArrayList<>();
        for (SpecificatDefEnter defEnter : defEnterList) {
            OpeSpecificatDef def = new OpeSpecificatDef();
            def.setDefName(defEnter.getDefName());
            def.setDefValue(defEnter.getDefValue());
            def.setSpecificatId(defEnter.getSpecificatId());
            def.setCreatedBy(userId);
            def.setCreatedTime(new Date());
            def.setUpdatedBy(userId);
            def.setUpdatedTime(new Date());
            def.setDr(0);
            def.setId(idAppService.getId(SequenceName.OPE_SPECIFICAT_DEF));
            list.add(def);
        }
        opeSpecificatDefService.saveOrUpdateBatch(list);
    }


    @Override
    @Transactional
    public void deleSpecificatDef(Long specificatId) {
        QueryWrapper<OpeSpecificatDef> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatDef.COL_SPECIFICAT_ID,specificatId);
        List<OpeSpecificatDef> list = opeSpecificatDefService.list(qw);
        if(CollectionUtils.isNotEmpty(list)){
            opeSpecificatDefService.removeByIds(list.stream().map(OpeSpecificatDef::getId).collect(Collectors.toList()));
        }
    }


    @Override
    public List<OpeSpecificatDef> getDef(Long specificatId) {
        List<OpeSpecificatDef> list = new ArrayList<>();
        QueryWrapper<OpeSpecificatDef> qw = new QueryWrapper<>();
        qw.eq(OpeSpecificatDef.COL_SPECIFICAT_ID,specificatId);
        list = opeSpecificatDefService.list(qw);
        return list;
    }
}
