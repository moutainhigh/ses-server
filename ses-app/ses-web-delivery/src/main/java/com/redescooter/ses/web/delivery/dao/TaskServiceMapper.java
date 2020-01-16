package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskListEnter;
import com.redescooter.ses.web.delivery.vo.task.TaskResult;

import java.util.List;

/**
 * @ClassName:TaskService
 * @description: TaskService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/16 09:52
 */
public interface TaskServiceMapper {

    /**
     * task 状态 统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * task 统计
     *
     * @param enter
     * @return
     */
    int taskCount(TaskListEnter enter);

    /**
     * 任务条目
     *
     * @param enter
     * @return
     */
    List<TaskResult> taskList(TaskListEnter enter);

    /**
     * 任务详情
     *
     * @param enter
     * @return
     */
    TaskResult detail(IdEnter enter);
}
