package com.company.demo.DAO;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.demo.entity.Employee;

@Component
public interface EmpRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findAll();
	
	@Query(value="SELECT u.email FROM Employee u WHERE u.emp_id = ?1")
	String selectEmail(int id);
	
}
