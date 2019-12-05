package com.hanyun.scm.api.service.impl;

import com.hanyun.scm.api.service.ReplenishmentApplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ReplenishmentApplyServiceImplTest {

    @Resource
    private ReplenishmentApplyService replenishmentApplyService;

    @Test
    public void testInvalidateApply() throws Exception {
        replenishmentApplyService.invalidateApply();
    }

}