package com.redescooter.ses.service.foundation.service.impl.count;

import com.redescooter.ses.api.common.vo.count.AppDownLoadCountResult;
import com.redescooter.ses.api.common.vo.count.OrderCountEnter;
import com.redescooter.ses.api.foundation.service.count.AppDownLoadCountService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:AppDownLoadCountServiceImpl
 * @description: AppDownLoadCountServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/17 10:49
 */
@Service
public class AppDownLoadCountServiceImpl implements AppDownLoadCountService {


    /**
     * app下载量的统计
     * @param enter
     * @return
     */
    @Override
    public List<AppDownLoadCountResult> appDownLoadCount(OrderCountEnter enter) {
        List<AppDownLoadCountResult> resultList = new ArrayList<>();
        AppDownLoadCountResult ios = new AppDownLoadCountResult();
        ios.setType(1);
        ios.setDownloadTotal(582);
        ios.setPercentRatio("62.45%");
        resultList.add(ios);
        AppDownLoadCountResult android = new AppDownLoadCountResult();
        android.setType(2);
        android.setDownloadTotal(350);
        android.setPercentRatio("37.55%");
        resultList.add(android);
        // todo 真实的数据 可能要对接友盟的API 暂时先返回一些假数据  后面对接时 再返回真实的数据
        return resultList;
    }
}
