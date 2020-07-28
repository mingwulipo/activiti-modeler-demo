package com.test.activiti.model.request;

import com.test.activiti.model.dto.modeler.ProcessBoundsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class ElementRequest {
    @ApiModelProperty(value = "节点类型，1开始，2任务，3连线，4结束", required = true)
    private Integer type;

    @ApiModelProperty(value = "资源id，所有类型都有，必须字母开头", required = true)
    private String resourceId;

    @ApiModelProperty(value = "边界位置，所有类型都有", required = true)
    private ProcessBoundsDto bounds;

    @ApiModelProperty(value = "任务key，type=2时候有")
    private String key;

    @ApiModelProperty(value = "下一个资源id，type#4时候有")
    private String nextResourceId;

}
