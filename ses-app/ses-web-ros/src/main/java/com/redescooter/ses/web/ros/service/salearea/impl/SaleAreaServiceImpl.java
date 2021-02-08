package com.redescooter.ses.web.ros.service.salearea.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSaleAreaMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleArea;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.service.base.OpeSaleAreaService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;
import com.redescooter.ses.web.ros.service.salearea.SaleAreaService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.salearea.RoleAreaEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaOpEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaSaveEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleCityTreeResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassNameSaleAreaServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/3 14:04
 * @Version V1.0
 **/
@Service
@Slf4j
public class SaleAreaServiceImpl implements SaleAreaService {


    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeSaleAreaMapper opeSaleAreaMapper;

    @Autowired
    private OpeSaleAreaService opeSaleAreaService;

    @Autowired
    private OpeSysRoleSalesCidyService opeSysRoleSalesCidyService;

    @Override
    public GeneralResult saleAreaSave(SaleAreaSaveEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        OpeSaleArea saleArea = new OpeSaleArea();
        BeanUtils.copyProperties(enter,saleArea);
        saleArea.setId(idAppService.getId(SequenceName.OPE_SALE_AREA));
        saleArea.setUpdatedBy(enter.getUserId());
        saleArea.setUpdateTime(new Date());
        opeSaleAreaMapper.insert(saleArea);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult saleAreaDetele(SaleAreaOpEnter enter) {
        opeSaleAreaService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<SaleCityTreeResult> roleAreaAuthShow(RoleOpEnter enter) {
        List<SaleCityTreeResult> list = new ArrayList<>();
        List<OpeSaleArea> areaList = opeSaleAreaService.list();
        if(CollectionUtils.isNotEmpty(areaList)){
            for (OpeSaleArea area : areaList) {
                SaleCityTreeResult result = new SaleCityTreeResult();
                BeanUtils.copyProperties(area,result);
                result.setSaleCityId(area.getId());
                result.setPId(area.getPId());
                list.add(result);
            }
            // 查找当前角色已经有的区域权限
            QueryWrapper<OpeSysRoleSalesCidy> qw = new QueryWrapper<>();
            qw.eq(OpeSysRoleSalesCidy.COL_ROLE_ID,enter.getId());
            List<OpeSysRoleSalesCidy>  roleSalesCidyList = opeSysRoleSalesCidyService.list(qw);
            if(CollectionUtils.isNotEmpty(roleSalesCidyList)){
                List<Long> cityIds = roleSalesCidyList.stream().map(OpeSysRoleSalesCidy::getCityId).collect(Collectors.toList());
                for (SaleCityTreeResult result : list) {
                    if(cityIds.contains(result.getSaleCityId())){
                        result.setChecked(true);
                    }
                }
            }
        }
        return TreeUtil.build(list, Constant.AREA_TREE_ROOT_ID);
    }


    @Override
    @Transactional
    public GeneralResult roleAreaAuth(RoleAreaEnter enter) {
        // 销售区域  先删除当前角色下面的已经有的区域
        QueryWrapper<OpeSysRoleSalesCidy> qw = new QueryWrapper<>();
        qw.eq(OpeSysRoleSalesCidy.COL_ROLE_ID,enter.getRoleId());
        opeSysRoleSalesCidyService.remove(qw);
        // 再保存新的区域
        if(!Strings.isNullOrEmpty(enter.getSaleCityIds())){
           List<OpeSysRoleSalesCidy>  update = new ArrayList<>();
            for (String s : enter.getSaleCityIds().split(",")) {
                OpeSysRoleSalesCidy salesCidy = new OpeSysRoleSalesCidy();
                salesCidy.setCityId(Long.valueOf(s));
                salesCidy.setRoleId(enter.getRoleId());
                update.add(salesCidy);
            }
            opeSysRoleSalesCidyService.batchInsert(update);
        }
        return new GeneralResult(enter.getRequestId());
    }


}
