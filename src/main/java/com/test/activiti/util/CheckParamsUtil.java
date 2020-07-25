package com.test.activiti.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lipo
 * @since 2020-07-09
 */
public class CheckParamsUtil {

    /**
     * 非空校验
     * @param arr
     */
    public static void notEmpty(Object [] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (Object o : arr) {
            if (o == null) {
                throw new RuntimeException("参数不能为空");
            }

            if (o instanceof String && StringUtils.isBlank((String)o)) {
                throw new RuntimeException("参数不能为空");
            }

            if (o instanceof List && CollectionUtils.isEmpty((List)o)) {
                throw new RuntimeException("参数不能为空");
            }
            if (o instanceof Set && CollectionUtils.isEmpty((Set)o)) {
                throw new RuntimeException("参数不能为空");
            }
            if (o instanceof Map && CollectionUtils.isEmpty((Map)o)) {
                throw new RuntimeException("参数不能为空");
            }
        }
    }
}
