package com.zj.infrastructure.adapter.repository;

import com.zj.domain.tag.adapter.repository.ITagRepository;
import com.zj.domain.tag.model.entity.CrowdTagsJobEntity;
import com.zj.infrastructure.dao.ICrowdTagsDao;
import com.zj.infrastructure.dao.ICrowdTagsDetailDao;
import com.zj.infrastructure.dao.ICrowdTagsJobDao;
import com.zj.infrastructure.dao.po.CrowdTags;
import com.zj.infrastructure.dao.po.CrowdTagsDetail;
import com.zj.infrastructure.dao.po.CrowdTagsJob;
import com.zj.infrastructure.redis.IRedisService;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TagRepository implements ITagRepository {
    @Resource
    private ICrowdTagsDao crowdTagsDao;
    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;
    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;
    @Resource
    private IRedisService redisService;
    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob queryCrow = CrowdTagsJob.builder().tagId(tagId).batchId(batchId).build();
        CrowdTagsJob crowdTagsJob = crowdTagsJobDao.queryCrowdTagsJob(queryCrow);
        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJob.getTagType())
                .tagRule(crowdTagsJob.getTagRule())
                .statStartTime(crowdTagsJob.getStatStartTime())
                .statEndTime(crowdTagsJob.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);
        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);
            // 获取BitSet
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);
        } catch (DuplicateKeyException ignore) {
            // 忽略唯一索引冲突
        }
    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, Integer size) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(size);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
