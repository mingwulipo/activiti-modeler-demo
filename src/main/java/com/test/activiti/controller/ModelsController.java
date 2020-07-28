package com.test.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.activiti.service.ModelsService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 路径用models和activiti自带的model路径区分开
 */
@Slf4j
@RestController
@RequestMapping("/models")
public class ModelsController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ModelsService modelsService;

    /**
     * 新建一个空模型
     */
    @RequestMapping("/create")
    public void newModel(HttpServletResponse response) throws IOException {
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);

        String id = model.getId();
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");

        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

        response.sendRedirect("/modeler.html?modelId=" + id);
    }

    /**
     * 获取所有模型
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object modelList() {
        List<Model> list = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        return list;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteOne(String modelId){
        repositoryService.deleteModel(modelId);
        return "ok";
    }

    /**
     * 发布模型为流程定义
     * 发布后，有问题：任务节点的名称乱码
     */
    @RequestMapping("/deploy")
    @ResponseBody
    public Object deploy(String modelId) throws Exception {
        return modelsService.deploy(modelId);
    }

}
