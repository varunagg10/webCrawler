package com.prmati.crawler.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public abstract class AbstractBaseTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

}