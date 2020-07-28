package com.test.activiti.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 任务节点
 * </p>
 *
 * @author lipo
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobExtendDto {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 任务节点key
     */
    private String jobKey;

    /**
     * 任务节点名称
     */
    private String jobName;

    /**
     * 角色
     */
    private String roleId;


    private String modelId;

}
