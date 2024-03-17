package com.example.cloudnative.catalogws.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.example.cloudnative.catalogws.annotation.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ApiAop {
	
	@Around("@annotation(com.example.cloudnative.catalogws.annotation.Api)")
	public Object calculateExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		// 해당 클래스 처리 전의 시간
		StopWatch sw = new StopWatch();
		sw.start();
		
	    MethodSignature signature = (MethodSignature) pjp.getSignature();
	    Method method = signature.getMethod();
	    Api api = method.getAnnotation(Api.class);

		// 해당 클래스의 메소드 실행
		Object result = pjp.proceed();

		// 해당 클래스 처리 후의 시간
		sw.stop();
		long executionTime = sw.getTime();

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String task = className + "." + methodName;

		log.info("##############################[ExecutionTime] " + task + "-->" + executionTime + "(ms)");

		return result;
	}
}
