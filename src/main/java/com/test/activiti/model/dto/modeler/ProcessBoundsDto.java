package com.test.activiti.model.dto.modeler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
public class ProcessBoundsDto {
    @ApiModelProperty(value = "右下角", required = true)
    private CoordinateDto lowerRight;

    @ApiModelProperty(value = "左上角", required = true)
    private CoordinateDto upperLeft;
}
