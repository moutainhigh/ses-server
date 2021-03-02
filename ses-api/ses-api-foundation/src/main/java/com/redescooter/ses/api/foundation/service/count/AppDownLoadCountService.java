package com.redescooter.ses.api.foundation.service.count;

import com.redescooter.ses.api.common.vo.count.AppDownLoadCountResult;
import com.redescooter.ses.api.common.vo.count.OrderCountEnter;

import java.util.List;

/**
 * @ClassName:AppDownLoadCountService
 * @description: AppDownLoadCountService
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/17 10:27
 */
public interface AppDownLoadCountService {

    /**
     * app下载量的统计
     * @param enter
     * @return
     */
    List<AppDownLoadCountResult> appDownLoadCount(OrderCountEnter enter);


}
