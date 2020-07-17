package com.test.activiti.controller;

import com.test.activiti.model.DeploymentVO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/deployment")
public class DeploymentController {

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 获取所有部署
     */
    @RequestMapping("/list")
    public Object list() {
        List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
        List<DeploymentVO> vos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(deployment -> {
                DeploymentVO vo = new DeploymentVO();
                vo.setId(deployment.getId());
                vo.setName(deployment.getName());
                vo.setDeploymentTime(deployment.getDeploymentTime());
                vos.add(vo);
            });
        }
        return vos;
    }

    /**
     * 删除部署
     * 流程部署act_re_deployment和流程定义act_re_procdef，字节数组表act_ge_bytearray都删除
     * @param deploymentId
     * @return
     */
    @RequestMapping("/delete")
    public Object deleteOne(String deploymentId){
        repositoryService.deleteDeployment(deploymentId);
        return "ok";
    }

}
