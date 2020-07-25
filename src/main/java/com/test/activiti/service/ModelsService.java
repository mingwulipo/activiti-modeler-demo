package com.test.activiti.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.activiti.util.CheckParamsUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Service
public class ModelsService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    public void saveModel(String modelId, String name, String description, String json_xml, String svg_xml) throws Exception {

        //校验非空
        CheckParamsUtil.notEmpty(new Object[] {modelId, name, json_xml});

        Model model = repositoryService.getModel(modelId);

        //校验非重复
        Model nameModel = repositoryService.createModelQuery().modelName(name).singleResult();
        if (nameModel != null && !nameModel.getId().equals(model.getId())) {
            throw new RuntimeException("模型名称重复");
        }

        //流程图的key和name都在json_xml中，json解析即可
        //   * {"resourceId":"42505","properties":{"process_id":"process","name":"办理流程",...
        JsonNode modelNode = new ObjectMapper().readTree(json_xml.getBytes("utf-8"));
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(CollectionUtils.isEmpty(bpmnModel.getProcesses())){
            throw new RuntimeException("数据模型不符要求，请至少设计一条主线流程");
        }
        Process process = bpmnModel.getProcesses().get(0);
        Model keyModel = repositoryService.createModelQuery().modelKey(process.getId()).singleResult();
        if (keyModel != null && !keyModel.getId().equals(modelId)) {
            throw new RuntimeException("流程key重复");
        }

        ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

        modelJson.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        model.setMetaInfo(modelJson.toString());
        model.setName(name);

        repositoryService.saveModel(model);

        repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

        InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
        TranscoderInput input = new TranscoderInput(svgStream);

        PNGTranscoder transcoder = new PNGTranscoder();
        // Setup output
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outStream);

        // Do the transformation
        transcoder.transcode(input, output);
        final byte[] result = outStream.toByteArray();
        repositoryService.addModelEditorSourceExtra(model.getId(), result);
        outStream.close();

    }
}
