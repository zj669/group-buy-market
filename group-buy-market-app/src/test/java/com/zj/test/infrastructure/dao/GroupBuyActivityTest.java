package com.zj.test.infrastructure.dao;

import com.zj.infrastructure.dao.IGroupBuyActivityDao;
import com.zj.infrastructure.dao.po.GroupBuyActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyActivityTest {
    @Resource
    private IGroupBuyActivityDao iGroupBuyActivityDao;

    @Test
    public void queryGroupBuyActivityList() {
        log.info("queryGroupBuyActivityList");
        List<GroupBuyActivity> groupBuyActivities =
                iGroupBuyActivityDao.queryGroupBuyActivityList();
        groupBuyActivities.forEach(groupBuyActivity -> log.info("{}", groupBuyActivity));
    }
}
