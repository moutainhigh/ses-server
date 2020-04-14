package com.redescooter.ses.mobile.rps.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.service.RpsHeadService;
import com.redescooter.ses.mobile.rps.vo.materialqc.RpsHeadResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:RpsHeadServiceImpl
 * @description: RpsHeadServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:46
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RpsHeadServiceImpl implements RpsHeadService {
    /**
     * rps 首页数据
     *
     * @param enter
     * @return
     */
    @Override
    public RpsHeadResult rpsHead(GeneralEnter enter) {
        return null;
    }
}
