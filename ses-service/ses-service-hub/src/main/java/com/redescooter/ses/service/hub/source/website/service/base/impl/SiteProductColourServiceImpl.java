package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductColour;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductColourMapper;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductColourService;
@Service
@DS("website")
public class SiteProductColourServiceImpl extends ServiceImpl<SiteProductColourMapper, SiteProductColour> implements SiteProductColourService{

}
