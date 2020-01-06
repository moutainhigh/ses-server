package com.redescooter.ses.service.hub.common;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.service.corporate.CorporateDriverService;
import com.redescooter.ses.service.hub.source.corporate.service.base.CorDriverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 10:48 上午
 * @ClassName: CorporateDriverServiceimpl
 * @Function: TODO
 */
@Slf4j
@Service
public class CorporateDriverServiceimpl implements CorporateDriverService {

    @Autowired
    private CorDriverService driverService;

    /**
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateDriverDef1(List<IdEnter> enter) {


        return null;
    }
}
