package SeguiTusCompras;

import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.persistence.IUserSecurityDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeguiTusComprasApplicationTests {
	@Autowired
	IUserDao userDao;
	@Autowired
	IUserSecurityDao userSecurity;
	@Test
	void contextLoads() {
		userDao.deleteAll();
	}

}
