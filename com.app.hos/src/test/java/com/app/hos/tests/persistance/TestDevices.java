package com.app.hos.tests.persistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.tests.config.PersistanceConfig;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class})
public class TestDevices {

    @Before
    public void before() {
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveDevices() {
    }
    
    @After
    public void after() {
    }
}
