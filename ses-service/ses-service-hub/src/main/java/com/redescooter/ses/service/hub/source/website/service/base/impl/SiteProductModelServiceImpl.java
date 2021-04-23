package com.redescooter.ses.service.hub.source.website.service.base.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductModel;
import com.redescooter.ses.service.hub.source.website.dao.SiteProductModelMapper;
import com.redescooter.ses.service.hub.source.website.service.base.SiteProductModelService;
@Service
@DS("website")
public class SiteProductModelServiceImpl extends ServiceImpl<SiteProductModelMapper, SiteProductModel> implements SiteProductModelService{

}
