/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.rest.editor.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;

import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author Tijs Rademakers
 */
@RestController
@RequestMapping("service")
public class ModelSaveRestResource implements ModelDataJsonConstants {
  
  protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

  @Autowired
  private RepositoryService repositoryService;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
  @ResponseStatus(value = HttpStatus.OK)
  public void saveModel(@PathVariable String modelId
          , String name, String description
          , String json_xml, String svg_xml) {
    try {
      //校验非空
      Model model = repositoryService.getModel(modelId);

      //校验非重复
      Model nameModel = repositoryService.createModelQuery().modelName(name).singleResult();
      if (nameModel != null && !nameModel.getId().equals(model.getId())) {
        throw new RuntimeException("模型名称重复");
      }

      //流程图的key和name都在json_xml中，json解析即可
      //   * {"resourceId":"42505","properties":{"process_id":"process","name":"办理流程",...
      JSONObject jsonObject = JSON.parseObject(json_xml);
      JSONObject properties = jsonObject.getJSONObject("properties");
      String processKey = properties.getString("process_id");
      String processName = properties.getString("name");

      ProcessDefinition pdKey = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).singleResult();
      ProcessDefinition pdName = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processName).singleResult();
      //未部署
      if (model.getDeploymentId() == null) {
        if (pdKey != null) {
          throw new RuntimeException("流程key重复");
        }

        if (pdName != null) {
          throw new RuntimeException("流程名称重复");
        }
      } else {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(model.getDeploymentId()).singleResult();
        if (pdKey != null && !pdKey.getId().equals(processDefinition.getId())) {
          throw new RuntimeException("流程key重复");
        }
        if (pdName != null && !pdName.getId().equals(processDefinition.getId())) {
          throw new RuntimeException("流程名称重复");
        }
      }

      ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

      modelJson.put(MODEL_NAME, name);
      modelJson.put(MODEL_DESCRIPTION, description);
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
      
    } catch (Exception e) {
      LOGGER.error("Error saving model", e);
      throw new ActivitiException("Error saving model", e);
    }
  }
}
