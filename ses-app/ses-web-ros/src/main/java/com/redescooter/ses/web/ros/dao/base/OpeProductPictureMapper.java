package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPicture;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductPictureMapper extends BaseMapper<OpeProductPicture> {
    int updateBatch(List<OpeProductPicture> list);

    int batchInsert(@Param("list") List<OpeProductPicture> list);

    int insertOrUpdate(OpeProductPicture record);

    int insertOrUpdateSelective(OpeProductPicture record);
}