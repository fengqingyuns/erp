package com.hanyun.scm.api.service.impl;

import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.service.IdGenerateSeqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class IdGenerateSeqServiceImplTest {

    @Resource
    IdGenerateSeqService idGenerateSeqService;

    @Test
    public void generateId() throws Exception {
        System.err.println(idGenerateSeqService.generateId(IdGenerateType.HJ));
    }

}