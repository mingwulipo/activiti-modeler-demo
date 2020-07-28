package com.test.activiti.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.activiti.ElementTypeEnum;
import com.test.activiti.dao.TaskSumMapper;
import com.test.activiti.entity.TaskSum;
import com.test.activiti.model.dto.modeler.*;
import com.test.activiti.model.request.MouldCreateRequest;
import com.test.activiti.util.CollectionUtil;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipo
 * @date 2020/7/27
 */
@Service
public class MouldService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskSumMapper taskSumMapper;

    @Transactional(rollbackFor = Exception.class)
    public Object create(MouldCreateRequest request) throws Exception {
        //创建模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String description = "";
        int revision = 1;

        ObjectNode modelNode = objectMapper.createObjectNode();
        String mouldName = request.getProcessName() + "模型";
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, mouldName);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(mouldName);
        model.setKey(request.getProcessKey());
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);

        //拼装json
        ProcessDto dto = new ProcessDto();
        dto.setResourceId(model.getId());
        dto.setProperties(new ProcessPropertiesDto().setProcess_id(request.getProcessKey()).setName(request.getProcessName()));
        dto.setBounds(request.getBounds());

        if (CollectionUtils.isEmpty(request.getChildren())) {
            throw new RuntimeException("节点列表不能为空");
        }
        List<ChildElementDto> list = new ArrayList<>();
        request.getChildren().forEach(elementRequest -> {
            ElementTypeEnum elementTypeEnum = ElementTypeEnum.getByCode(elementRequest.getType());
            if (elementTypeEnum == null) {
                throw new RuntimeException("元素类型赋值错误");
            }

            if (elementTypeEnum == ElementTypeEnum.START) {
                    StartDto startDto = new StartDto();
                    startDto.setResourceId(elementRequest.getResourceId());
                    startDto.setOutgoing(CollectionUtil.singleList(new OutgoingDto(elementRequest.getNextResourceId())));
                    startDto.setBounds(elementRequest.getBounds());
                    startDto.setProperties(new StartPropertiesDto());
                    list.add(startDto);

            } else if (elementTypeEnum == ElementTypeEnum.TASK) {
                UserTaskDto userTaskDto = new UserTaskDto();
                userTaskDto.setResourceId(elementRequest.getResourceId());
                userTaskDto.setOutgoing(CollectionUtil.singleList(new OutgoingDto(elementRequest.getNextResourceId())));
                userTaskDto.setBounds(elementRequest.getBounds());

                UserTaskPropertiesDto userTaskPropertiesDto = new UserTaskPropertiesDto().setOverrideid(elementRequest.getKey());
                TaskSum taskSum = taskSumMapper.getByKey(elementRequest.getKey());
                if (taskSum == null) {
                    throw new RuntimeException("任务节点key赋值错误");
                }
                userTaskPropertiesDto.setName(taskSum.getTaskName());
                CandidateGroupDto candidateGroupDto = new CandidateGroupDto();
                candidateGroupDto.setValue(taskSum.getRoleId());
                candidateGroupDto.set$$hashKey(taskSum.getRoleId());
                userTaskPropertiesDto.setUsertaskassignment(new UsertaskassignmentDto(new AssignmentDto(CollectionUtil.singleList(candidateGroupDto))));
                userTaskDto.setProperties(userTaskPropertiesDto);
                list.add(userTaskDto);

            } else if (elementTypeEnum == ElementTypeEnum.LINE) {
                SequenceFlowDto sequenceFlowDto = new SequenceFlowDto();
                sequenceFlowDto.setResourceId(elementRequest.getResourceId());
                sequenceFlowDto.setOutgoing(CollectionUtil.singleList(new OutgoingDto(elementRequest.getNextResourceId())));
                sequenceFlowDto.setTarget(new OutgoingDto(elementRequest.getNextResourceId()));
                sequenceFlowDto.setBounds(elementRequest.getBounds());
                sequenceFlowDto.setProperties(new SequenceFlowPropertiesDto());

                List<CoordinateDto> dockers = new ArrayList<>(2);
                dockers.add(new CoordinateDto(15, 15));
                dockers.add(new CoordinateDto(50, 40));
                sequenceFlowDto.setDockers(dockers);
                list.add(sequenceFlowDto);

            } else if (elementTypeEnum == ElementTypeEnum.END) {
                EndDto endDto = new EndDto();
                endDto.setResourceId(elementRequest.getResourceId());
                endDto.setBounds(elementRequest.getBounds());
                endDto.setProperties(new EndPropertiesDto());
                list.add(endDto);
            }
        });
        dto.setChildShapes(list);
        dto.setStencilset(new ProcessStencilsetDto());

        String json = JSON.toJSONString(dto);
        repositoryService.addModelEditorSource(model.getId(), json.getBytes("utf-8"));

        return "ok";
    }
}
