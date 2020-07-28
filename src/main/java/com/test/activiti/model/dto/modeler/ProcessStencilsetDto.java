package com.test.activiti.model.dto.modeler;

import lombok.Data;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class ProcessStencilsetDto {
    private String url = "stencilsets/bpmn2.0/bpmn2.0.json";
    private String namespace = "http://b3mn.org/stencilset/bpmn2.0#";
}
