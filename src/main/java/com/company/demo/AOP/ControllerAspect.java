package com.company.demo.AOP;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.company.demo.Exceptions.AccountDisabledException;
import com.company.demo.Exceptions.ApiError;
import com.company.demo.Exceptions.InvalidCredentialException;
import com.company.demo.Exceptions.NoDataFoundException;
import com.company.demo.Exceptions.UnauthorizedUserException;

import io.jsonwebtoken.ExpiredJwtException;

@Aspect
@Component
public class ControllerAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);
	
	@Pointcut("execution(* com.company.demo.service.DataManager.*(..))")
	public void dataManagementMethods() { }
	
	@Pointcut("execution(* com.company.demo.controller.apiController.*(..))")
	public void apiMethods() {}
	
	@Pointcut("execution(* com.company.demo.service.JWTcache.*(..))")
	public void jwtCacheMethods() {};
	
	@Pointcut("execution(* com.company.demo.Utility.JwtUtil.*(..))")
	public void jwtUtilMethods() {};
	
	@Pointcut("execution(* com.company.demo.service.SalaryScheduler.*(..))")
	public void salarySchedulerMethods() {};
	
	@Pointcut("execution(* com.company.demo.Utility.JWTAuthorization.*(..))")
	public void JWTAuthenticationMethods() {};
	
	@Pointcut("execution(* com.company.demo.controller.ViewController.*(..))")
	public void viewControllerMethods() {};
	
	@Around("dataManagementMethods()")
	public Object aroundDataManagement(ProceedingJoinPoint pjp) throws Throwable{
		log.info("Class:"+pjp.getTarget().getClass()+" start executing -> method ->"+pjp.getSignature().getName());
		Object obj = null;
		try {
			obj = pjp.proceed();
//			log.info("Method "+pjp.getSignature().getName()+" execution time: " + elapsedTime + " milliseconds.");
			log.info("Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName());
			throw e;
		}
		return obj;
	}
	@Around("JWTAuthenticationMethods()")
	public Object aroundJWTAuthentication(ProceedingJoinPoint pjp) throws Throwable{
		log.info("Class:"+pjp.getTarget().getClass()+" start executing -> method ->"+pjp.getSignature().getName());
		Object obj = null;
		try {
			obj = pjp.proceed();
//			log.info("Method "+pjp.getSignature().getName()+" execution time: " + elapsedTime + " milliseconds.");
			log.info("Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName());
			throw e;
		}
		return obj;
	}
	@Around("apiMethods()")
	public Object aroundApiController(ProceedingJoinPoint pjp){
		
		log.info("Class:"+pjp.getTarget().getClass()+" start executing -> method ->"+pjp.getSignature().getName());
		Object obj = null;
		try {
			obj = pjp.proceed();
			
			log.info("Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
			return obj;
			
		} catch (UnauthorizedUserException e) {
			// TODO: handle exception
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), e.getMessage()),HttpStatus.UNAUTHORIZED);
		} catch(InvalidCredentialException e) {
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
		 catch(AccountDisabledException e) {
				log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
//				log.error("error information is",e);
				return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), e.getMessage()),HttpStatus.UNAUTHORIZED);
			}
		catch(NoSuchElementException e) {
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, LocalDateTime.now(), e.getMessage()),HttpStatus.NOT_FOUND);
		}
		catch(ExpiredJwtException e) {
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
		catch(InvalidDataAccessApiUsageException e) {
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "there is already a user with this id"),HttpStatus.BAD_REQUEST);
		}
		catch(NoDataFoundException e) {
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, LocalDateTime.now(), e.getMessage()),HttpStatus.NOT_FOUND);
		}
		catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+ e.getMessage());
			log.error("error information is",e);
			String method = pjp.getSignature().getName();
//			if(method.substring(0, 3)=="get")obj = ResponseEntity.notFound().build();
			if(method.equals("generateJwtToken"))obj= new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), e.getMessage()),HttpStatus.UNAUTHORIZED);
			else obj=ResponseEntity.internalServerError().build();
			return obj;
		}
		
	}
	@Around("jwtUtilMethods()")
	public Object aroundUtilMethod(ProceedingJoinPoint pjp) throws Throwable{
		long start = System.currentTimeMillis();
		log.info("Class:"+pjp.getTarget().getClass()+" start executing -> method ->"+pjp.getSignature().getName());
		Object obj = null;
		try {
			obj = pjp.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
//			log.info("Method "+pjp.getSignature().getName()+" execution time: " + elapsedTime + " milliseconds.");
			log.info("Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName());
			throw e;
		}
		return obj;
	}
	
	@AfterThrowing(pointcut = "jwtCacheMethods()",throwing = "ex")
	public void throwingJWTcache(JoinPoint jp, Throwable ex) throws Throwable {
		log.error("Error  in "+jp.getTarget().getClass()+" while executing -> method ->"+jp.getSignature().getName()+" "+ ex.getMessage());
		throw ex;
	}
	@AfterThrowing(pointcut = "salarySchedulerMethods()",throwing = "ex")
	public void throwingSalaryScheduler(JoinPoint jp, Throwable ex){
		log.error("Error  in "+jp.getTarget().getClass()+" while executing -> method ->"+jp.getSignature().getName()+" "+ ex.getMessage(),ex);
	}
	@Around("viewControllerMethods()")
	public Object aroundViewController(ProceedingJoinPoint pjp) {
		log.info("Class:"+pjp.getTarget().getClass()+" start executing -> method ->"+pjp.getSignature().getName());
		Object obj = null;
		try {
			obj = pjp.proceed();
			
			log.info("Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
			return obj;
			
		}
		catch (UnauthorizedUserException e) {
			// TODO: handle exception
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+e.getMessage());
			log.error("error information is",e);
			return "unauthorized";
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("Error  in "+pjp.getTarget().getClass()+" while executing -> method ->"+pjp.getSignature().getName()+" "+ e.getMessage());
			log.error("error information is",e);
//			if(method.substring(0, 3)=="get")obj = ResponseEntity.notFound().build();
			return "error";
		}
	}
}
