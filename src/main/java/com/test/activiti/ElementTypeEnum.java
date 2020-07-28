package com.test.activiti;

/**
 * @author lipo
 * @date 2020/7/27
 */
public enum ElementTypeEnum {
    START(1, "开始"),
    TASK(2, "任务"),
    LINE(3, "连线"),
    END(4, "结束"),
    ;

    ElementTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ElementTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ElementTypeEnum elementTypeEnum : values()) {
            if (elementTypeEnum.getCode().equals(code)) {
                return elementTypeEnum;
            }
        }
        return null;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }}
