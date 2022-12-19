package com.devbank.receiversregister;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
class ReceiversRegisterApplicationTests {

	@Test
	void contextLoads() {
	}

}
