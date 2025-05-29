package SeguiTusCompras.persistence.report;

import org.springframework.data.jpa.repository.JpaRepository;

import SeguiTusCompras.model.report.Report;

public interface IReportDao extends JpaRepository<Report, Long>{
    
}
