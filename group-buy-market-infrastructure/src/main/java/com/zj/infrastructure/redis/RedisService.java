package com.zj.infrastructure.redis;

import org.redisson.api.RBitSet;
import org.springframework.stereotype.Component;

@Component
public class RedisService implements IRedisService{
    @Override
    public RBitSet getBitSet(String tagId) {
        return null;
    }

    @Override
    public long getIndexFromUserId(String userId) {
        return 0;
    }
}
