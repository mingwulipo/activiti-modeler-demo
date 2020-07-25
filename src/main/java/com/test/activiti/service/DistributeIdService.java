package com.test.activiti.service;

import com.test.activiti.dao.DistributeIdMapper;
import com.test.activiti.entity.DistributeId;
import com.test.activiti.util.DistributeIdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

/**
 * @author lipo
 * @date 2020/7/25
 */
@Service
public class DistributeIdService {
    @Autowired
    private DistributeIdMapper distributeIdMapper;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    public Integer getNextId(String idName) {
        Lock lock = redisLockRegistry.obtain(idName);
        lock.lock();
        try {
            DistributeId distributeId = distributeIdMapper.getByName(idName);
            //不存在，设置初始值
            if (distributeId == null) {
                distributeId = new DistributeId();
                distributeId.setName(idName);
                distributeId.setInitValue(DistributeIdConstant.INIT_VALUE);
                distributeId.setCurrentValue(distributeId.getInitValue());
                distributeIdMapper.insert(distributeId);
                return distributeId.getCurrentValue();
            }
            //存在，当前值+1
            else {
                DistributeId update = new DistributeId();
                update.setId(distributeId.getId());
                update.setCurrentValue(distributeId.getCurrentValue() + 1);
                distributeIdMapper.updateById(update);
                return update.getCurrentValue();
            }
        } finally {
            lock.unlock();
        }
    }
}
