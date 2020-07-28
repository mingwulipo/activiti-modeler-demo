package com.test.activiti.model.dto.modeler;

import lombok.Data;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class SequenceFlowPropertiesDto {

    private String overrideid = "";
    private String name = "";
    private String documentation  = "";
    private String conditionsequenceflow  = "";
    private String executionlisteners  = "";
    private String defaultflow  = "false";
}
