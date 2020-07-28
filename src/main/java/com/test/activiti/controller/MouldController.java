package com.test.activiti.controller;

import com.test.activiti.model.request.MouldCreateRequest;
import com.test.activiti.service.ModelsService;
import com.test.activiti.service.MouldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lipo
 * @date 2020/7/27
 */
@RestController
@Api(tags = "业务模型")
@RequestMapping("/mould")
public class MouldController {
    @Autowired
    private MouldService mouldService;
    @Autowired
    private ModelsService modelsService;

    /**
     * 流程走通了，创建，部署，启动，办理任务，都可以。
     * 流程图显示失败，这个无所谓的，前端自己处理展示的。
     * 连线的dockers数组伪造，流程也能走通
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(("创建"))
    @PostMapping("/create")
    public Object create(@RequestBody MouldCreateRequest request) throws Exception {
        return mouldService.create(request);
    }


    /**
     * 发布模型为流程定义
     */
    @PostMapping("/deploy")
    @ApiOperation(("部署"))
    public Object deploy(String modelId) throws Exception {
        return modelsService.deploy(modelId);
    }

}
