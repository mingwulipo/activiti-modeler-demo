package com.test.activiti.controller;

import com.test.activiti.model.vo.TaskVO;
import com.test.activiti.service.TaskSumService;
import com.test.activiti.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipo
 * @date 2020/7/17
 */

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskSumService taskSumService;

    @RequestMapping("/list")
    public Object list() {
        String loginUserId = UserUtil.loginUserId;
        List<Task> list = taskService.createTaskQuery().taskCandidateUser(loginUserId).orderByTaskCreateTime().desc().list();
        list.forEach(task -> taskService.claim(task.getId(), loginUserId));

        List<Task> myList = taskService.createTaskQuery().taskAssignee(loginUserId).orderByTaskCreateTime().desc().list();
        List<TaskVO> taskVOS = new ArrayList<>();
        myList.forEach(task -> {
            taskService.claim(task.getId(), loginUserId);
            TaskVO vo = new TaskVO();
            vo.setTaskId(task.getId());
            vo.setTaskKey(task.getTaskDefinitionKey());
            vo.setTaskName(task.getName());
            vo.setCreateTime(task.getCreateTime());
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
            vo.setProcessDefinitionKey(processDefinition.getKey());
            vo.setProcessDefinitionName(processDefinition.getName());
            taskVOS.add(vo);
        });

        return taskVOS;
    }

    @RequestMapping("/complete")
    public Object complete(String taskId) {
        taskService.complete(taskId);
        return "ok";
    }

    @PostMapping("/allJob")
    public Object allJob() {
        return taskSumService.allJob();
    }

}
