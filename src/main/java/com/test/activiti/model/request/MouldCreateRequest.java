package com.test.activiti.model.request;

import com.test.activiti.model.dto.modeler.ProcessBoundsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lipo
 * @date 2020/7/27
 */
@Data
@ApiModel("业务创建模型请求")
public class MouldCreateRequest {

    @ApiModelProperty(value = "流程key", required = true)
    private String processKey;

    @ApiModelProperty(value = "流程名称，格式：xxx流程", required = true)
    private String processName;

    @ApiModelProperty(value = "节点列表", required = true)
    private List<ElementRequest> children;

    @ApiModelProperty(value = "边界位置", required = true)
    private ProcessBoundsDto bounds;
}
