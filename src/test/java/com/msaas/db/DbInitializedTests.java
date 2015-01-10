package com.msaas.db;

import com.msaas.MsaasApplication;
import com.msaas.rest.ObserverController;
import com.msaas.security.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

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

    @Autowired
    private ObserverController observerController;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void test_default_fixtures() {
        assertThat(this.template.queryForObject("SELECT count(*) FROM camera", Integer.class), greaterThanOrEqualTo(6));
    }

    @Test
    public void should_have_a_next_screen() {
        UserDetails user = userDetailsService.loadUserByUsername("Cosmin");

        observerController.nextScreen((User) user, 5);
    }


}