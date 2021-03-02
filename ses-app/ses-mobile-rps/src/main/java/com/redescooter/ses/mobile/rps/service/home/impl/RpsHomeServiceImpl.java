package com.redescooter.ses.mobile.rps.service.home.impl;

import com.redescooter.ses.api.common.enums.rps.RpsOrderTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderMapper;
import com.redescooter.ses.mobile.rps.service.home.RpsHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author assert
 * @date 2021/2/3 9:42
 */
@Slf4j
@Service
public class RpsHomeServiceImpl implements RpsHomeService {

    @Autowired
    private QcOrderMapper qcOrderMapper;

    @Autowired
    private CombinationOrderMapper combinationOrderMapper;

    @Autowired
    private InWhOrderMapper inWhOrderMapper;

    @Autowired
    private OutWarehouseOrderMapper outWarehouseOrderMapper;

    @Autowired
    private EntrustOrderMapper entrustOrderMapper;

    @Override
    public Map<Integer, Integer> getAllOrderCount(GeneralEnter enter) {
        /**
         * {orderType, totalCount}
         */
        Map<Integer, Integer> map = new HashMap<>();
        map.put(RpsOrderTypeEnum.QC_ORDER.getType(), qcOrderMapper.allCountByQcOrder());
        map.put(RpsOrderTypeEnum.COMBINATION_ORDER.getType(), combinationOrderMapper.allCountByCombinationOrder());
        map.put(RpsOrderTypeEnum.IN_WH_ORDER.getType(), inWhOrderMapper.allCountByInWhOrder());
        map.put(RpsOrderTypeEnum.OUT_WH_ORDER.getType(), outWarehouseOrderMapper.allCountByOutWhOrder());
        map.put(RpsOrderTypeEnum.ENTRUST_ORDER.getType(), entrustOrderMapper.allCountByEntrustOrder());
        return map;
    }

}
