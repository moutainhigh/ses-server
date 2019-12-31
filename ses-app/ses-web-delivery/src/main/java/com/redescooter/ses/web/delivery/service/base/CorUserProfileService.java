package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorUserProfile;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 8:32 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
public interface CorUserProfileService extends IService<CorUserProfile> {


    int updateBatch(List<CorUserProfile> list);

    int batchInsert(List<CorUserProfile> list);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);

}
