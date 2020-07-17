package com.test.activiti.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.activiti.util.DateConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskVO {

    private String taskId;

    private String taskKey;

    private String taskName;

    private String processDefinitionKey;

    private String processDefinitionName;

    @JsonFormat(pattern = DateConstant.YYYYMMDDHHMMSS, timezone = DateConstant.TIME_ZONE)
    private Date createTime;
}
