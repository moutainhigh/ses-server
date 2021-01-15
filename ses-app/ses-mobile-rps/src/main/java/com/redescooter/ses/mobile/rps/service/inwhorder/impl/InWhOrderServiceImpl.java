package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/15 18:09
 */
@Slf4j
@Service
public class InWhOrderServiceImpl implements InWhOrderService {

    @Resource
    private InWhOrderMapper inWhOrderMapper;


    @Override
    public Map<Integer, Integer> getInWarehouseOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseOrderTypeCount();
        /**
         * {orderType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }

        return map;
    }

    @Override
    public Map<Integer, Integer> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseTypeCount(paramDTO);
        /**
         * {inWhType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult:: getTotalCount)
        );

        for (InWhTypeEnums item : InWhTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }

        return map;
    }

    @Override
    public PageResult<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO) {
        int count = inWhOrderMapper.countByInWhOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, inWhOrderMapper.getInWarehouseOrderList(paramDTO));
    }

    @Override
    public GeneralResult startQc(IdEnter enter) {
        return null;
    }

    @Override
    public InWhOrderDetailDTO getInWarehouseOrderDetailById(IdEnter enter) {
        return null;
    }

}
