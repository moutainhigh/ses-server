package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;
import com.redescooter.ses.web.ros.service.sys.SysSalesAreaService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysSalesAreaServiceImpl
 * @Author Jerry
 * @date 2020/03/11 14:06
 * @Description:
 */
@Slf4j
@Service
public class SysSalesAreaServiceImpl implements SysSalesAreaService {

    @Autowired
    private OpeSysRoleSalesCidyService sysRoleSalesCidyService;

    @Reference
    private CityBaseService cityBaseService;

    @Override
    public List<SalesAreaTressResult> list(IdEnter enter) {
        return this.getsaleaAreaList(enter);
    }

    @Override
    public List<SalesAreaTressResult> trees(IdEnter enter) {
        return TreeUtil.build(this.getsaleaAreaList(enter), Constant.AREA_TREE_ROOT_ID);
    }

    private List<SalesAreaTressResult> getsaleaAreaList(IdEnter enter) {
        List<CityResult> list = cityBaseService.list(enter);

        List<SalesAreaTressResult> treeList = new ArrayList<>();

        if (list.size() > 0) {
            list.forEach(li -> {
                SalesAreaTressResult tree = new SalesAreaTressResult();
                tree.setId(li.getId());
                tree.setPId(li.getPId());
                tree.setName(li.getName());
                tree.setCode(li.getCode());
                tree.setChecked(Boolean.FALSE);
                tree.setDisabled(Boolean.TRUE);
                treeList.add(tree);
            });
        }

        QueryWrapper<OpeSysRoleSalesCidy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeSysRoleSalesCidy.COL_ROLE_ID, enter.getId());
        List<OpeSysRoleSalesCidy> salesCidies = sysRoleSalesCidyService.list(queryWrapper);

        if (salesCidies.size() > 0) {
            salesCidies.forEach(city -> {
                treeList.forEach(tr -> {
                    if (city.getCityId() == tr.getId()) {
                        tr.setChecked(Boolean.TRUE);
                    }
                });
            });
        }
        return treeList;
    }
}
