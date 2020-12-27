package com.redescooter.ses.web.ros.service.wms.cn.china.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.service.wms.cn.china.ChinaWhService;
import com.redescooter.ses.web.ros.vo.wms.cn.china.ChinaInOrOutCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.StockCountResult;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ChinaWhServiceImpl
 * @description: ChinaWhServiceImpl
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/27 16:09
 */
@Service
public class ChinaWhServiceImpl implements ChinaWhService {


    @Override
    public List<ChinaInOrOutCountResult> inOrOutCount(GeneralEnter enter) {
        List<ChinaInOrOutCountResult> list = new ArrayList<>();
        // 今日入库
        ChinaInOrOutCountResult inWh = new ChinaInOrOutCountResult();
        inWh.setType(1);
        list.add(inWh);

        // 今日出库
        ChinaInOrOutCountResult outWh = new ChinaInOrOutCountResult();
        outWh.setType(2);
        list.add(outWh);
        return list;
    }


    @Override
    public List<StockCountResult> stockCount(GeneralEnter enter) {
        List<StockCountResult> list = new ArrayList<>();
        // 成品库
        StockCountResult finishWh = new StockCountResult();
        finishWh.setType(1);
        list.add(finishWh);

        // 原料库
        StockCountResult materialWh = new StockCountResult();
        materialWh.setType(2);
        list.add(materialWh);

        // 不合格品库
        StockCountResult unqualifiedWh = new StockCountResult();
        unqualifiedWh.setType(3);
        list.add(unqualifiedWh);
        return list;
    }
}
