package SeguiTusCompras;

import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.persistence.IUserSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeguiTusComprasApplicationTests {
	@Autowired
	IUserDao userDao;
	@Autowired
	IUserSecurity userSecurity;
	@Test
	void contextLoads() {
		userDao.deleteAll();
	}

}
