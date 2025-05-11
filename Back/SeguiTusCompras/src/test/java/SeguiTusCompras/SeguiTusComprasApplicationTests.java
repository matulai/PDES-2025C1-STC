package SeguiTusCompras;

import SeguiTusCompras.persistence.IUserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeguiTusComprasApplicationTests {
	@Autowired
	IUserDao userDao;
	@Test
	void contextLoads() {
		userDao.deleteAll();
	}

}
