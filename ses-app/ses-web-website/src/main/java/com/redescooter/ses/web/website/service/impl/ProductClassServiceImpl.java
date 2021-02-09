package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.code.MainCode;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.service.ProductClassService;
import com.redescooter.ses.web.website.service.base.SiteProductClassService;
import com.redescooter.ses.web.website.vo.product.AddProductClassEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductClassEnter;
import com.redescooter.ses.web.website.vo.product.ProductClassDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/5 4:50 下午
 * @Description 产品种类服务实现类
 **/

@Slf4j
@Service
public class ProductClassServiceImpl implements ProductClassService {

    @Autowired
    private SiteProductClassService siteProductClassService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建产品种类
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult addProductClass(AddProductClassEnter enter) {

        SiteProductClass addProductClassVO = new SiteProductClass();
        addProductClassVO.setId(idAppService.getId(SequenceName.SITE_PRODUCT_CLASS));
        addProductClassVO.setDr(Constant.DR_FALSE);
        addProductClassVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addProductClassVO.setProductClassName(enter.getProductClassName());
        addProductClassVO.setProductClassCode(new StringBuffer().append("CA_").append(MainCode.generateByShuffle()).toString());
        addProductClassVO.setCnName(enter.getCnName());
        addProductClassVO.setFrName(enter.getFrName());
        addProductClassVO.setEnName(enter.getEnName());

        if (StringUtils.isNotBlank(enter.getRemark())) {
            addProductClassVO.setRemark(enter.getRemark());
        }
        addProductClassVO.setRevision(0);
        addProductClassVO.setSynchronizeFlag(false);
        addProductClassVO.setCreatedBy(enter.getUserId());
        addProductClassVO.setCreatedTime(new Date());
        addProductClassVO.setUpdatedBy(enter.getUserId());

        siteProductClassService.save(addProductClassVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 编辑产品种类
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult modityProductClass(ModityProductClassEnter enter) {

        SiteProductClass modityProductClassVO = new SiteProductClass();
        BeanUtils.copyProperties(enter, modityProductClassVO);

        siteProductClassService.update(modityProductClassVO, new UpdateWrapper<SiteProductClass>().eq(SiteProductClass.COL_ID, enter.getId()));

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 移除产品种类
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult removeProductClass(IdEnter enter) {
        siteProductClassService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获取产品种类详情
     *
     * @param enter
     */
    @Override
    public ProductClassDetailsResult getProductClassDetails(IdEnter enter) {

        SiteProductClass productClass = siteProductClassService.getById(enter.getId());
        ProductClassDetailsResult result = new ProductClassDetailsResult();

        if (productClass != null) {
            BeanUtils.copyProperties(productClass, result);
            result.setRequestId(enter.getRequestId());
        }

        return result;
    }

    /**
     * 获取产品种类列表
     *
     * @param enter
     */
    @Override
    public List<ProductClassDetailsResult> getProductClassList(GeneralEnter enter) {

        List<ProductClassDetailsResult> resultList = new ArrayList<>();
        List<SiteProductClass> list = siteProductClassService.list(new QueryWrapper<SiteProductClass>().eq(SiteProductClass.COL_DR, 0));

        if (list.size() > 0) {
            list.forEach(pc -> {
                ProductClassDetailsResult result = new ProductClassDetailsResult();
                BeanUtils.copyProperties(pc, result);
                resultList.add(result);
            });
        }
        return resultList;
    }
}
