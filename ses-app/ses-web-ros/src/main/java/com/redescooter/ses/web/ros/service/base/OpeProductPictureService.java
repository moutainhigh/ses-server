package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductPicture;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductPictureService extends IService<OpeProductPicture> {


    int updateBatch(List<OpeProductPicture> list);

    int batchInsert(List<OpeProductPicture> list);

    int insertOrUpdate(OpeProductPicture record);

    int insertOrUpdateSelective(OpeProductPicture record);

}



