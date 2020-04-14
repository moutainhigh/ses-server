package com.redescooter.ses.mobile.rps.service.impl.purchasinwh.impl;

import cn.hutool.db.Session;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.dao.purchasinwh.PurchasPutStorageMapper;
import com.redescooter.ses.mobile.rps.service.impl.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.PutStorageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
class PurchasPutStorageServiceImpl implements PurchasPutStroageService {


    @Autowired
    private PurchasPutStorageMapper purchasPutStorageMapper;

    @Reference
    private IdAppService idAppService;


    @Override
    public List<PutStorageResult> purchasPutStroageList() {

        return  purchasPutStorageMapper.putStorageResult();
    }
}
