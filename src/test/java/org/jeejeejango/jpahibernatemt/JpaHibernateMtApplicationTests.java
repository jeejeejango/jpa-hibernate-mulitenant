package org.jeejeejango.jpahibernatemt;

import org.jeejeejango.entity.User;
import org.jeejeejango.mt.TenantHolder;
import org.jeejeejango.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaHibernateMtApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@BeforeClass
	public static void init() {
		org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.getInstance();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMultiTenantJpa() {
		//get user for tenant 1
		TenantHolder.setTenantId("tenant_1");
		Optional<User> user = userRepository.findById(1L);
		user.ifPresent(u -> {
			//update first name to tenant id
			u.setFirstName("tenant_1");
			userRepository.save(u);
		});
		user = userRepository.findById(1L);
		//validate that name has been updated to db
		assertTrue(user.isPresent() && user.get().getFirstName().equals("tenant_1"));

		//get user from tenant 2
		TenantHolder.setTenantId("tenant_2");
		user = userRepository.findById(1L);

		//check that user is retrieved from tenant_2
		assertTrue(user.isPresent() && !user.get().getFirstName().equals("tenant_1"));
		user.get().setFirstName("tenant_2");
		userRepository.save(user.get());

		user = userRepository.findById(1L);
		//validate that name has been updated to db
		assertTrue(user.isPresent() && user.get().getFirstName().equals("tenant_2"));

		TenantHolder.clear();
	}

}

