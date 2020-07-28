package com.test.activiti.model.dto.modeler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateDto {
    @ApiModelProperty(value = "横坐标", required = true)
    private Integer x;

    @ApiModelProperty(value = "纵坐标", required = true)
    private Integer y;
}
