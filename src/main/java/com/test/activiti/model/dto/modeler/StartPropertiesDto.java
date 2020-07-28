package com.test.activiti.model.dto.modeler;

import lombok.Data;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class StartPropertiesDto {
    private String overrideid = "";
    private String name = "";
    private String documentation = "";
    private String executionlisteners = "";
    private String initiator = "";
    private String formkeydefinition = "";
    private String formproperties = "";
}
