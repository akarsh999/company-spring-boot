package com.company.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.demo.entity.EmpSalary;
@Component
public interface SalaryRepository extends JpaRepository<EmpSalary, Integer>{

	@Query(value="select emp_salary.* from emp_salary inner join employee on employee.emp_id = emp_salary.id where employee.has_resigned is false and (emp_salary.last_processed is null or emp_salary.last_processed < ?2 ) order by emp_salary.id limit ?1", nativeQuery = true)
	List<EmpSalary> findNextEligible(int size, String date);
		
//	  @Transactional
//	  @Modifying
//	  @Query("UPDATE EmpSalary u SET u.is_Processed = :value WHERE u.id = :userId")
//	  void updateProcessStatus( @Param("value") boolean value,@Param("userId") int userId);
}
