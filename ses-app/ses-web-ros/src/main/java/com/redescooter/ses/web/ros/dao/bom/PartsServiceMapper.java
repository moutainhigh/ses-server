package com.redescooter.ses.web.ros.dao.bom;

import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsDto;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PartsServiceMapper
 * @Author Jerry
 * @date 2020/02/26 22:34
 * @Description:
 */
public interface PartsServiceMapper {

    /**
     * 列表计数
     *
     * @param enter
     * @return
     */
    int listCount(PartListEnter enter);

    /**
     * 列表展示
     *
     * @param enter
     * @return
     */
    List<DetailsPartsResult> list(PartListEnter enter);

    /**
     * 查询部品下的历史记录返回
     *
     * @param partsId
     * @return
     */
    List<HistoryPartsDto> historyList(@Param("partsId") long partsId, @Param("userId") long userId);

}
