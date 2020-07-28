package com.test.activiti.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lipo
 * @date 2020/7/27
 */
@Data
@ApiModel("业务编辑模型请求")
public class MouldEditRequest {

    @ApiModelProperty("模型Id")
    private String mouldId;

    @ApiModelProperty("模型名称")
    private String mouldName;

    @ApiModelProperty("流程key")
    private String processKey;

    @ApiModelProperty("流程名称")
    private String processName;

    @ApiModelProperty("节点列表")
    private List<ElementRequest> children;

}
