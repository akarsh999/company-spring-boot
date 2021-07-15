package com.company.demo.filter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
@Order(1)
public class LoggingFilter implements Filter{
	
	private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long start = System.currentTimeMillis();
		HttpServletRequest req = (HttpServletRequest) request;
        
        chain.doFilter(request, response);
        
        long elapsedTime = System.currentTimeMillis() - start;
        
        log.info("method {} : {} execution time : {} milliseconds.", req.getMethod(), req.getRequestURI(), elapsedTime );
	}

	
    
	
	
	
}
