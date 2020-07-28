package com.test.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.activiti.entity.TaskSum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务节点 Mapper 接口
 * </p>
 *
 * @author lipo
 * @since 2020-07-20
 */
@Mapper
public interface TaskSumMapper extends BaseMapper<TaskSum> {

    List<TaskSum> allJob();

    TaskSum getByKey(@Param("taskKey") String taskKey);
}
