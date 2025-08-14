package com.zj.infrastructure.redis;

import org.redisson.api.RBitSet;

public interface  IRedisService {
    RBitSet getBitSet(String tagId);

    long getIndexFromUserId(String userId);
}
