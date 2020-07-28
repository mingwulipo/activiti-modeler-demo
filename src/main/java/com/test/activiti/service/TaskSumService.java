package com.test.activiti.service;

import com.test.activiti.dao.TaskSumMapper;
import com.test.activiti.entity.TaskSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lipo
 * @date 2020/7/27
 */
@Service
public class TaskSumService {
    @Autowired
    private TaskSumMapper taskSumMapper;

    public Object allJob() {
        List<TaskSum> taskSums = taskSumMapper.allJob();
        return taskSums;
    }
}
