package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;

@Transactional
public interface OpeRegionalPriceSheetHistoryService extends IService<OpeRegionalPriceSheetHistory> {


    int updateBatch(List<OpeRegionalPriceSheetHistory> list);

    int batchInsert(List<OpeRegionalPriceSheetHistory> list);

    int insertOrUpdate(OpeRegionalPriceSheetHistory record);

    int insertOrUpdateSelective(OpeRegionalPriceSheetHistory record);

}


