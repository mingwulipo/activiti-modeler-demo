package com.test.activiti.model.dto.modeler;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
@Accessors(chain = true)
public class ProcessPropertiesDto {
    private String process_id = "";
    private String name = "";
    private String documentation = "";
    private String process_author = "";
    private String process_version = "";
    private String process_namespace = "http://www.activiti.org/processdef";
    private String executionlisteners = "";
    private String eventlisteners = "";
    private String signaldefinitions = "";
    private String messagedefinitions = "";
}
