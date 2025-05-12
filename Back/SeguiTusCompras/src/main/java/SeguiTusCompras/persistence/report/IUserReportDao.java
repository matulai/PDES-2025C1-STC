package SeguiTusCompras.persistence.report;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeguiTusCompras.model.report.UserReport;
import SeguiTusCompras.model.user.User;

public interface IUserReportDao extends JpaRepository<UserReport, Long>  {
    @Query("SELECT ur.user FROM UserReport ur ORDER BY ur.numberOfPurchases DESC")
    List<User> getTopBuyers(Pageable topFive);
}
