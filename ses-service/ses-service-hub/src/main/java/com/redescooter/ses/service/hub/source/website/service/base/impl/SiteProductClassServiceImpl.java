package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductClass;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductClassMapper;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductClassService;

@Service
@DS("website")
public class SiteProductClassServiceImpl extends ServiceImpl<SiteProductClassMapper, SiteProductClass> implements SiteProductClassService {

}




