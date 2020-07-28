package com.test.activiti.model.dto.modeler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class ProcessDto {
    private String resourceId;
    private ProcessPropertiesDto properties;
    private ProcessStencilDto stencil = new ProcessStencilDto("BPMNDiagram");
    private List<ChildElementDto> childShapes;
    private ProcessBoundsDto bounds;
    private ProcessStencilsetDto stencilset;
    private List<String> ssextensions = new ArrayList<>();
}
