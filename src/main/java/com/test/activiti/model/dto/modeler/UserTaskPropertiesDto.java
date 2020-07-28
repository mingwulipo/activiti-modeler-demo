package com.test.activiti.model.dto.modeler;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
@Accessors(chain = true)
public class UserTaskPropertiesDto {
    /**
     * id，必须赋值
     */
    private String overrideid = "";

    /**
     * 名称，必须赋值
     */
    private String name = "";

    private String documentation  = "";
    private String asynchronousdefinition  = "false";
    private String exclusivedefinition  = "false";
    private String executionlisteners  = "";
    private String multiinstance_type  = "None";
    private String multiinstance_cardinality  = "";
    private String multiinstance_collection  = "";
    private String multiinstance_variable  = "";
    private String multiinstance_condition  = "";
    private String isforcompensation  = "false";

    /**
     * 角色，必须赋值
     */
    private UsertaskassignmentDto usertaskassignment;

    private String formkeydefinition  = "";
    private String duedatedefinition  = "";
    private String prioritydefinition  = "";
    private String formproperties  = "";
    private String tasklisteners  = "";
}
