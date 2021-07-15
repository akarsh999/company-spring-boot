package com.company.demo.DAO;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.demo.entity.Departments;

@Component
public interface DeptRepository extends JpaRepository<Departments, Integer>{

	List<Departments> findAll();
	

	@Query("select u FROM Departments u WHERE u.dept_name= :n")
	List<Departments> findDeptbyname(@Param("n") String name);
}
