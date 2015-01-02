package com.msaas.db;

import com.msaas.MsaasApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * @author cj
 * @since 02/01/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MsaasApplication.class)
@WebAppConfiguration
public class DbInitializedTests {
    
    @Autowired
    private JdbcTemplate template;
    
    @Test
    public void testDefaultFixtures() {
        assertEquals(new Integer(1), this.template.queryForObject("select count(*) from camera", Integer.class));
    }
    
}