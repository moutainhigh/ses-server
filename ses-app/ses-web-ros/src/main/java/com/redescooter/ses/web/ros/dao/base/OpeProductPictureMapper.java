package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeProductPictureMapper extends BaseMapper<OpeProductPicture> {
    int updateBatch(List<OpeProductPicture> list);

    int batchInsert(@Param("list") List<OpeProductPicture> list);

    int insertOrUpdate(OpeProductPicture record);

    int insertOrUpdateSelective(OpeProductPicture record);
}