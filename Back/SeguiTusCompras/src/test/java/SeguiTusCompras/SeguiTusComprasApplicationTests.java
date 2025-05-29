package SeguiTusCompras;

import SeguiTusCompras.persistence.IUserDao;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("No andan los e2e tests")
class SeguiTusComprasApplicationTests {
	@Autowired
	IUserDao userDao;
	@Test
	void contextLoads() {
		userDao.deleteAll();
	}

}
