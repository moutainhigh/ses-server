package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteColour;
import com.redescooter.ses.service.hub.source.website.dao.SiteColourMapper;
import com.redescooter.ses.service.hub.source.website.service.base.SiteColourService;

@Service
@DS("website")
public class SiteColourServiceImpl extends ServiceImpl<SiteColourMapper, SiteColour> implements SiteColourService {

}

