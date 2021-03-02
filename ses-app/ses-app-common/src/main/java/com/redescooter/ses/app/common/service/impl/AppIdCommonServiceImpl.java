package com.redescooter.ses.app.common.service.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.base.AppIDResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.app.common.service.AppIdCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/19 12:04 下午
 * @Description 系统应用服务实现类
 **/

@Slf4j
@Service
public class AppIdCommonServiceImpl implements AppIdCommonService {
    /**
     * 枚举转对象给前端
     *
     * @param enter
     * @return
     */
    @Override
    public List<AppIDResult> list(GeneralEnter enter) {
        List<AppIDResult> listResult = new ArrayList<>();

        for (AppIDEnums item : AppIDEnums.values()) {
            AppIDResult result = AppIDResult.builder()
                    .appId(item.getAppId())
                    .systemId(item.getSystemId())
                    .value(item.getValue())
                    .remark(item.getRemark())
                    .build();

            result.setRequestId(enter.getRequestId());
            listResult.add(result);
        }
        return listResult;
    }
}
