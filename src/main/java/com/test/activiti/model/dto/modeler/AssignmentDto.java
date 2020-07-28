package com.test.activiti.model.dto.modeler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {
    private List<CandidateGroupDto> candidateGroups;
}
