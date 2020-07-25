package com.test.activiti.dao;

import com.test.activiti.entity.DistributeId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 分布式id Mapper 接口
 * </p>
 *
 * @author lipo
 * @since 2020-07-25
 */
@Mapper
public interface DistributeIdMapper extends BaseMapper<DistributeId> {

    DistributeId getByName(@Param("name") String name);
}
