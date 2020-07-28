package com.test.activiti.model.dto.modeler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class SequenceFlowDto implements ChildElementDto {
    private String resourceId;
    private SequenceFlowPropertiesDto properties;
    private ProcessStencilDto stencil = new ProcessStencilDto("SequenceFlow");
    private List<ChildElementDto> childShapes = new ArrayList<>();
    private List<OutgoingDto> outgoing;
    private ProcessBoundsDto bounds;
    private List<CoordinateDto> dockers = new ArrayList<>();
    private OutgoingDto target;
}
