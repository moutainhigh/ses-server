package com.redescooter.ses.web.ros.service.salearea.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSaleAreaMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleArea;
import com.redescooter.ses.web.ros.service.base.OpeSaleAreaService;
import com.redescooter.ses.web.ros.service.salearea.SaleAreaService;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaOpEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleAreaSaveEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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


    @Autowired
    private IdAppService idAppService;

    @Autowired
    private OpeSaleAreaMapper opeSaleAreaMapper;

    @Autowired
    private OpeSaleAreaService opeSaleAreaService;

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


}
