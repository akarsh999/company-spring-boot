package com.company.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.company.demo.entity.SalaryAudit;

@Component
public interface SalaryAuditRepository extends JpaRepository<SalaryAudit, Integer> {

}
